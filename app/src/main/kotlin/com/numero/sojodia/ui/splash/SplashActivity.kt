package com.numero.sojodia.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.numero.sojodia.R
import com.numero.sojodia.extension.module
import com.numero.sojodia.repository.BusDataRepository
import com.numero.sojodia.repository.ConfigRepository
import com.numero.sojodia.ui.board.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity(), SplashView {

    private val configRepository: ConfigRepository
        get() = module.configRepository
    private val busDataRepository: BusDataRepository
        get() = module.busDataRepository

    private lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        presenter = SplashPresenterImpl(this, busDataRepository, configRepository)

        retryButton.setOnClickListener {
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
        errorGroup.isVisible = true
        progress.isInvisible = true
    }

    private fun loadBusData() {
        errorGroup.isVisible = false
        progress.isInvisible = false
        presenter.subscribe()
    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, SplashActivity::class.java)
    }
}
