package com.nikhilverma360.udacitypopularmovies.repo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nikhilverma360.udacitypopularmovies.api.ApiInterface;
import com.nikhilverma360.udacitypopularmovies.api.ApiResponse;
import com.nikhilverma360.udacitypopularmovies.api.model.CastResponse;
import com.nikhilverma360.udacitypopularmovies.api.model.GenreResponse;
import com.nikhilverma360.udacitypopularmovies.api.model.MoviesResponse;
import com.nikhilverma360.udacitypopularmovies.api.model.ReviewResponse;
import com.nikhilverma360.udacitypopularmovies.api.model.VideoResponse;
import com.nikhilverma360.udacitypopularmovies.database.dao.MoviesDao;
import com.nikhilverma360.udacitypopularmovies.database.entity.CastEntity;
import com.nikhilverma360.udacitypopularmovies.database.entity.FavMovieEntity;
import com.nikhilverma360.udacitypopularmovies.database.entity.GenreEntity;
import com.nikhilverma360.udacitypopularmovies.database.entity.MovieEntity;
import com.nikhilverma360.udacitypopularmovies.database.entity.ReviewEntity;
import com.nikhilverma360.udacitypopularmovies.database.entity.VideoEntity;
import com.nikhilverma360.udacitypopularmovies.utils.AbsentLiveData;
import com.nikhilverma360.udacitypopularmovies.utils.AppConstants;
import com.nikhilverma360.udacitypopularmovies.utils.AppExecutor;
import com.nikhilverma360.udacitypopularmovies.utils.AppUtils;
import com.nikhilverma360.udacitypopularmovies.utils.Resource;
import com.nikhilverma360.udacitypopularmovies.utils.SharedPreferenceHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppRepository implements AppRepositoryInterface {

    private final ApiInterface apiInterface;
    private final MoviesDao moviesDao;
    private final AppExecutor executor;

    @Inject
    public AppRepository(ApiInterface apiInterface, MoviesDao moviesDao, AppExecutor executor) {
        this.apiInterface = apiInterface;
        this.moviesDao = moviesDao;
        this.executor = executor;
    }

    @Override
    public LiveData<Resource<List<GenreEntity>>> loadGenres(List<Integer> genreIds) {
        return new NetworkBoundResource<List<GenreEntity>, GenreResponse>(executor) {
            @Override
            protected void saveCallResult(@NonNull GenreResponse item) {
                moviesDao.saveGenresToDb(item.getGenres());
            }

            @Override
            protected boolean shouldFetch(@Nullable List<GenreEntity> data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<GenreEntity>> loadFromDb() {
                return moviesDao.getGenresById(genreIds);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<GenreResponse>> createCall() {
                return apiInterface.getGenres(AppConstants.LANGUAGE);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<List<MovieEntity>>> loadMovies(boolean forceLoad, String sortBy) {
        return new NetworkBoundResource<List<MovieEntity>, MoviesResponse>(executor) {
            @Override
            protected void saveCallResult(@NonNull MoviesResponse item) {
                SharedPreferenceHelper.setSharedPreferenceLong(AppConstants.DATA_SAVED_TIME, new Date(System.currentTimeMillis()).getTime());
                moviesDao.deleteMovies();
                moviesDao.saveMoviesToDb(item.getResults());
            }

            @Override
            protected boolean shouldFetch(@Nullable List<MovieEntity> data) {
                return (((data == null || data.isEmpty()) && forceLoad && shouldFetchData(new Date(System.currentTimeMillis()).getTime()))
                        || (data == null || data.isEmpty())
                        || forceLoad
                        || shouldFetchData(new Date(System.currentTimeMillis()).getTime()));
            }

            @NonNull
            @Override
            protected LiveData<List<MovieEntity>> loadFromDb() {
                return moviesDao.loadFromDb();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<MoviesResponse>> createCall() {
                return apiInterface.getMovies(sortBy, AppConstants.LANGUAGE, AppConstants.PAGE);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<List<CastEntity>>> loadCast(int movieId) {
        return new NetworkBoundResource<List<CastEntity>, CastResponse>(executor) {
            private List<CastEntity> castList = new ArrayList<>();

            @Override
            protected void saveCallResult(@NonNull CastResponse item) {
                castList = item.getCast();
            }

            @Override
            protected boolean shouldFetch(@Nullable List<CastEntity> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<CastEntity>> loadFromDb() {
                if (castList == null) {
                    return AbsentLiveData.create();
                } else {
                    return new LiveData<List<CastEntity>>() {
                        @Override
                        protected void onActive() {
                            super.onActive();
                            setValue(castList);
                        }
                    };
                }
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<CastResponse>> createCall() {
                return apiInterface.getCast(movieId, AppConstants.LANGUAGE);
            }

        }.asLiveData();
    }

    @Override
    public LiveData<Resource<List<VideoEntity>>> loadVideos(int movieId) {
        return new NetworkBoundResource<List<VideoEntity>, VideoResponse>(executor) {
            private List<VideoEntity> videoResultsList = new ArrayList<>();

            @Override
            protected void saveCallResult(@NonNull VideoResponse item) {
                videoResultsList = item.getResults();
            }

            @Override
            protected boolean shouldFetch(@Nullable List<VideoEntity> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<VideoEntity>> loadFromDb() {
                if (videoResultsList == null) {
                    return AbsentLiveData.create();
                } else {
                    return new LiveData<List<VideoEntity>>() {
                        @Override
                        protected void onActive() {
                            super.onActive();
                            setValue(videoResultsList);
                        }
                    };
                }
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<VideoResponse>> createCall() {
                return apiInterface.getVideos(movieId, AppConstants.LANGUAGE);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<List<ReviewEntity>>> loadReviews(int movieId) {
        return new NetworkBoundResource<List<ReviewEntity>, ReviewResponse>(executor) {
            private List<ReviewEntity> reviewResultsList = new ArrayList<>();

            @Override
            protected void saveCallResult(@NonNull ReviewResponse item) {
                reviewResultsList = item.getResults();
            }

            @Override
            protected boolean shouldFetch(@Nullable List<ReviewEntity> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<ReviewEntity>> loadFromDb() {
                if (reviewResultsList == null) {
                    return AbsentLiveData.create();
                } else {
                    return new LiveData<List<ReviewEntity>>() {
                        @Override
                        protected void onActive() {
                            super.onActive();
                            setValue(reviewResultsList);
                        }
                    };
                }
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ReviewResponse>> createCall() {
                return apiInterface.getReviews(movieId, AppConstants.LANGUAGE);
            }
        }.asLiveData();
    }

    private Boolean shouldFetchData(Long time) {
        boolean shouldFetch;
        long savedTime;
        if (SharedPreferenceHelper.contains(AppConstants.DATA_SAVED_TIME)) {
            savedTime = SharedPreferenceHelper.getSharedPreferenceLong(AppConstants.DATA_SAVED_TIME, 0L);
            shouldFetch = (time - savedTime) > TimeUnit.MINUTES.toMillis(AppConstants.FRESH_TIMEOUT_IN_MINUTES);
        } else {
            shouldFetch = false;
        }

        if (!AppUtils.isNetworkAvailable()) {
            shouldFetch = false;
        }
        return shouldFetch;
    }

    @Override
    public LiveData<MovieEntity> loadMoviesById(int movieId) {
        return moviesDao.getMovieById(movieId);
    }

    @Override
    public void saveFavouriteMovie(FavMovieEntity favMovieEntity) {
        executor.diskIO().execute(() -> moviesDao.saveFavMovie(favMovieEntity));
    }

    @Override
    public void saveFavMovieCast(List<CastEntity> favMovieCastEntities) {
        executor.diskIO().execute(() -> moviesDao.saveFavMovieCasts(favMovieCastEntities));
    }

    @Override
    public void saveFavMovieReviews(List<ReviewEntity> favMovieReviewEntities) {
        executor.diskIO().execute(() -> moviesDao.saveFavReviews(favMovieReviewEntities));
    }

    @Override
    public void saveFavMovieVideos(List<VideoEntity> favMovieVideoEntities) {
        executor.diskIO().execute(() -> moviesDao.saveFavTrailers(favMovieVideoEntities));
    }

    @Override
    public LiveData<List<FavMovieEntity>> loadFavMoviesFromDb() {
        return moviesDao.loadFavMoviesFromDb();
    }

    @Override
    public LiveData<FavMovieEntity> loadFavMovieById(int movieId) {
        return moviesDao.loadFavMoviesById(movieId);
    }

    @Override
    public LiveData<List<CastEntity>> getCastsById(List<Integer> castIds) {
        return moviesDao.getCastsById(castIds);
    }

    @Override
    public LiveData<List<ReviewEntity>> getReviewsByMovie(int favMovieId) {
        return moviesDao.getReviewsByMovie(favMovieId);
    }

    @Override
    public LiveData<List<VideoEntity>> getVideosByMovie(int favMovieId) {
        return moviesDao.getVideosByMovie(favMovieId);
    }

    @Override
    public LiveData<Integer> deleteMovieById(int favMovieId) {
        MutableLiveData<Integer> liveData = new MutableLiveData<>();
        executor.diskIO().execute(() -> liveData.postValue(moviesDao.deleteMovieById(favMovieId)));
        return liveData;
    }
}
