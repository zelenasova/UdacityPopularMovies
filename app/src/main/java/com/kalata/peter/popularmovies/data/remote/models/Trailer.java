package com.kalata.peter.popularmovies.data.remote.models;


import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Trailer {

    @SerializedName("id")
    String id;

    @SerializedName("name")
    String name;

    @SerializedName("key")
    String key;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }
}
