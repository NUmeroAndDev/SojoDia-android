package com.numero.sojodia

import android.content.Context
import android.content.Intent
import com.numero.common.IIntentResolver
import com.numero.sojodia.activity.MainActivity
import com.numero.sojodia.activity.SettingsActivity
import com.numero.sojodia.activity.SplashActivity
import com.numero.sojodia.component.library.LicensesActivity

class IntentResolver(context: Context) : IIntentResolver {
    override val licensesActivityIntent: Intent = Intent(context, LicensesActivity::class.java)

    override val mainActivityIntent: Intent = Intent(context, MainActivity::class.java)

    override val settingsActivityIntent: Intent = Intent(context, SettingsActivity::class.java)

    override val splashActivityIntent: Intent = Intent(context, SplashActivity::class.java)
}