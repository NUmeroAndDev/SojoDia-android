package com.numero.sojodia.fragment

import android.content.Context
import android.os.Bundle
import androidx.preference.DropDownPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.numero.sojodia.BuildConfig
import com.numero.sojodia.R
import com.numero.sojodia.extension.app
import com.numero.sojodia.model.AppTheme
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

        val appThemePreference = findPreference<DropDownPreference>("select_app_theme")
        appThemePreference?.setOnPreferenceChangeListener { _, newValue ->
            transition?.switchAppTheme(AppTheme.from(newValue.toString()))
            true
        }

        findPreference<Preference>("data_version")?.summary = configRepository.currentVersion.value.toString()

        val appVersionScreen = findPreference<Preference>("app_version")
        appVersionScreen?.summary = BuildConfig.VERSION_NAME

        val licensesScreen = findPreference<Preference>("licenses")
        licensesScreen?.setOnPreferenceClickListener {
            transition?.showLicensesScreen()
            false
        }

        findPreference<Preference>("view_source")?.setOnPreferenceClickListener {
            transition?.showSource()
            false
        }
    }

    interface ISettingsTransition {
        fun switchAppTheme(appTheme: AppTheme)

        fun showSource()

        fun showLicensesScreen()
    }

    companion object {
        fun newInstance(): SettingsFragment = SettingsFragment()
    }
}
