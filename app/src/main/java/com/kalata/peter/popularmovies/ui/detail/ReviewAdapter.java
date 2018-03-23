package com.kalata.peter.popularmovies.ui.detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kalata.peter.popularmovies.R;
import com.kalata.peter.popularmovies.data.remote.models.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter {

    private List<Review> reviews;

    public ReviewAdapter(){
        setHasStableIds(true);
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_review, viewGroup, false);
        return new ReviewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        ((ReviewViewHolder) holder).bindData(reviews.get(i));
    }

    @Override
    public int getItemCount() {
        return reviews == null ? 0 : reviews.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}