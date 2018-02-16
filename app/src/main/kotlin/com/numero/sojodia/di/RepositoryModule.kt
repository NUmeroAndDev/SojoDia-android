package com.numero.sojodia.di

import android.content.Context
import com.numero.sojodia.repository.BusDataRepository
import com.numero.sojodia.repository.ConfigRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideBusDataRepository(context: Context): BusDataRepository = BusDataRepository(context)

    @Provides
    @Singleton
    fun provideConfigRepository(context: Context): ConfigRepository = ConfigRepository(context)
}
