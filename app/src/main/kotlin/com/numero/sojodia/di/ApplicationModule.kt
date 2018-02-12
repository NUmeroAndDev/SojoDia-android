package com.numero.sojodia.di

import android.app.Application
import android.content.Context

import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    fun provideApplicationContext(): Context = application.applicationContext
}
