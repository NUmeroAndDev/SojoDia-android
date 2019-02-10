package com.numero.sojodia.component.settings

import android.content.Context
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.numero.sojodia.repository.IConfigRepository

class SettingsFragment : PreferenceFragmentCompat() {

    private var transition: ISettingsTransition? = null

    private val configRepository: IConfigRepository
        get() = module.configRepository

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

        val useDarkModeScreen = findPreference<SwitchPreferenceCompat>("use_dark_theme")
        useDarkModeScreen.setOnPreferenceChangeListener { _, _ ->
            transition?.reopenSettingsScreen()
            true
        }

        findPreference<Preference>("data_version").summary = configRepository.versionCode.toString()

        val appVersionScreen = findPreference<Preference>("app_version")
        // FIXME アプリのバージョンを参照
        appVersionScreen.summary = BuildConfig.VERSION_NAME

        val licensesScreen = findPreference<Preference>("licenses")
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
