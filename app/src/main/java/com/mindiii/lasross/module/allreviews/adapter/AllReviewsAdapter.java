package com.mindiii.lasross.module.allreviews.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mindiii.lasross.R;
import com.mindiii.lasross.module.allreviews.model.Review;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class AllReviewsAdapter extends RecyclerView.Adapter<AllReviewsAdapter.Holder> {

    private List<Review> list;
    private Context context;

    public AllReviewsAdapter(List<Review> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AllReviewsAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        AllReviewsAdapter.Holder holder;

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_reviews_adapter_layout, viewGroup, false);
        holder = new AllReviewsAdapter.Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllReviewsAdapter.Holder holder, int i) {

        Review itemList = list.get(i);
        holder.tvUserName.setText(itemList.getFull_name());
        if (itemList.getDescription() !=null  && itemList.getDescription().isEmpty()){
            holder.tvUserComment.setVisibility(View.GONE);
        }else {
            holder.tvUserComment.setVisibility(View.VISIBLE);
            holder.tvUserComment.setText(itemList.getDescription());
        }

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy ");
        try {
            String date = outputFormat.format(inputFormat.parse(itemList.getUpdated_on()));
            holder.tvDateProductReview.setText(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (itemList.getUser_profile_image().isEmpty()) {
            holder.ivUserImage.setImageResource(R.drawable.product_review_list_image_circle);
        } else {
            Picasso.with(context)
                    .load(itemList.getUser_profile_image())
                    .into(holder.ivUserImage);
        }


        if (itemList.getAverageRating() != null) {
            holder.ratingBarAllReviews.setRating(Float.parseFloat(itemList.getAverageRating()));
            //setRatingBarStart(holder.ratingBarAllReviews);
        }
    }

    private void setRatingBarStart(RatingBar rating_bar) {
        LayerDrawable stars = (LayerDrawable) rating_bar.getProgressDrawable();
        stars.getDrawable(2)
                .setColorFilter(context.getResources().getColor(R.color.star_fill_color),
                        PorterDuff.Mode.SRC_ATOP); // for filled stars
        stars.getDrawable(1)
                .setColorFilter(context.getResources().getColor(R.color.star_fill_color),
                        PorterDuff.Mode.SRC_ATOP); // for half filled stars
        stars.getDrawable(0)
                .setColorFilter(context.getResources().getColor(R.color.star_unfill_color),
                        PorterDuff.Mode.SRC_ATOP); // for empty stars
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        ImageView ivUserImage;
        RatingBar ratingBarAllReviews;
        TextView tvUserName, tvUserComment, tvDateProductReview;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ivUserImage = itemView.findViewById(R.id.ivUserImage);
            ratingBarAllReviews = itemView.findViewById(R.id.ratingBarAllReviews);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvUserComment = itemView.findViewById(R.id.tvUserComment);
            tvDateProductReview = itemView.findViewById(R.id.tvDateProductReview);
        }
    }
}