package com.numero.sojodia.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.numero.sojodia.R
import com.numero.sojodia.extension.app
import com.numero.sojodia.fragment.SettingsFragment
import com.numero.sojodia.presenter.SettingsPresenter
import com.numero.sojodia.repository.ConfigRepository
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private val configRepository: ConfigRepository
        get() = app.configRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragment: SettingsFragment = supportFragmentManager.findFragmentById(R.id.container) as? SettingsFragment
                ?: SettingsFragment.newInstance().also {
                    supportFragmentManager.beginTransaction().replace(R.id.container, it).commit()
                }
        SettingsPresenter(fragment, configRepository)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, SettingsActivity::class.java)
    }
}
