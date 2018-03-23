package com.kalata.peter.popularmovies.data.remote.api;


import com.kalata.peter.popularmovies.data.remote.models.Movie;
import com.kalata.peter.popularmovies.data.remote.models.MovieResponse;
import com.kalata.peter.popularmovies.data.remote.models.Review;
import com.kalata.peter.popularmovies.data.remote.models.ReviewResponse;
import com.kalata.peter.popularmovies.data.remote.models.Trailer;
import com.kalata.peter.popularmovies.data.remote.models.TrailerResponse;
import com.kalata.peter.popularmovies.data.remote.services.MovieService;

import java.util.List;

import io.reactivex.Single;

import static com.kalata.peter.popularmovies.BuildConfig.API_KEY_VALUE;


public class MovieApiImpl implements MovieApi {

    private final MovieService service;

    public MovieApiImpl(MovieService service) {
        this.service = service;
    }

    @Override
    public Single<List<Movie>> getMovies(String sortOrder) {
        Single<MovieResponse> apiResult = service.getMovies(sortOrder, API_KEY_VALUE);
        return apiResult.map(MovieResponse::getMovies);
    }

    @Override
    public Single<List<Trailer>> getMovieVideos(String movieId) {
        Single<TrailerResponse> apiResult = service.getMovieVideos(movieId, API_KEY_VALUE);
        return apiResult.map(TrailerResponse::getTrailers);
    }

    @Override
    public Single<List<Review>> getMovieReviews(String movieId) {
        Single<ReviewResponse> apiResult = service.getMovieReviews(movieId, API_KEY_VALUE);
        return apiResult.map(ReviewResponse::getReviews);
    }

}
