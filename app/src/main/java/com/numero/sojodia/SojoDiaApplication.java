package com.numero.sojodia;

import android.app.Application;

import com.numero.sojodia.di.ApplicationComponent;
import com.numero.sojodia.di.ApplicationModule;
import com.numero.sojodia.di.BusDataModule;
import com.numero.sojodia.di.DaggerApplicationComponent;

public class SojoDiaApplication extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .busDataModule(new BusDataModule())
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
