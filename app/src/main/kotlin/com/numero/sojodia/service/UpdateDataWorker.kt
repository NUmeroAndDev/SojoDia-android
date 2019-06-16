package com.numero.sojodia.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.numero.sojodia.Module
import com.numero.sojodia.model.LatestVersion

class UpdateDataWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    private val configRepository = (applicationContext as Module).configRepository
    private val busDataRepository = (applicationContext as Module).busDataRepository

    override suspend fun doWork(): Result {
        if (configRepository.isTodayUpdateChecked) {
            val resultData = Data.Builder().putString(KEY_RESULT, UpdateResult.NO_NEED_UPDATE.key).build()
            return Result.success(resultData)
        }

        val result = busDataRepository.fetchConfig()
        return when (result) {
            is com.numero.sojodia.model.Result.Success -> {
                val config = result.value
                configRepository.updateCheckUpdateDate()
                if (config.checkUpdate(configRepository.currentVersion)) {
                    executeUpdate(config.latestVersion)
                } else {
                    val resultData = Data.Builder().putString(KEY_RESULT, UpdateResult.NO_NEED_UPDATE.key).build()
                    Result.success(resultData)
                }
            }
            is com.numero.sojodia.model.Result.Error -> {
                result.throwable.printStackTrace()
                Result.failure()
            }
        }
    }

    private suspend fun executeUpdate(latestVersion: LatestVersion): Result {
        val result = busDataRepository.fetchBusData()
        when (result) {
            is com.numero.sojodia.model.Result.Success -> {
                configRepository.updateBusDataVersion(latestVersion)
                val resultData = Data.Builder().putString(KEY_RESULT, UpdateResult.SUCCESS_UPDATE.key).build()
                return Result.success(resultData)
            }
            is com.numero.sojodia.model.Result.Error -> {
                result.throwable.printStackTrace()
                return Result.failure()
            }
        }
    }

    companion object {
        const val KEY_RESULT = "KEY_RESULT"
    }
}

enum class UpdateResult(val key: String) {
    NO_NEED_UPDATE("NO_NEED_UPDATE"),
    SUCCESS_UPDATE("SUCCESS_UPDATE");

    companion object {
        fun find(key: String): UpdateResult {
            return requireNotNull(values().find { it.key == key })
        }
    }
}