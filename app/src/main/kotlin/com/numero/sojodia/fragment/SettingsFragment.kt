package com.numero.sojodia.fragment

import android.content.Context
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.numero.sojodia.BuildConfig
import com.numero.sojodia.R
import com.numero.sojodia.extension.app
import com.numero.sojodia.repository.IConfigRepository

class SettingsFragment : PreferenceFragmentCompat() {

    private var transition: ISettingsTransition? = null

    private val configRepository: IConfigRepository
        get() = app.configRepository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ISettingsTransition) {
            transition = context
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val useDarkModeScreen = findPreference("use_dark_theme") as SwitchPreferenceCompat
        useDarkModeScreen.setOnPreferenceChangeListener { _, _ ->
            transition?.reopenSettingsScreen()
            true
        }

        findPreference("data_version").summary = configRepository.versionCode.toString()

        val appVersionScreen = findPreference("app_version")
        appVersionScreen.summary = BuildConfig.VERSION_NAME

        val licensesScreen = findPreference("licenses")
        licensesScreen.setOnPreferenceClickListener {
            transition?.showLicensesScreen()
            false
        }
    }

    interface ISettingsTransition {
        fun reopenSettingsScreen()

        fun showLicensesScreen()
    }

    companion object {
        fun newInstance(): SettingsFragment = SettingsFragment()
    }
}
