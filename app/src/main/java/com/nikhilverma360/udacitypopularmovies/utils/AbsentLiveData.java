package com.nikhilverma360.udacitypopularmovies.utils;

import android.arch.lifecycle.LiveData;

public class AbsentLiveData extends LiveData {

    private AbsentLiveData() {
        postValue(null);
    }

    public static <T> LiveData<T> create() {
        //noinspection unchecked
        return new AbsentLiveData();
    }
}
