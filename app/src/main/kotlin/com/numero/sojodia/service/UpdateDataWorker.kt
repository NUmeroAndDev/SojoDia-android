package com.numero.sojodia.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.numero.sojodia.Module
import com.numero.sojodia.model.LatestVersion

class UpdateDataWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    private val configRepository = (applicationContext as Module).configRepository
    private val busDataRepository = (applicationContext as Module).busDataRepository

    override suspend fun doWork(): Result {
        if (configRepository.isTodayUpdateChecked) {
            return Result.success()
        }

        val result = busDataRepository.fetchConfig()
        return when (result) {
            is com.numero.sojodia.model.Result.Success -> {
                val config = result.value
                configRepository.updateCheckUpdateDate()
                if (config.checkUpdate(configRepository.currentVersion)) {
                    executeUpdate(config.latestVersion)
                } else {
                    executeUpdate(config.latestVersion)
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
                return Result.success()
            }
            is com.numero.sojodia.model.Result.Error -> {
                result.throwable.printStackTrace()
                return Result.failure()
            }
        }
    }
}