package com.nikhilverma360.udacitypopularmovies.repo;

import android.arch.lifecycle.LiveData;

import com.nikhilverma360.udacitypopularmovies.database.entity.CastEntity;
import com.nikhilverma360.udacitypopularmovies.database.entity.FavMovieEntity;
import com.nikhilverma360.udacitypopularmovies.database.entity.GenreEntity;
import com.nikhilverma360.udacitypopularmovies.database.entity.MovieEntity;
import com.nikhilverma360.udacitypopularmovies.database.entity.ReviewEntity;
import com.nikhilverma360.udacitypopularmovies.database.entity.VideoEntity;
import com.nikhilverma360.udacitypopularmovies.utils.Resource;

import java.util.List;

public interface AppRepositoryInterface {
    LiveData<Resource<List<GenreEntity>>> loadGenres(List<Integer> genreIds);

    LiveData<Resource<List<MovieEntity>>> loadMovies(boolean forceLoad, String sortBy);

    LiveData<MovieEntity> loadMoviesById(int movieId);

    LiveData<Resource<List<CastEntity>>> loadCast(int movieId);

    LiveData<Resource<List<VideoEntity>>> loadVideos(int movieId);

    LiveData<Resource<List<ReviewEntity>>> loadReviews(int movieId);

    void saveFavouriteMovie(FavMovieEntity favMovieEntity);

    void saveFavMovieCast(List<CastEntity> favMovieCastEntities);

    void saveFavMovieReviews(List<ReviewEntity> favMovieReviewEntities);

    void saveFavMovieVideos(List<VideoEntity> favMovieVideoEntities);

    LiveData<List<FavMovieEntity>> loadFavMoviesFromDb();

    LiveData<FavMovieEntity> loadFavMovieById(int movieId);

    LiveData<List<CastEntity>> getCastsById(List<Integer> castIds);

    LiveData<List<ReviewEntity>> getReviewsByMovie(int favMovieId);

    LiveData<List<VideoEntity>> getVideosByMovie(int favMovieId);

    LiveData<Integer> deleteMovieById(int favMovieId);
}
