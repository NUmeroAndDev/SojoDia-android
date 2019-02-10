package com.numero.sojodia.component.library

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.numero.common.extension.module
import com.numero.common.extension.styleRes
import com.numero.sojodia.repository.IConfigRepository
import kotlinx.android.synthetic.main.activity_licenses.*

class LicensesActivity : AppCompatActivity() {

    private val configRepository: IConfigRepository
        get() = module.configRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(configRepository.theme.styleRes)
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
    }
}