package com.kalata.peter.popularmovies.data.repository;


import com.kalata.peter.popularmovies.App;
import com.kalata.peter.popularmovies.R;
import com.kalata.peter.popularmovies.data.local.LocalDataStore;
import com.kalata.peter.popularmovies.data.remote.api.MovieApi;
import com.kalata.peter.popularmovies.data.remote.models.Movie;
import com.kalata.peter.popularmovies.data.remote.models.Review;
import com.kalata.peter.popularmovies.data.remote.models.Trailer;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MovieRepositoryImpl implements MovieRepository {

    private final MovieApi movieApi;
    private final LocalDataStore localDataStore;

    public MovieRepositoryImpl(MovieApi movieApi, LocalDataStore localDataStore) {
        this.movieApi = movieApi;
        this.localDataStore = localDataStore;
    }

    @Override
    public Single<List<Movie>> getMovies(String sortOrder, boolean isFav) {
        if (isFav) {
            return localDataStore.getFavorites()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        } else {
            return movieApi.getMovies(sortOrder)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

    }

    @Override
    public Single<List<Trailer>> getTrailers(String movieId) {

        return movieApi.getMovieVideos(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<List<Review>> getReviews(String movieId) {

        return movieApi.getMovieReviews(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
