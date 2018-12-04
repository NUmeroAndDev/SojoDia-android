package com.numero.sojodia.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.numero.sojodia.R
import com.numero.sojodia.extension.app
import com.numero.sojodia.presenter.ISplashPresenter
import com.numero.sojodia.presenter.SplashPresenter
import com.numero.sojodia.repository.IBusDataRepository
import com.numero.sojodia.repository.IConfigRepository
import com.numero.sojodia.view.ISplashView

class SplashActivity : AppCompatActivity(), ISplashView {

    private val configRepository: IConfigRepository
        get() = app.configRepository
    private val busDataRepository: IBusDataRepository
        get() = app.busDataRepository

    private lateinit var presenter: ISplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(configRepository.themeRes)
        setContentView(R.layout.activity_splash)

        presenter = SplashPresenter(this, busDataRepository, configRepository)
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
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
        // TODO エラーハンドリング
    }

    companion object {
        fun createIntent(context: Context): Intent = Intent(context, SplashActivity::class.java)
    }
}
