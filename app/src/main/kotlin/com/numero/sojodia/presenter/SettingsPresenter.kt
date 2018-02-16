package com.numero.sojodia.presenter

import com.numero.sojodia.contract.SettingsContract
import com.numero.sojodia.repository.IConfigRepository

class SettingsPresenter(private val view: SettingsContract.View, private val configRepository: IConfigRepository) : SettingsContract.Presenter {

    init {
        this.view.setPresenter(this)
    }

    override fun subscribe() {
        view.showBusDataVersion(configRepository.versionCode.toString())
    }

    override fun unSubscribe() {}
}
