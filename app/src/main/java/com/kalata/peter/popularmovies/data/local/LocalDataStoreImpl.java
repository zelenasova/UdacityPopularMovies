package com.kalata.peter.popularmovies.data.local;


import android.content.Context;
import android.database.Cursor;

import com.kalata.peter.popularmovies.common.MovieUtils;
import com.kalata.peter.popularmovies.data.remote.models.Movie;

import java.util.List;

import io.reactivex.Single;

import static com.kalata.peter.popularmovies.data.local.MovieContract.MovieEntry.CONTENT_URI;


public class LocalDataStoreImpl implements LocalDataStore {

    private Context appContext;

    public LocalDataStoreImpl(Context appContext) {
        this.appContext = appContext;
    }

    public Single<List<Movie>> getFavorites() {

        return Single.create(subscriber -> {
            Cursor cursor = appContext.getContentResolver().query(CONTENT_URI,
                    null,
                    null,
                    null,
                    null);

            subscriber.onSuccess(MovieUtils.getMoviesFromCursor(cursor));
        });
    }

}
