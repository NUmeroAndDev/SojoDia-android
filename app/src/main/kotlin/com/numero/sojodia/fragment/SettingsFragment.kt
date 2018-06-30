package com.numero.sojodia.fragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.numero.sojodia.BuildConfig
import com.numero.sojodia.R
import com.numero.sojodia.activity.LicensesActivity
import com.numero.sojodia.contract.SettingsContract

class SettingsFragment : PreferenceFragmentCompat(), SettingsContract.View {

    private lateinit var presenter: SettingsContract.Presenter
//    private val dataVersionScreen: PreferenceScreen by lazy {
//        findPreference("data_version") as PreferenceScreen
//    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appVersionScreen = findPreference("app_version")
        appVersionScreen.summary = BuildConfig.VERSION_NAME

        val licensesScreen = findPreference("licenses")
        licensesScreen.setOnPreferenceClickListener {
            startActivity(LicensesActivity.createIntent(context!!))
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
        findPreference("data_version").summary = version
    }

    companion object {
        fun newInstance(): SettingsFragment = SettingsFragment()
    }
}
