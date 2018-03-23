package com.kalata.peter.popularmovies.injection;

import com.kalata.peter.popularmovies.ui.detail.DetailViewModel;
import com.kalata.peter.popularmovies.ui.main.MainViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface AppComponent {

    void inject(MainViewModel viewModel);

    void inject(DetailViewModel viewModel);

}