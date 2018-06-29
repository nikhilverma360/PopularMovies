package com.nikhilverma360.udacitypopularmovies.view.ui.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.nikhilverma360.udacitypopularmovies.database.entity.FavMovieEntity;
import com.nikhilverma360.udacitypopularmovies.database.entity.MovieEntity;
import com.nikhilverma360.udacitypopularmovies.repo.AppRepository;
import com.nikhilverma360.udacitypopularmovies.utils.Resource;

import java.util.List;

import javax.inject.Inject;

public class HomeViewModel extends ViewModel {

    private AppRepository moviesRepo;

    @Inject
    HomeViewModel(AppRepository moviesRepo) {
        this.moviesRepo = moviesRepo;
    }

    LiveData<Resource<List<MovieEntity>>> loadMovies(boolean forceLoad, String sortBy){
        return moviesRepo.loadMovies(forceLoad, sortBy);
    }

    LiveData<List<FavMovieEntity>> loadFavMoviesFromDb(){
        return moviesRepo.loadFavMoviesFromDb();
    }
}
