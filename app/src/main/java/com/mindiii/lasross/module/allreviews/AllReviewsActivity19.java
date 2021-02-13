package com.mindiii.lasross.module.allreviews;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mindiii.lasross.R;
import com.mindiii.lasross.base.ApiCallback;
import com.mindiii.lasross.base.LasrossParentActivity;
import com.mindiii.lasross.module.allreviews.adapter.AllReviewsAdapter;
import com.mindiii.lasross.module.allreviews.model.AllReviewsModel;
import com.mindiii.lasross.module.allreviews.model.Review;
import com.mindiii.lasross.module.allreviews.presenter.AllReviewsPresenter;

import java.util.ArrayList;
import java.util.List;

public class AllReviewsActivity19 extends LasrossParentActivity implements ApiCallback.AllReviewsCallback {

    RecyclerView rvReviewsList;
    ImageView ivBackAllReviews;
    AllReviewsAdapter allReviewsAdapter;
    List<Review> reviewList;
    String productId = "";
    LinearLayout llRatingLayout;
    TextView tvNoReviewsFound, tvProductReview, tvRatingAndReview, tvFiveStartUser, tvFourStartUser, tvThreeStartUser, tvTwoStartUser, tvOneStartUser;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_reviews_activity_layout_19);

        reviewList = new ArrayList<>();

        productId = getIntent().getStringExtra("productId");

        rvReviewsList = findViewById(R.id.rvReviewsList);
        tvProductReview = findViewById(R.id.tvProductReview);
        tvNoReviewsFound = findViewById(R.id.tvNoReviewsFound);
        tvRatingAndReview = findViewById(R.id.tvRatingAndReview);
        tvFiveStartUser = findViewById(R.id.tvFiveStartUser);
        tvFourStartUser = findViewById(R.id.tvFourStartUser);
        tvThreeStartUser = findViewById(R.id.tvThreeStartUser);
        tvTwoStartUser = findViewById(R.id.tvTwoStartUser);
        tvOneStartUser = findViewById(R.id.tvOneStartUser);
        ivBackAllReviews = findViewById(R.id.ivBackAllReviews);
        llRatingLayout = findViewById(R.id.llRatingLayout);

        ivBackAllReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rvReviewsList.setHasFixedSize(true);

        productReviewApiCall();
        setproductReviewListAdapter();

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.home_header_bg1));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    void productReviewApiCall() {
        new AllReviewsPresenter(this, this).callAllReviewListApi(productId);
    }

    void setproductReviewListAdapter() {
        allReviewsAdapter = new AllReviewsAdapter(reviewList, this);
        rvReviewsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvReviewsList.setAdapter(allReviewsAdapter);
    }

    @Override
    public void onSuccessReviews(AllReviewsModel allReviewsModel) {
        if (allReviewsModel.getStatus().equalsIgnoreCase("fail")) {
            toastMessage(getString(R.string.no_list));
        } else {
            reviewList.addAll(allReviewsModel.getData().getReview());

            if (reviewList.size() == 0) {
                tvNoReviewsFound.setVisibility(View.VISIBLE);
                rvReviewsList.setVisibility(View.GONE);
                llRatingLayout.setVisibility(View.GONE);
            } else {
                tvNoReviewsFound.setVisibility(View.GONE);
                rvReviewsList.setVisibility(View.VISIBLE);
                llRatingLayout.setVisibility(View.VISIBLE);
                String strRating = allReviewsModel.getData().getRating().getAverage_rating();
                float intRating = Float.valueOf(strRating);

                if ((intRating % 1) > 0)
                    tvProductReview.setText(getOneValueAfterDecimal(allReviewsModel.getData().getRating().getAverage_rating()));
                else
                    tvProductReview.setText(getOneValueAfterDecimal(allReviewsModel.getData().getRating().getAverage_rating()) + ".0");

                tvRatingAndReview.setText(allReviewsModel.getData().getRating().getAll_ratings() + getString(R.string.rating_and) +" "+ allReviewsModel.getData().getRating().getReview() +" "+ getString(R.string.review_and));
                tvFiveStartUser.setText(allReviewsModel.getData().getRating().getFive_star());
                tvFourStartUser.setText(allReviewsModel.getData().getRating().getFour_star());
                tvThreeStartUser.setText(allReviewsModel.getData().getRating().getThree_star());
                tvTwoStartUser.setText(allReviewsModel.getData().getRating().getTwo_star());
                tvOneStartUser.setText(allReviewsModel.getData().getRating().getOne_star());
                allReviewsAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onTokenChangeError(String errorMessage) {
        showDialog(this);
    }

    @Override
    public void onShowBaseLoader() {
        showLoader();
    }

    @Override
    public void onHideBaseLoader() {
        hideLoader();
    }

    @Override
    public void onError(String errorMessage) {
    }
}
