package com.numero.sojodia.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.numero.sojodia.databinding.ActivitySplashBinding
import com.numero.sojodia.extension.module
import com.numero.sojodia.repository.BusDataRepository
import com.numero.sojodia.repository.ConfigRepository
import com.numero.sojodia.ui.board.MainActivity
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding

class SplashActivity : AppCompatActivity(), SplashView {

    private val configRepository: ConfigRepository
        get() = module.configRepository
    private val busDataRepository: BusDataRepository
        get() = module.busDataRepository

    private lateinit var presenter: SplashPresenter
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = SplashPresenterImpl(this, busDataRepository, configRepository)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding.root.applySystemWindowInsetsToPadding(bottom = true, left = true, right = true)
        binding.retryButton.setOnClickListener {
            loadBusData()
        }
    }

    override fun onResume() {
        super.onResume()
        loadBusData()
    }

    override fun onPause() {
        super.onPause()
        presenter.unSubscribe()
    }

    override fun successDownloadedBusData() {
        startActivity(MainActivity.createClearTopIntent(this))
        finish()
    }

    override fun onError(throwable: Throwable) {
        binding.errorGroup.isVisible = true
        binding.progress.isInvisible = true
    }

    private fun loadBusData() {
        binding.errorGroup.isVisible = false
        binding.progress.isInvisible = false
        presenter.subscribe()
    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, SplashActivity::class.java)
    }
}
