package com.kalata.peter.popularmovies.data.remote.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResponse {

    @SerializedName("results")
    List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }
}
