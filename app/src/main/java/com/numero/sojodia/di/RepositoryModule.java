package com.numero.sojodia.di;

import android.content.Context;

import com.numero.sojodia.repository.BusDataRepository;
import com.numero.sojodia.repository.ConfigRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    public BusDataRepository provideBusDataRepository(Context context) {
        return new BusDataRepository(context);
    }

    @Provides
    @Singleton
    public ConfigRepository provideConfigRepository(Context context) {
        return new ConfigRepository(context);
    }
}
