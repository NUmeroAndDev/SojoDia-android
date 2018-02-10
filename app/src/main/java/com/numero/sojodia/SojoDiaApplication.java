package com.numero.sojodia;

import android.app.Application;

import com.numero.sojodia.di.ApiModule;
import com.numero.sojodia.di.ApplicationComponent;
import com.numero.sojodia.di.ApplicationModule;
import com.numero.sojodia.di.RepositoryModule;
import com.numero.sojodia.di.DaggerApplicationComponent;

public class SojoDiaApplication extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .repositoryModule(new RepositoryModule())
                .apiModule(new ApiModule())
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
