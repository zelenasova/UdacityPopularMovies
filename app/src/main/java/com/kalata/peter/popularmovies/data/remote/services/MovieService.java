package com.kalata.peter.popularmovies.data.remote.services;


import com.kalata.peter.popularmovies.data.remote.models.MovieResponse;
import com.kalata.peter.popularmovies.data.remote.models.ReviewResponse;
import com.kalata.peter.popularmovies.data.remote.models.TrailerResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    String API_KEY = "api_key";

    @GET("{sort_order}")
    Single<MovieResponse> getMovies(
            @Path("sort_order") String sortOrder,
            @Query(API_KEY) String apiKey
    );

    @GET("{id}/videos")
    Single<TrailerResponse> getMovieVideos(
            @Path("id") String id,
            @Query(API_KEY) String apiKey
    );

    @GET("{id}/reviews")
    Single<ReviewResponse> getMovieReviews(
            @Path("id") String id,
            @Query(API_KEY) String apiKey
    );
}
