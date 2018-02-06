package com.numero.sojodia.di;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    public Context provideApplicationContext() {
        return application.getApplicationContext();
    }
}
