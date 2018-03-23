package com.kalata.peter.popularmovies.data.remote.models;


import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Movie {

    @SerializedName("original_title")
    String originalTitle;

    @SerializedName("id")
    long id;

    @SerializedName("overview")
    String overview;

    @SerializedName("poster_path")
    String posterPath;

    @SerializedName("vote_average")
    String vote_average;

    @SerializedName("release_date")
    String releaseDate;

    public Movie() {}

    public Movie(long id, String originalTitle, String overview, String posterPath, String vote_average, String releaseDate) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.posterPath = posterPath;
        this.vote_average = vote_average;
        this.releaseDate = releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public long getId() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

}
