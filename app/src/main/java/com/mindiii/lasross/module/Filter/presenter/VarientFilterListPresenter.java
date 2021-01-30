package com.mindiii.lasross.module.Filter.presenter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.mindiii.lasross.R;
import com.mindiii.lasross.base.ApiCallback;
import com.mindiii.lasross.base.errorResponse.APIErrors;
import com.mindiii.lasross.base.errorResponse.ErrorUtils;
import com.mindiii.lasross.module.Filter.model.VarientListResponse;
import com.mindiii.lasross.network.API;
import com.mindiii.lasross.network.ServiceGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VarientFilterListPresenter {
    private Context mContext;
    private ApiCallback.VariantFilterCallback variantFilterCallback;

    public VarientFilterListPresenter(Context mContext, ApiCallback.VariantFilterCallback variantFilterCallback) {
        this.mContext = mContext;
        this.variantFilterCallback = variantFilterCallback;
    }

    public void callGetVarientListApi() {

        variantFilterCallback.onShowBaseLoader();
        final API api = ServiceGenerator.createService(API.class);
        Call<VarientListResponse> varientApi = api.callVariantFilterApi();
        varientApi.enqueue(new Callback<VarientListResponse>() {
            @Override
            public void onResponse(@NonNull Call<VarientListResponse> call, @NonNull Response<VarientListResponse> response) {
                variantFilterCallback.onHideBaseLoader();
                if (response.isSuccessful()) {
                    variantFilterCallback.onSuccessVariantList(response.body());
                } else {
                    APIErrors apiErrors = ErrorUtils.parseError(response);
                    variantFilterCallback.onError(apiErrors.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<VarientListResponse> call, @NonNull Throwable t) {
                variantFilterCallback.onHideBaseLoader();
                if (t instanceof IOException) {
                    variantFilterCallback.onError(mContext.getString(R.string.internet_connection));
                } else {
                    variantFilterCallback.onError(mContext.getString(R.string.oops_wrong));
                }
            }
        });
    }
}