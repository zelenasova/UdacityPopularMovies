package com.kalata.peter.popularmovies.ui.main;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.preference.PreferenceManager;
import android.widget.Toast;

import com.kalata.peter.popularmovies.App;
import com.kalata.peter.popularmovies.R;
import com.kalata.peter.popularmovies.common.NetworkUtils;
import com.kalata.peter.popularmovies.common.SingleLiveEvent;
import com.kalata.peter.popularmovies.data.remote.models.Movie;
import com.kalata.peter.popularmovies.data.repository.MovieRepository;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

import static com.kalata.peter.popularmovies.data.local.MovieContract.MovieEntry.CONTENT_URI;


public class MainViewModel extends AndroidViewModel implements SharedPreferences.OnSharedPreferenceChangeListener{

    @Inject
    MovieRepository movieRepository;

    private final CompositeDisposable composite = new CompositeDisposable();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final SingleLiveEvent<Throwable> error = new SingleLiveEvent<>();
    private final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private ContentObserver contentObserver;
    private SharedPreferences sharedPreferences;

    public MainViewModel(Application application) {
        super(application);
        App.getAppComponent().inject(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplication());
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        loadMoviesFromApi(getSortOrder());
        registerContentObserver();

    }

    private void registerContentObserver() {
        contentObserver = new MyObserver(new Handler());
        getApplication().getContentResolver().registerContentObserver(
                CONTENT_URI,
                true,
                contentObserver);
    }

    private void loadMoviesFromApi(String sortOrder) {
        boolean isFav = isFavSortOrder(sortOrder);
        if (!NetworkUtils.isConnected() && !isFav) {
            error.setValue(new Throwable(getApplication().getString(R.string.error_internet)));
            movies.setValue(new ArrayList<>());
            return;
        }

        composite.add(movieRepository.getMovies(sortOrder, isFav)
                .doOnSubscribe(s -> isLoading.setValue(true))
                .doAfterTerminate(() -> isLoading.setValue(false))
                .subscribe(
                        movies::setValue,
                        error::setValue
                ));
    }

    private String getSortOrder() {
        return sharedPreferences.getString(getApplication().getString(R.string.pref_sort_order_key),
                getApplication().getString(R.string.pref_sort_order_most_popular_value));
    }

    private boolean isFavSortOrder(String sortOrder) {
        return sortOrder.equals(getApplication().getString(R.string.pref_sort_order_favorite_value));
    }

    LiveData<List<Movie>> getMovies() {
        return movies;
    }

    LiveData<Boolean> getLoadingStatus() {
        return isLoading;
    }

    SingleLiveEvent<Throwable> getError() {
        return error;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getApplication().getString(R.string.pref_sort_order_key))) {
            loadMoviesFromApi(sharedPreferences.getString(key, getApplication()
                    .getString(R.string.pref_sort_order_most_popular_value)));
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        composite.clear();
        PreferenceManager.getDefaultSharedPreferences(getApplication())
                .unregisterOnSharedPreferenceChangeListener(this);
        getApplication().getContentResolver().unregisterContentObserver(contentObserver);
    }

    private class MyObserver extends ContentObserver {

        public MyObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            String sortOrder = getSortOrder();
            if (isFavSortOrder(sortOrder)) {
                loadMoviesFromApi(sortOrder);
            }
        }
    }

}