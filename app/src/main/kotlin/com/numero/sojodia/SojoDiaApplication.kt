package com.numero.sojodia

import android.app.Application
import com.numero.sojodia.di.ApiModule
import com.numero.sojodia.di.ApplicationComponent
import com.numero.sojodia.di.ApplicationModule
import com.numero.sojodia.di.RepositoryModule

class SojoDiaApplication : Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .repositoryModule(RepositoryModule())
                .apiModule(ApiModule())
                .build()
    }
}
