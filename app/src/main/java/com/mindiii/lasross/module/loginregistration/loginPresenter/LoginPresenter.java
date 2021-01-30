package com.mindiii.lasross.module.loginregistration.loginPresenter;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.mindiii.lasross.R;
import com.mindiii.lasross.base.ApiCallback;
import com.mindiii.lasross.base.errorResponse.APIErrors;
import com.mindiii.lasross.base.errorResponse.ErrorUtils;
import com.mindiii.lasross.module.loginregistration.model.ForgotPasswordResponse;
import com.mindiii.lasross.module.loginregistration.model.LoginResponse;
import com.mindiii.lasross.network.API;
import com.mindiii.lasross.network.ServiceGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginPresenter {
    Context mContext;
    private ApiCallback.LoginCallback loginCallback;

    public LoginPresenter(ApiCallback.LoginCallback loginCallback, Context mContext) {
        this.loginCallback = loginCallback;
        this.mContext = mContext;
    }

    //simple login api calling
    public void callLoginApi(String emailId, String password, String dToken) {
        loginCallback.onShowBaseLoader();
        API api = ServiceGenerator.createService(API.class);
        Call<LoginResponse> loginResponseCall = api.callLoginApi(emailId, password, "2", dToken);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                loginCallback.onHideBaseLoader();
                try {
                    if (response.isSuccessful()) {
                        loginCallback.onSuccessLogin(response.body());
                    } else {
                        APIErrors apiErrors = ErrorUtils.parseError(response);
                        loginCallback.onError(apiErrors.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                loginCallback.onHideBaseLoader();
                if (t instanceof IOException) {
                    //loginCallback.onError(mContext.getString(R.string.internet_connection));
                } else {
                    loginCallback.onError(mContext.getString(R.string.oops_wrong));
                }
            }
        });
    }

    public void callForgotPasswordApi(String email, final Dialog dialog) {
        loginCallback.onShowBaseLoader();
        final API api = ServiceGenerator.createService(API.class);
        Call<ForgotPasswordResponse> genderApi = api.callForgotPasswordApi(email);
        genderApi.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(@NonNull Call<ForgotPasswordResponse> call, @NonNull Response<ForgotPasswordResponse> response) {
                loginCallback.onHideBaseLoader();
                if (response.isSuccessful()) {
                    loginCallback.onForgotPassword(response.body(), dialog);
                } else {
                    APIErrors apiErrors = ErrorUtils.parseError(response);
                    loginCallback.onError(apiErrors.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ForgotPasswordResponse> call, @NonNull Throwable t) {
                loginCallback.onHideBaseLoader();
                if (t instanceof IOException) {
                    loginCallback.onError(mContext.getString(R.string.internet_connection));
                } else {
                    loginCallback.onError(mContext.getString(R.string.oops_wrong));
                }
            }
        });
    }

    public void callSocialCheck(String name, String email, String id, String url, String type, String dToken) {
        loginCallback.onShowBaseLoader();
        API api = ServiceGenerator.createService(API.class);
        Call<LoginResponse> loginResponseCall = api.callSignUpApi(name, email, "", id, type, "2", dToken, url, "2");
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                loginCallback.onHideBaseLoader();
                try {
                    if (response.isSuccessful()) {
                        loginCallback.onSuccessSocial(response.body());
                    } else {
                        APIErrors apiErrors = ErrorUtils.parseError(response);
                        loginCallback.onError(apiErrors.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                loginCallback.onHideBaseLoader();
                if (t instanceof IOException) {
                    loginCallback.onError(mContext.getString(R.string.internet_connection));
                } else {
                    loginCallback.onError(mContext.getString(R.string.oops_wrong));
                }
            }
        });
    }
}