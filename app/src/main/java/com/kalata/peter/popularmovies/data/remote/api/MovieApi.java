package com.kalata.peter.popularmovies.data.remote.api;


import com.kalata.peter.popularmovies.data.remote.models.Movie;
import com.kalata.peter.popularmovies.data.remote.models.Review;
import com.kalata.peter.popularmovies.data.remote.models.Trailer;

import java.util.List;

import io.reactivex.Single;

public interface MovieApi {

    Single<List<Movie>> getMovies(String sortOrder);

    Single<List<Trailer>> getMovieVideos(String movieId);

    Single<List<Review>> getMovieReviews(String movieId);

}