package com.kalata.peter.popularmovies.ui.detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kalata.peter.popularmovies.R;
import com.kalata.peter.popularmovies.data.remote.models.Trailer;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter {

    private List<Trailer> trailers;
    private final ActionListener actionListener;

    public TrailerAdapter(ActionListener actionListener){
        this.actionListener = actionListener;
        setHasStableIds(true);
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_trailer, viewGroup, false);
        return new TrailerViewHolder(itemView, actionListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        ((TrailerViewHolder) holder).bindData(trailers.get(i));
    }

    @Override
    public int getItemCount() {
        return trailers == null ? 0 : trailers.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    interface ActionListener {

        void onTrailerClick(Trailer trailer);

    }



}