package com.numero.sojodia.repository

import android.content.Context
import com.numero.sojodia.api.BusDataApi
import com.numero.sojodia.model.BusDataFile
import com.numero.sojodia.model.BusTime
import com.numero.sojodia.model.Time
import com.numero.sojodia.model.Week
import io.reactivex.Observable
import java.io.*
import java.util.*

class BusDataRepository(private val context: Context, private val busDataApi: BusDataApi) : IBusDataRepository {

    override var tkBusTimeListGoing: MutableList<BusTime> = mutableListOf()
        get() {
            return if (field.isEmpty()) {
                loadBusData(BusDataFile.TK_TO_KUTC).blockingFirst().apply {
                    field = this
                }
            } else {
                field
            }
        }

    override var tkBusTimeListReturn: MutableList<BusTime> = mutableListOf()
        get() {
            return if (field.isEmpty()) {
                loadBusData(BusDataFile.KUTC_TO_TK).blockingFirst().apply {
                    field = this
                }
            } else {
                field
            }
        }

    override var tndBusTimeListGoing: MutableList<BusTime> = mutableListOf()
        get() {
            return if (field.isEmpty()) {
                loadBusData(BusDataFile.TND_TO_KUTC).blockingFirst().apply {
                    field = this
                }
            } else {
                field
            }
        }

    override var tndBusTimeListReturn: MutableList<BusTime> = mutableListOf()
        get() {
            return if (field.isEmpty()) {
                loadBusData(BusDataFile.KUTC_TO_TND).blockingFirst().apply {
                    field = this
                }
            } else {
                field
            }
        }

    override fun loadAndSaveBusData(busDataFile: BusDataFile): Observable<String> {
        return busDataApi.getBusData(busDataFile)
                .doOnNext({
                    saveDownLoadData(busDataFile.fileName, it)
                })
    }

    override fun clearCache() {
        tkBusTimeListGoing.clear()
        tkBusTimeListReturn.clear()
        tndBusTimeListGoing.clear()
        tndBusTimeListReturn.clear()
    }

    @Throws(Exception::class)
    private fun saveDownLoadData(fileName: String, data: String) {
        context.openFileOutput(fileName, Context.MODE_PRIVATE).apply {
            write(data.toByteArray())
        }.close()
    }

    private fun loadBusData(busDataFile: BusDataFile): Observable<MutableList<BusTime>> {
        return Observable.create {
            val dataList = mutableListOf<BusTime>()
            val bufferedReader: BufferedReader
            var fileInputStream: FileInputStream? = null
            var inputStream: InputStream? = null

            // ダウンロードしたファイルがなければアセットから取得
            val file = File(context.filesDir.toString() + "/" + busDataFile.fileName)
            if (file.exists()) {
                fileInputStream = FileInputStream(file)
                bufferedReader = BufferedReader(InputStreamReader(fileInputStream, "UTF-8"))
            } else {
                val assetManager = context.resources.assets
                inputStream = assetManager.open(busDataFile.fileName)
                bufferedReader = BufferedReader(InputStreamReader(inputStream!!))
            }

            var isFirstLine = true
            while (true) {
                val line = bufferedReader.readLine() ?: break
                if (isFirstLine) {
                    // 最初の行はデータではないためスキップ
                    isFirstLine = false
                    continue
                }
                val tokenizer = StringTokenizer(line, ",")
                val hour = tokenizer.nextToken().toInt()
                val minutes = tokenizer.nextToken().toInt()
                val week = Week.getWeek(tokenizer.nextToken().toInt()) ?: continue
                val isNonstop = tokenizer.nextToken().toInt() != 0

                dataList.add(BusTime(Time(hour, minutes), week, isNonstop))
            }

            bufferedReader.close()
            fileInputStream?.close()
            inputStream?.close()
            it.onNext(dataList)
        }
    }
}
