package com.kalata.peter.popularmovies.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kalata.peter.popularmovies.R;
import com.kalata.peter.popularmovies.data.local.MovieContract;
import com.kalata.peter.popularmovies.data.remote.models.Movie;
import com.kalata.peter.popularmovies.data.remote.models.Trailer;
import com.kalata.peter.popularmovies.ui.dialogs.ErrorDialogFragment;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kalata.peter.popularmovies.common.Constants.PICTURE_SMALL_BASE_URL;
import static com.kalata.peter.popularmovies.common.Constants.TRAILER_APP_VND;
import static com.kalata.peter.popularmovies.common.Constants.TRAILER_WATCH_URL;
import static com.kalata.peter.popularmovies.data.local.MovieContract.MovieEntry.CONTENT_URI;

public class DetailActivity extends AppCompatActivity implements TrailerAdapter.ActionListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_release_date_value)
    TextView tvReleaseDate;
    @BindView(R.id.tv_rating_value)
    TextView tvRating;
    @BindView(R.id.btn_fav)
    Button btnFav;
    @BindView(R.id.tv_overview)
    TextView tvOverview;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.rv_trailers)
    RecyclerView rvTrailers;
    @BindView(R.id.rv_reviews)
    RecyclerView rvReviews;

    public static final String EXTRA_MOVIE = "extra_movie";
    private static final int ID_DETAIL_LOADER = 0;

    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;
    private Movie movie;
    private Uri detailUri;
    private boolean isFavorite = false;

    public static void startDetailActivity(Context context, Movie movie) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, Parcels.wrap(movie));
        context.startActivity(intent);
    }

    private Movie getMovie() {
        return Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_MOVIE));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        movie = getMovie();
        populateUI(movie);
        setupRecyclerViews();
        setupViewModel();

        detailUri = CONTENT_URI.buildUpon()
                .appendPath(Long.toString(movie.getId()))
                .build();
        getSupportLoaderManager().initLoader(ID_DETAIL_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle loaderArgs) {

        switch (loaderId) {

            case ID_DETAIL_LOADER:

                return new CursorLoader(this,
                        detailUri,
                        null,
                        null,
                        null,
                        null);

            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }
    }

    private void setupViewModel() {
        DetailViewModel detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        String movieId = String.valueOf(movie.getId());
        detailViewModel.getTrailers(movieId).observe(this, trailers -> trailerAdapter.setTrailers(trailers));
        detailViewModel.getReviews(movieId).observe(this, reviews -> reviewAdapter.setReviews(reviews));
        detailViewModel.getError().observe(this, throwable -> ErrorDialogFragment.newInstance(
                throwable != null ? throwable.getMessage() : null).show(getSupportFragmentManager(), null));
    }

    private void setupRecyclerViews() {

        rvTrailers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        trailerAdapter = new TrailerAdapter(this);
        rvTrailers.setAdapter(trailerAdapter);
        rvTrailers.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        rvReviews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        reviewAdapter = new ReviewAdapter();
        rvReviews.setAdapter(reviewAdapter);
        rvReviews.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    private void populateUI(Movie movie) {
        tvReleaseDate.setText(movie.getReleaseDate());
        tvTitle.setText(movie.getOriginalTitle());
        tvRating.setText(movie.getVote_average());
        tvOverview.setText(movie.getOverview());
        Glide.with(this)
                .load(PICTURE_SMALL_BASE_URL + movie.getPosterPath())
                .placeholder(R.drawable.poster_placeholder)
                .error(R.drawable.error)
                .centerCrop()
                .into(ivCover);
    }

    @Override
    public void onTrailerClick(Trailer trailer) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(TRAILER_APP_VND + trailer.getKey()));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(TRAILER_WATCH_URL + trailer.getKey()));
        if (appIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(appIntent);
        } else if (webIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(webIntent);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data != null && data.moveToFirst()) {
            isFavorite = true;
            btnFav.setText(R.string.remove_from_favorite);
        } else {
            isFavorite = false;
            btnFav.setText(R.string.mark_as_favorite);
        }

    }

    @OnClick(R.id.btn_fav)
    void onFavBtnClick() {
        if (isFavorite) {
            int movieDeleted = getContentResolver().delete(detailUri, null, null);
            if (movieDeleted != 0) {
                isFavorite = false;
                btnFav.setText(R.string.mark_as_favorite);
            }
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movie.getId());
            contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getOriginalTitle());
            contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
            contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
            contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVote_average());
            contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
            Uri uri = getContentResolver().insert(CONTENT_URI, contentValues);

            if (uri != null) {
                isFavorite = true;
                btnFav.setText(R.string.remove_from_favorite);
            }
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
