package com.kalata.peter.popularmovies.data.repository;


import com.kalata.peter.popularmovies.data.remote.models.Movie;
import com.kalata.peter.popularmovies.data.remote.models.Review;
import com.kalata.peter.popularmovies.data.remote.models.Trailer;

import java.util.List;

import io.reactivex.Single;


public interface MovieRepository {

    Single<List<Movie>> getMovies(String sortOrder, boolean isFav);

    Single<List<Trailer>> getTrailers(String movieId);

    Single<List<Review>> getReviews(String movieId);

}
