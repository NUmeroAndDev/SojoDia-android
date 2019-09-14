package com.numero.sojodia.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.net.toUri
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.numero.sojodia.BuildConfig
import com.numero.sojodia.R
import com.numero.sojodia.databinding.ActivitySettingsBinding
import com.numero.sojodia.extension.applyApplication
import com.numero.sojodia.extension.module
import com.numero.sojodia.model.AppTheme
import com.numero.sojodia.repository.ConfigRepository
import com.numero.sojodia.ui.licenses.LicensesActivity

class SettingsActivity : AppCompatActivity() {

    private val configRepository: ConfigRepository
        get() = module.configRepository

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var appUpdateManager: AppUpdateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        appUpdateManager = AppUpdateManagerFactory.create(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupViews()
    }

    override fun onResume() {
        super.onResume()
        checkHasUpdate()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkHasUpdate() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            when (appUpdateInfo.updateAvailability()) {
                UpdateAvailability.UPDATE_AVAILABLE -> {
                    binding.appVersionSettingsItemView.apply {
                        setVisibleIcon(true)
                        setSummary(getString(R.string.settings_newer_version_available))
                        setOnClickListener {
                            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, this@SettingsActivity, UPDATE_REQUEST_CODE)
                        }
                    }
                }
                UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS -> {
                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, this, UPDATE_REQUEST_CODE)
                }
                else -> {
                    binding.appVersionSettingsItemView.apply {
                        setVisibleIcon(false)
                        setSummary(BuildConfig.VERSION_NAME)
                        setOnClickListener(null)
                    }
                }
            }
        }
    }

    private fun setupViews() {
        binding.selectThemeSettingsItemView.apply {
            val currentTheme = configRepository.appTheme
            setSummary(getString(currentTheme.textRes))
            setOnClickListener {
                showSelectThemeMenu(it.findViewById(R.id.titleTextView))
            }
        }

        binding.busDataSettingsItemView.setSummary(configRepository.currentVersion.value.toString())

        binding.appVersionSettingsItemView.apply {
            setVisibleIcon(false)
            setSummary(BuildConfig.VERSION_NAME)
        }

        binding.viewSourceSettingsItemView.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, SOURCE_URL.toUri()))
        }

        binding.licensesSettingsItemView.setOnClickListener {
            startActivity(LicensesActivity.createIntent(this@SettingsActivity))
        }
    }

    private fun showSelectThemeMenu(anchorView: View) {
        val popupMenu = PopupMenu(this, anchorView).apply {
            menuInflater.inflate(R.menu.menu_select_theme, menu)
            setOnMenuItemClickListener {
                val theme = when (it.itemId) {
                    R.id.theme_light -> AppTheme.LIGHT
                    R.id.theme_dark -> AppTheme.DARK
                    R.id.theme_system -> AppTheme.SYSTEM_DEFAULT
                    R.id.theme_auto_battery -> AppTheme.SYSTEM_DEFAULT
                    else -> throw Exception()
                }
                configRepository.appTheme = theme
                binding.selectThemeSettingsItemView.setSummary(getString(theme.textRes))
                theme.applyApplication()
                true
            }
        }
        popupMenu.show()
    }

    private val AppTheme.textRes: Int
        get() {
            return when (this) {
                AppTheme.LIGHT -> R.string.theme_light
                AppTheme.DARK -> R.string.theme_dark
                AppTheme.SYSTEM_DEFAULT -> if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    R.string.theme_auto_battery
                } else {
                    R.string.theme_system_default
                }
            }
        }

    companion object {
        private const val UPDATE_REQUEST_CODE = 1

        private const val SOURCE_URL = "https://github.com/NUmeroAndDev/SojoDia-android"

        fun createIntent(context: Context): Intent = Intent(context, SettingsActivity::class.java)
    }
}
