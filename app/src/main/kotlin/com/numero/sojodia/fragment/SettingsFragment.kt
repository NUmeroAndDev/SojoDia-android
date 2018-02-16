package com.numero.sojodia.fragment

import android.os.Bundle
import android.preference.PreferenceFragment
import android.preference.PreferenceScreen

import com.numero.sojodia.BuildConfig
import com.numero.sojodia.R
import com.numero.sojodia.activity.LicensesActivity
import com.numero.sojodia.contract.SettingsContract

class SettingsFragment : PreferenceFragment(), SettingsContract.View {

    private lateinit var presenter: SettingsContract.Presenter
    private val dataVersionScreen: PreferenceScreen by lazy {
        findPreference("data_version") as PreferenceScreen
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.settings)

        val appVersionScreen = findPreference("app_version") as PreferenceScreen
        appVersionScreen.summary = BuildConfig.VERSION_NAME

        val licensesScreen = findPreference("licenses") as PreferenceScreen
        licensesScreen.setOnPreferenceClickListener {
            startActivity(LicensesActivity.createIntent(activity))
            false
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun onPause() {
        super.onPause()
        presenter.unSubscribe()
    }

    override fun setPresenter(presenter: SettingsContract.Presenter) {
        this.presenter = presenter
    }

    override fun showBusDataVersion(version: String) {
        dataVersionScreen.summary = version
    }

    companion object {
        fun newInstance(): SettingsFragment = SettingsFragment()
    }
}
