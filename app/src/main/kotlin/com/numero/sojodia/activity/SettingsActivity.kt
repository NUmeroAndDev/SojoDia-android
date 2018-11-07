package com.numero.sojodia.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.numero.sojodia.R
import com.numero.sojodia.extension.app
import com.numero.sojodia.fragment.SettingsFragment
import com.numero.sojodia.repository.IConfigRepository
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity(), SettingsFragment.ISettingsTransition {

    private val configRepository: IConfigRepository
        get() = app.configRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(configRepository.themeRes)
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
        startActivity(MainActivity.createClearTopIntent(this))
    }

    override fun reopenSettingsScreen() {
        recreate()
    }

    override fun showLicensesScreen() {
        startActivity(LicensesActivity.createIntent(this))
    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, SettingsActivity::class.java)
    }
}
