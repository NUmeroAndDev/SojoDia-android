package com.numero.sojodia.di;

import android.content.Context;

import com.numero.sojodia.repository.BusDataRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class BusDataModule {

    @Provides
    @Singleton
    public BusDataRepository provideBusDataRepository(Context context) {
        return new BusDataRepository(context);
    }
}
