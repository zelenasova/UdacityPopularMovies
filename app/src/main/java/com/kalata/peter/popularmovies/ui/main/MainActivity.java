package com.kalata.peter.popularmovies.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.kalata.peter.popularmovies.R;
import com.kalata.peter.popularmovies.ui.detail.DetailActivity;
import com.kalata.peter.popularmovies.ui.dialogs.ErrorDialogFragment;
import com.kalata.peter.popularmovies.data.remote.models.Movie;
import com.kalata.peter.popularmovies.ui.settings.SettingsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ActionListener {

    @BindView(R.id.rv_movie_list)
    RecyclerView rvMovieList;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupRecyclerView();
        setupViewModel();

    }

    private void setupRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvMovieList.setLayoutManager(layoutManager);
        adapter = new MovieAdapter(this);
        rvMovieList.setAdapter(adapter);
    }

    private void updateLoadingVisibility(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    private void setupViewModel() {
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getMovies().observe(this, movies -> adapter.setMovies(movies));
        mainViewModel.getLoadingStatus().observe(this, this::updateLoadingVisibility);
        mainViewModel.getError().observe(this, throwable -> {
            ErrorDialogFragment.newInstance(
                    throwable != null ? throwable.getMessage() : null).show(getSupportFragmentManager(), null);
        });
    }

    @Override
    public void onMovieClick(Movie movie) {
        DetailActivity.startDetailActivity(this, movie);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pop_movies_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
