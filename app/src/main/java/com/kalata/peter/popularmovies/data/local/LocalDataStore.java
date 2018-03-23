package com.kalata.peter.popularmovies.data.local;


import android.database.Cursor;

import com.kalata.peter.popularmovies.data.remote.models.Movie;

import java.util.List;

import io.reactivex.Single;


public interface LocalDataStore {

    Single<List<Movie>> getFavorites();

}
