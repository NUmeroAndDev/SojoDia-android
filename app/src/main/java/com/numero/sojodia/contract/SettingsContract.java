package com.numero.sojodia.contract;

import com.numero.sojodia.presenter.IPresenter;
import com.numero.sojodia.view.IView;

public interface SettingsContract {

    interface View extends IView<Presenter> {

        void showBusDataVersion(String version);
    }

    interface Presenter extends IPresenter {
    }
}
