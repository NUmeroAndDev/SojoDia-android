package com.numero.sojodia.component.library

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.numero.sojodia.repository.IConfigRepository
import com.numero.sojodia.repository.Theme
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

    private val Theme.styleRes: Int
        get() {
            return when (this) {
                Theme.DARK -> R.style.AppTheme_Dark_NoActionBar
                Theme.LIGHT -> R.style.AppTheme_Light_NoActionBar
            }
        }

    companion object {

        private const val LICENSES_HTML_PATH = "file:///android_asset/licenses.html"
    }
}
