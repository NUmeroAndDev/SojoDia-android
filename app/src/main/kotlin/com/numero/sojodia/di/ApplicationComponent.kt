package com.numero.sojodia.di

import com.numero.sojodia.activity.MainActivity
import com.numero.sojodia.activity.SettingsActivity
import com.numero.sojodia.fragment.BusScheduleFragment
import com.numero.sojodia.fragment.TimeTableDialogFragment
import com.numero.sojodia.service.UpdateBusDataService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    (ApplicationModule::class),
    (ApiModule::class),
    (RepositoryModule::class)
])
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(busScheduleFragment: BusScheduleFragment)

    fun inject(timeTableDialogFragment: TimeTableDialogFragment)

    fun inject(settingsActivity: SettingsActivity)

    fun inject(service: UpdateBusDataService)

}
