package com.nikhilverma360.udacitypopularmovies.di.module;

import com.nikhilverma360.udacitypopularmovies.view.ui.detail.DetailActivity;
import com.nikhilverma360.udacitypopularmovies.view.ui.detail.reviews.ReviewsActivity;
import com.nikhilverma360.udacitypopularmovies.view.ui.detail.trailers.TrailerActivity;
import com.nikhilverma360.udacitypopularmovies.view.ui.home.HomeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract HomeActivity contributeHomeActivity();

    @ContributesAndroidInjector
    abstract DetailActivity contributeDetailActivity();

    @ContributesAndroidInjector
    abstract ReviewsActivity contributeReviewsActivity();

    @ContributesAndroidInjector
    abstract TrailerActivity contributeTrailerActivity();
}