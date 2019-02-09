package com.numero.sojodia.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.numero.common.IIntentResolver
import com.numero.sojodia.R
import com.numero.sojodia.extension.module
import com.numero.sojodia.extension.styleRes
import com.numero.sojodia.fragment.SettingsFragment
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
        val intent = intentResolver.mainActivityIntent.apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(MainActivity.BUNDLE_RECIPROCATE, reciprocate)
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

        private const val BUNDLE_RECIPROCATE = "BUNDLE_RECIPROCATE"

        fun createIntent(context: Context, reciprocate: Reciprocate): Intent = Intent(context, SettingsActivity::class.java).apply {
            putExtra(BUNDLE_RECIPROCATE, reciprocate)
        }
    }
}
