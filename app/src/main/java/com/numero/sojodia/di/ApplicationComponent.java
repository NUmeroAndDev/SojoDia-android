package com.numero.sojodia.di;

import com.numero.sojodia.activity.MainActivity;
import com.numero.sojodia.service.UpdateBusDataService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class, RepositoryModule.class})
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);

    void inject(UpdateBusDataService service);

}
