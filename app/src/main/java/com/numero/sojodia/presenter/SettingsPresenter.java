package com.numero.sojodia.presenter;

import com.numero.sojodia.contract.SettingsContract;
import com.numero.sojodia.repository.ConfigRepository;

public class SettingsPresenter implements SettingsContract.Presenter {

    private final SettingsContract.View view;
    private final ConfigRepository configRepository;

    public SettingsPresenter(SettingsContract.View view, ConfigRepository configRepository) {
        this.view = view;
        this.configRepository = configRepository;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        view.showBusDataVersion(String.valueOf(configRepository.getVersionCode()));
    }

    @Override
    public void unSubscribe() {
    }
}
