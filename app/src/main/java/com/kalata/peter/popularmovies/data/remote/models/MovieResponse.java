package com.kalata.peter.popularmovies.data.remote.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {

    @SerializedName("results")
    List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }
}
