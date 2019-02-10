package com.numero.sojodia

import android.content.Context
import android.content.Intent
import com.numero.common.IIntentResolver
import com.numero.sojodia.component.library.LicensesActivity
import com.numero.sojodia.component.main.MainActivity
import com.numero.sojodia.component.settings.SettingsActivity
import com.numero.sojodia.component.splash.SplashActivity
import com.numero.sojodia.model.Reciprocate

class IntentResolver(
        private val context: Context
) : IIntentResolver {

    override val licensesActivityIntent: Intent = Intent(context, LicensesActivity::class.java)

    override fun getMainActivityIntent(reciprocate: Reciprocate?): Intent {
        return Intent(context, MainActivity::class.java).apply {
            putExtra(MainActivity.BUNDLE_RECIPROCATE, reciprocate)
        }
    }

    override fun getSettingsActivityIntent(reciprocate: Reciprocate): Intent {
        return Intent(context, SettingsActivity::class.java).apply {
            putExtra(SettingsActivity.BUNDLE_RECIPROCATE, reciprocate)
        }
    }

    override val splashActivityIntent: Intent = Intent(context, SplashActivity::class.java)
}