package com.nikhilverma360.udacitypopularmovies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.nikhilverma360.udacitypopularmovies.database.converter.IntegerListConvertor;
import com.nikhilverma360.udacitypopularmovies.database.dao.MoviesDao;
import com.nikhilverma360.udacitypopularmovies.database.entity.CastEntity;
import com.nikhilverma360.udacitypopularmovies.database.entity.FavMovieEntity;
import com.nikhilverma360.udacitypopularmovies.database.entity.ReviewEntity;
import com.nikhilverma360.udacitypopularmovies.database.entity.VideoEntity;
import com.nikhilverma360.udacitypopularmovies.database.entity.GenreEntity;
import com.nikhilverma360.udacitypopularmovies.database.entity.MovieEntity;

@Database(entities = {
        MovieEntity.class,
        GenreEntity.class,
        FavMovieEntity.class,
        CastEntity.class,
        ReviewEntity.class,
        VideoEntity.class},
        version = 1, exportSchema = false)
@TypeConverters({IntegerListConvertor.class})
public abstract class MoviesDb extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile MoviesDb INSTANCE;

    // --- DAO ---
    public abstract MoviesDao moviesDao();

}
