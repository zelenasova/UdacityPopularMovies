package com.kalata.peter.popularmovies.data.remote.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerResponse {

    @SerializedName("results")
    List<Trailer> trailers;

    public List<Trailer> getTrailers() {
        return trailers;
    }
}
