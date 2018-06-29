package com.nikhilverma360.udacitypopularmovies.di.component;

import android.app.Application;

import com.nikhilverma360.udacitypopularmovies.MoviesApp;
import com.nikhilverma360.udacitypopularmovies.di.module.ActivityModule;
import com.nikhilverma360.udacitypopularmovies.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules={ActivityModule.class, AppModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(MoviesApp app);
}
