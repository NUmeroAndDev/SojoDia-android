package com.numero.sojodia.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.net.toUri
import com.numero.sojodia.BuildConfig
import com.numero.sojodia.R
import com.numero.sojodia.extension.applyAppTheme
import com.numero.sojodia.extension.module
import com.numero.sojodia.model.AppTheme
import com.numero.sojodia.repository.IConfigRepository
import com.numero.sojodia.ui.licenses.LicensesActivity
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private val configRepository: IConfigRepository
        get() = module.configRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyAppTheme(configRepository.appTheme)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupViews()
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

    private fun setupViews() {
        selectThemeSettingsItemView.apply {
            val currentTheme = configRepository.appTheme
            setSummary(getString(currentTheme.textRes))
            setOnClickListener {
                showSelectThemeMenu(it.findViewById(R.id.titleTextView))
            }
        }

        busDataSettingsItemView.setSummary(configRepository.currentVersion.value.toString())

        appVersionSettingsItemView.setSummary(BuildConfig.VERSION_NAME)

        viewSourceSettingsItemView.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, SOURCE_URL.toUri()))
        }

        licensesSettingsItemView.setOnClickListener {
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
                    R.id.theme_auto_battery -> AppTheme.AUTO_BATTERY
                    else -> throw Exception()
                }
                configRepository.appTheme = theme
                selectThemeSettingsItemView.setSummary(getString(theme.textRes))
                applyAppTheme(theme)
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
                AppTheme.SYSTEM_DEFAULT -> R.string.theme_system_default
                AppTheme.AUTO_BATTERY -> R.string.theme_auto_battery
            }
        }

    companion object {

        private const val SOURCE_URL = "https://github.com/NUmeroAndDev/SojoDia-android"

        fun createIntent(context: Context): Intent = Intent(context, SettingsActivity::class.java)
    }
}
