package com.kalata.peter.popularmovies.injection;

import android.content.Context;

import com.kalata.peter.popularmovies.data.local.LocalDataStore;
import com.kalata.peter.popularmovies.data.local.LocalDataStoreImpl;
import com.kalata.peter.popularmovies.data.remote.api.MovieApi;
import com.kalata.peter.popularmovies.data.repository.MovieRepository;
import com.kalata.peter.popularmovies.data.repository.MovieRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context mAppContext;

    public AppModule(Context appContext) {
        this.mAppContext = appContext;
    }

    @Provides
    @Singleton
    Context provideAppContext() {
        return mAppContext;
    }


    @Provides
    @Singleton
    MovieRepository providesMovieRepository(MovieApi movieApi, LocalDataStore localDataStore) {
        return new MovieRepositoryImpl(movieApi, localDataStore);
    }

    @Provides
    @Singleton
    LocalDataStore providesLocalDataSource(Context appContext) {
        return new LocalDataStoreImpl(appContext);
    }

}
