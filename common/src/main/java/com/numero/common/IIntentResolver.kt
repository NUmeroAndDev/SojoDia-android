package com.numero.common

import android.content.Intent

interface IIntentResolver {
    val licensesActivityIntent: Intent

    val mainActivityIntent: Intent

    val settingsActivityIntent: Intent

    val splashActivityIntent: Intent
}