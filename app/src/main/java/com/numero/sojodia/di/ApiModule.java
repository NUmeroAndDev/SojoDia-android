package com.numero.sojodia.di;

import com.numero.sojodia.BuildConfig;
import com.numero.sojodia.api.BusDataApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class ApiModule {

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        return builder.build();
    }

    @Provides
    @Singleton
    public BusDataApi provideBusDataApi(OkHttpClient okHttpClient) {
        return new BusDataApi(okHttpClient);
    }

}
