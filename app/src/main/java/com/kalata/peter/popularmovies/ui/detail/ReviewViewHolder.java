package com.kalata.peter.popularmovies.ui.detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kalata.peter.popularmovies.R;
import com.kalata.peter.popularmovies.data.remote.models.Review;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.tv_content)
    TextView tvContent;

    ReviewViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindData(Review review) {
        tvAuthor.setText(review.getAuthor());
        tvContent.setText(review.getContent());
    }
}
