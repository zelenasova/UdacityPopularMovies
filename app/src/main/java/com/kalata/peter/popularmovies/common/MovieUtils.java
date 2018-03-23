package com.kalata.peter.popularmovies.common;

import android.database.Cursor;
import android.net.NetworkInfo;
import android.provider.UserDictionary;

import com.kalata.peter.popularmovies.data.remote.models.Movie;

import java.util.ArrayList;
import java.util.List;

import static com.kalata.peter.popularmovies.data.local.MovieContract.MovieEntry.COLUMN_MOVIE_ID;
import static com.kalata.peter.popularmovies.data.local.MovieContract.MovieEntry.COLUMN_OVERVIEW;
import static com.kalata.peter.popularmovies.data.local.MovieContract.MovieEntry.COLUMN_POSTER_PATH;
import static com.kalata.peter.popularmovies.data.local.MovieContract.MovieEntry.COLUMN_RELEASE_DATE;
import static com.kalata.peter.popularmovies.data.local.MovieContract.MovieEntry.COLUMN_TITLE;
import static com.kalata.peter.popularmovies.data.local.MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE;

public class MovieUtils {

    public static List<Movie> getMoviesFromCursor(Cursor cursor) {
        List<Movie> movies = new ArrayList<>();
        try {
            int idColumn = cursor.getColumnIndex(COLUMN_MOVIE_ID);
            int titleColumn = cursor.getColumnIndex(COLUMN_TITLE);
            int overviewColumn = cursor.getColumnIndex(COLUMN_OVERVIEW);
            int posterPathColumn = cursor.getColumnIndex(COLUMN_POSTER_PATH);
            int voteAverageColumn = cursor.getColumnIndex(COLUMN_VOTE_AVERAGE);
            int releaseDateColumn = cursor.getColumnIndex(COLUMN_RELEASE_DATE);

            while (cursor.moveToNext()) {

                int id = cursor.getInt(idColumn);
                String title = cursor.getString(titleColumn);
                String overview = cursor.getString(overviewColumn);
                String posterPath= cursor.getString(posterPathColumn);
                String voteAverage= cursor.getString(voteAverageColumn);
                String releaseDate = cursor.getString(releaseDateColumn);
                movies.add(new Movie(id, title, overview, posterPath, voteAverage, releaseDate));
            }

        } finally {
            cursor.close();
        }
        return movies;
    }

}
