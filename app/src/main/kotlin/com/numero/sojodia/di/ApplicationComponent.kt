package com.numero.sojodia.di

import com.numero.sojodia.activity.MainActivity
import com.numero.sojodia.activity.SettingsActivity
import com.numero.sojodia.service.UpdateBusDataService

import javax.inject.Singleton

import dagger.Component

@Singleton
@Component(modules = [(ApplicationModule::class), (ApiModule::class), (RepositoryModule::class)])
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(settingsActivity: SettingsActivity)

    fun inject(service: UpdateBusDataService)

}
