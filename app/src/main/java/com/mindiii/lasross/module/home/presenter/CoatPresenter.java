package com.mindiii.lasross.module.home.presenter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.mindiii.lasross.Lasross;
import com.mindiii.lasross.R;
import com.mindiii.lasross.app.session.Session;
import com.mindiii.lasross.base.ApiCallback;
import com.mindiii.lasross.base.errorResponse.APIErrors;
import com.mindiii.lasross.base.errorResponse.ErrorUtils;
import com.mindiii.lasross.module.home.model.AddRemoveWishListResponse;
import com.mindiii.lasross.module.home.model.ProductResponse;
import com.mindiii.lasross.network.API;
import com.mindiii.lasross.network.ServiceGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoatPresenter {
    private ApiCallback.CoatsListCallback coatsListCallback;
    private Context mContext;
    private Session session;

    public CoatPresenter(ApiCallback.CoatsListCallback coatsListCallback, Context mContext) {
        this.coatsListCallback = coatsListCallback;
        this.mContext = mContext;
        session = new Session(mContext);
    }

    public void callGetProductListApi(String productName, String limit, String offset, String size,
                                      String color, String price_from, String price_to, String popular, String rating,
                                      String latest, String price_low, String pice_high, String category, String deal_id) {

        coatsListCallback.onShowBaseLoader();
        final API api = ServiceGenerator.createService(API.class);
        Call<ProductResponse> genderApi = api.callProductListApi(Lasross.appLanguage,productName,
                limit, offset, size, color, price_from, price_to, popular, rating, latest, price_low,
                pice_high, category, session.getRegistration().getUserId(), deal_id);
        genderApi.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProductResponse> call, @NonNull Response<ProductResponse> response) {
                coatsListCallback.onHideBaseLoader();
                if (response.isSuccessful()) {
                    coatsListCallback.onSuccessProductList(response.body());
                } else {
                    APIErrors apiErrors = ErrorUtils.parseError(response);
                    coatsListCallback.onError(apiErrors.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductResponse> call, @NonNull Throwable t) {
                coatsListCallback.onHideBaseLoader();
                if (t instanceof IOException) {
                    coatsListCallback.onError(mContext.getString(R.string.internet_connection));
                } else {
                    coatsListCallback.onError(mContext.getString(R.string.oops_wrong));
                }
            }
        });
    }

    public void callAddRemoveWishListApi(String productId) {
        coatsListCallback.onShowBaseLoader();
        final API api = ServiceGenerator.createService(API.class);
        Call<AddRemoveWishListResponse> genderApi = api.callAddRemoveWishListApi(Lasross.appLanguage,session.getRegistration().getAuth_token(), productId);
        genderApi.enqueue(new Callback<AddRemoveWishListResponse>() {
            @Override
            public void onResponse(@NonNull Call<AddRemoveWishListResponse> call, @NonNull Response<AddRemoveWishListResponse> response) {
                coatsListCallback.onHideBaseLoader();
                if (response.isSuccessful()) {
                    coatsListCallback.onSuccessAddRemoveWishList(response.body());
                } else {
                    APIErrors apiErrors = ErrorUtils.parseError(response);
                    if (apiErrors.getMessage().equals("Invalid token")) {
                        coatsListCallback.onTokenChangeError(apiErrors.getMessage());
                    } else {
                        coatsListCallback.onError(apiErrors.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AddRemoveWishListResponse> call, @NonNull Throwable t) {
                coatsListCallback.onHideBaseLoader();
                if (t instanceof IOException) {
                    coatsListCallback.onError(mContext.getString(R.string.internet_connection));
                } else {
                    coatsListCallback.onError(mContext.getString(R.string.oops_wrong));
                }
            }
        });
    }
}
