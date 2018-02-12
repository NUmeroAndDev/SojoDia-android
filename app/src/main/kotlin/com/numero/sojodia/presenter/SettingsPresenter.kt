package com.numero.sojodia.presenter

import com.numero.sojodia.contract.SettingsContract
import com.numero.sojodia.repository.ConfigRepository

class SettingsPresenter(private val view: SettingsContract.View, private val configRepository: ConfigRepository) : SettingsContract.Presenter {

    init {
        this.view.setPresenter(this)
    }

    override fun subscribe() {
        view.showBusDataVersion(configRepository.versionCode.toString())
    }

    override fun unSubscribe() {}
}
