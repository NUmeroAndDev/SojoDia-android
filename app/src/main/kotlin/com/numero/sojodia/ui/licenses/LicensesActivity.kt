package com.numero.sojodia.ui.licenses

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.numero.sojodia.databinding.ActivityLicensesBinding

class LicensesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLicensesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLicensesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.webView.apply {
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
