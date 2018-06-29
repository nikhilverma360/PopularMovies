package com.nikhilverma360.udacitypopularmovies.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.nikhilverma360.udacitypopularmovies.di.interfaces.ViewModelKey;
import com.nikhilverma360.udacitypopularmovies.factory.ViewModelFactory;
import com.nikhilverma360.udacitypopularmovies.view.ui.detail.DetailViewModel;
import com.nikhilverma360.udacitypopularmovies.view.ui.home.HomeViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindHomeViewModel(HomeViewModel homeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel.class)
    abstract ViewModel bindDetailViewModel(DetailViewModel detailViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
