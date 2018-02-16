package com.numero.sojodia.contract

import com.numero.sojodia.presenter.IPresenter
import com.numero.sojodia.view.IView

interface SettingsContract {

    interface View : IView<Presenter> {

        fun showBusDataVersion(version: String)
    }

    interface Presenter : IPresenter
}
