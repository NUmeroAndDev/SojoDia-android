package com.numero.sojodia.di;

import com.numero.sojodia.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);

}
