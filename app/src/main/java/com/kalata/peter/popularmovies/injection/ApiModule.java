package com.kalata.peter.popularmovies.injection;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kalata.peter.popularmovies.BuildConfig;
import com.kalata.peter.popularmovies.data.remote.api.MovieApi;
import com.kalata.peter.popularmovies.data.remote.api.MovieApiImpl;
import com.kalata.peter.popularmovies.data.remote.services.MovieService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.kalata.peter.popularmovies.common.Constants.BASE_URL;

@Module
public class ApiModule {

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            client.addNetworkInterceptor(new StethoInterceptor());
        }
        return client.build();
    }


    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().setLenient().create();
    }


    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    MovieApi provideMovieApi(Retrofit retrofit) {
        return new MovieApiImpl(retrofit.create(MovieService.class));
    }

}
