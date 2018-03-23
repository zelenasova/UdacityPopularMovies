package com.kalata.peter.popularmovies;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.kalata.peter.popularmovies.injection.ApiModule;
import com.kalata.peter.popularmovies.injection.AppComponent;
import com.kalata.peter.popularmovies.injection.AppModule;
import com.kalata.peter.popularmovies.injection.DaggerAppComponent;

public class App extends Application {

    private AppComponent mAppComponent;
    private static App sInstance;

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    public static AppComponent getAppComponent() {
        return sInstance.mAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        initDagger();
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }

    private void initDagger() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .apiModule(new ApiModule())
                    .build();
        }
    }

}
