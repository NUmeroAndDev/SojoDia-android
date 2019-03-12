package com.numero.sojodia.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.numero.sojodia.R
import com.numero.sojodia.extension.app
import com.numero.sojodia.extension.setNightMode
import com.numero.sojodia.fragment.SettingsFragment
import com.numero.sojodia.model.Reciprocate
import com.numero.sojodia.repository.IConfigRepository
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity(), SettingsFragment.ISettingsTransition {

    private val configRepository: IConfigRepository
        get() = app.configRepository

    private val reciprocate by lazy { intent.getSerializableExtra(BUNDLE_RECIPROCATE) as Reciprocate }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setNightMode(configRepository.isUseDarkTheme)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.beginTransaction().replace(R.id.container, SettingsFragment.newInstance()).commit()
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

    override fun onBackPressed() {
        startActivity(MainActivity.createClearTopIntent(this, reciprocate))
    }

    override fun reopenSettingsScreen() {
        recreate()
    }

    override fun showSource() {
        startActivity(Intent(Intent.ACTION_VIEW, SOURCE_URL.toUri()))
    }

    override fun showLicensesScreen() {
        startActivity(LicensesActivity.createIntent(this))
    }

    companion object {

        private const val BUNDLE_RECIPROCATE = "BUNDLE_RECIPROCATE"

        private const val SOURCE_URL = "https://github.com/NUmeroAndDev/SojoDia-android"

        fun createIntent(context: Context, reciprocate: Reciprocate): Intent = Intent(context, SettingsActivity::class.java).apply {
            putExtra(BUNDLE_RECIPROCATE, reciprocate)
        }
    }
}
