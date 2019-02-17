package com.numero.sojodia.model

data class Config(val latestVersion: LatestVersion) {
    fun checkUpdate(currentVersion: CurrentVersion): Boolean {
        return currentVersion.value < latestVersion.value
    }
}

inline class LatestVersion(val value: Long)

inline class CurrentVersion(val value: Long)