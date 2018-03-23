package com.kalata.peter.popularmovies.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kalata.peter.popularmovies.R;
import com.kalata.peter.popularmovies.data.remote.models.Movie;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.kalata.peter.popularmovies.common.Constants.PICTURE_BASE_URL;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_cover)
    ImageView ivCover;

    private final MovieAdapter.ActionListener actionListener;
    private Movie movie;

    MovieViewHolder(View itemView, final MovieAdapter.ActionListener actionListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.actionListener = actionListener;
    }

    public void bindData(Movie movie) {
        this.movie = movie;
        setupImage();
    }

    private void setupImage() {
        Glide.with(itemView.getContext())
                .load(PICTURE_BASE_URL + movie.getPosterPath())
                .error(R.drawable.error)
                .placeholder(R.color.colorPrimary)
                .centerCrop()
                .into(ivCover);
    }

    @OnClick(R.id.iv_cover)
    void onMovieClick() {
        actionListener.onMovieClick(movie);
    }
}
