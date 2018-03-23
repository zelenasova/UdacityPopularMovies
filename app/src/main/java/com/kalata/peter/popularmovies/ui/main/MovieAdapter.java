package com.kalata.peter.popularmovies.ui.main;

import android.content.res.Configuration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kalata.peter.popularmovies.R;
import com.kalata.peter.popularmovies.data.remote.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter {

    private List<Movie> movies;
    private final ActionListener actionListener;

    public MovieAdapter(ActionListener actionListener){
        this.actionListener = actionListener;
        setHasStableIds(true);
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) itemView.getLayoutParams();
        if(itemView.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            lp.height = viewGroup.getMeasuredHeight() / 2;
        } else {
            lp.height = (int) (viewGroup.getMeasuredWidth() / 2 * 1.5);
        }
        itemView.setLayoutParams(lp);
        return new MovieViewHolder(itemView, actionListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        ((MovieViewHolder) holder).bindData(movies.get(i));
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    interface ActionListener {

        void onMovieClick(Movie movie);

    }



}