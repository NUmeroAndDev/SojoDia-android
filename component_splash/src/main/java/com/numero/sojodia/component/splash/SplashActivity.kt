package com.numero.sojodia.component.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.numero.common.IIntentResolver
import com.numero.common.extension.module
import com.numero.common.extension.styleRes
import com.numero.sojodia.repository.IBusDataRepository
import com.numero.sojodia.repository.IConfigRepository
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity(), ISplashView {

    private val configRepository: IConfigRepository
        get() = module.configRepository
    private val busDataRepository: IBusDataRepository
        get() = module.busDataRepository
    private val intentResolver: IIntentResolver
        get() = module.intentResolver

    private lateinit var presenter: ISplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(configRepository.theme.styleRes)
        setContentView(R.layout.activity_splash)

        presenter = SplashPresenter(this, busDataRepository, configRepository)

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
        val intent = intentResolver.getMainActivityIntent().apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        startActivity(intent)
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
}