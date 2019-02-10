package com.numero.sojodia.component.settings

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.numero.common.IIntentResolver
import com.numero.common.extension.module
import com.numero.common.extension.styleRes
import com.numero.sojodia.model.Reciprocate
import com.numero.sojodia.repository.IConfigRepository
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity(), SettingsFragment.ISettingsTransition {

    private val configRepository: IConfigRepository
        get() = module.configRepository
    private val intentResolver: IIntentResolver
        get() = module.intentResolver

    private val reciprocate by lazy { intent.getSerializableExtra(BUNDLE_RECIPROCATE) as Reciprocate }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(configRepository.theme.styleRes)
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
        val intent = intentResolver.getMainActivityIntent(reciprocate).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
    }

    override fun reopenSettingsScreen() {
        recreate()
    }

    override fun showLicensesScreen() {
        startActivity(intentResolver.licensesActivityIntent)
    }

    companion object {

        const val BUNDLE_RECIPROCATE = "BUNDLE_RECIPROCATE"
    }
}
