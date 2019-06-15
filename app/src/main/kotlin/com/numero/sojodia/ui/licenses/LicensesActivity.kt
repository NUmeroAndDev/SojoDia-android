package com.numero.sojodia.ui.licenses

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.numero.sojodia.R
import com.numero.sojodia.extension.module
import com.numero.sojodia.extension.applyAppTheme
import com.numero.sojodia.repository.ConfigRepository
import kotlinx.android.synthetic.main.activity_licenses.*

class LicensesActivity : AppCompatActivity() {

    private val configRepository: ConfigRepository
        get() = module.configRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyAppTheme(configRepository.appTheme)
        setContentView(R.layout.activity_licenses)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        webView.apply {
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            loadUrl(LICENSES_HTML_PATH)
        }
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

        private const val LICENSES_HTML_PATH = "file:///android_asset/licenses.html"

        fun createIntent(context: Context): Intent = Intent(context, LicensesActivity::class.java)
    }
}
