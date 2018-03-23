package com.kalata.peter.popularmovies.ui.detail;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kalata.peter.popularmovies.App;
import com.kalata.peter.popularmovies.R;
import com.kalata.peter.popularmovies.data.remote.models.Trailer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrailerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_trailer)
    ImageView ivTrailer;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.root)
    ConstraintLayout root;

    private final TrailerAdapter.ActionListener actionListener;
    private Trailer trailer;

    TrailerViewHolder(View itemView, final TrailerAdapter.ActionListener actionListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.actionListener = actionListener;
    }

    public void bindData(Trailer trailer) {
        this.trailer = trailer;
        tvTitle.setText(trailer.getName());
        setupImage();
    }

    private void setupImage() {
        String url = String.format(App.getAppContext().getString(R.string.trailer_image), trailer.getKey());
        Glide.with(itemView.getContext())
                .load(url)
                .error(R.drawable.error)
                .placeholder(R.color.colorPrimary)
                .into(ivTrailer);
    }

    @OnClick(R.id.root)
    void onTrailerClick() {
        actionListener.onTrailerClick(trailer);
    }
}
