package com.kalata.peter.popularmovies.ui.detail;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.kalata.peter.popularmovies.App;
import com.kalata.peter.popularmovies.R;
import com.kalata.peter.popularmovies.common.NetworkUtils;
import com.kalata.peter.popularmovies.data.remote.models.Review;
import com.kalata.peter.popularmovies.data.remote.models.Trailer;
import com.kalata.peter.popularmovies.data.repository.MovieRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;


public class DetailViewModel extends AndroidViewModel {

    @Inject
    MovieRepository movieRepository;

    private final CompositeDisposable composite = new CompositeDisposable();
    private final MutableLiveData<Throwable> error = new MutableLiveData<>();
    private MutableLiveData<List<Trailer>> trailers;
    private MutableLiveData<List<Review>> reviews;

    public DetailViewModel(Application application) {
        super(application);
        App.getAppComponent().inject(this);
    }

    LiveData<List<Trailer>> getTrailers(String movieId) {
        if (trailers == null) {
            trailers = new MutableLiveData<>();
            if (!NetworkUtils.isConnected()) {
                error.setValue(new Throwable(getApplication().getString(R.string.error_internet)));
            } else {
                composite.add(movieRepository.getTrailers(movieId)
                        .subscribe(
                                trailers::setValue,
                                error::setValue
                        ));
            }

        }
        return trailers;
    }

    LiveData<List<Review>> getReviews(String movieId) {
        if (reviews == null) {
            reviews = new MutableLiveData<>();
            if (!NetworkUtils.isConnected()) {
                error.setValue(new Throwable(getApplication().getString(R.string.error_internet)));
            } else {
                composite.add(movieRepository.getReviews(movieId)
                        .subscribe(
                                reviews::setValue,
                                error::setValue
                        ));
            }

        }
        return reviews;
    }

    LiveData<Throwable> getError() {
        return error;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        composite.clear();
    }
}