package com.numero.common

import android.content.Intent
import com.numero.sojodia.model.Reciprocate

interface IIntentResolver {
    val licensesActivityIntent: Intent

    fun getMainActivityIntent(reciprocate: Reciprocate? = null): Intent

    fun getSettingsActivityIntent(reciprocate: Reciprocate): Intent

    val splashActivityIntent: Intent
}