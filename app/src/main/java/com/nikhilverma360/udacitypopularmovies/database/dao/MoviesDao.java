package com.nikhilverma360.udacitypopularmovies.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.nikhilverma360.udacitypopularmovies.database.entity.CastEntity;
import com.nikhilverma360.udacitypopularmovies.database.entity.FavMovieEntity;
import com.nikhilverma360.udacitypopularmovies.database.entity.ReviewEntity;
import com.nikhilverma360.udacitypopularmovies.database.entity.VideoEntity;
import com.nikhilverma360.udacitypopularmovies.database.entity.GenreEntity;
import com.nikhilverma360.udacitypopularmovies.database.entity.MovieEntity;

import java.util.List;

@Dao
public interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveMoviesToDb(List<MovieEntity> movieList);

    @Query("SELECT * FROM movies ORDER BY _id ASC")
    LiveData<List<MovieEntity>> loadFromDb();

    @Query("DELETE FROM movies")
    void deleteMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveGenresToDb(List<GenreEntity> genreList);

    @Query("SELECT * FROM genres WHERE genreId IN (:genreIds)")
    LiveData<List<GenreEntity>> getGenresById(List<Integer> genreIds);

    @Query("SELECT * FROM movies WHERE movieId =:movieId")
    LiveData<MovieEntity> getMovieById(int movieId);

    // Click on favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveFavMovie(FavMovieEntity favMovieEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveFavMovieCasts(List<CastEntity> castList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveFavReviews(List<ReviewEntity> movieReviewList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveFavTrailers(List<VideoEntity> movieVideoList);

    // Get from Favorite tables

    @Query("SELECT * FROM fav_movies ORDER BY createdAt ASC")
    LiveData<List<FavMovieEntity>> loadFavMoviesFromDb();

    @Query("SELECT * FROM fav_movies WHERE movieId =:favMovieId")
    LiveData<FavMovieEntity> loadFavMoviesById(int favMovieId);

    @Query("SELECT * FROM fav_movies_cast WHERE id IN (:castIds)")
    LiveData<List<CastEntity>> getCastsById(List<Integer> castIds);

    @Query("SELECT * FROM fav_movies_reviews where fav_movie_id = :favMovieId")
    LiveData<List<ReviewEntity>> getReviewsByMovie(int favMovieId);

    @Query("SELECT * FROM fav_movies_videos where fav_movie_id = :favMovieId")
    LiveData<List<VideoEntity>> getVideosByMovie(int favMovieId);

    // Click on remove from favourite

    @Query("DELETE FROM fav_movies WHERE movieId = :favMovieId")
    int deleteMovieById(int favMovieId);

}
