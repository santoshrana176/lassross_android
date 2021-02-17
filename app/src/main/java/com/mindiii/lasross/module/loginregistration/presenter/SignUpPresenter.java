package com.mindiii.lasross.module.loginregistration.presenter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.mindiii.lasross.Lasross;
import com.mindiii.lasross.R;
import com.mindiii.lasross.base.ApiCallback;
import com.mindiii.lasross.base.errorResponse.APIErrors;
import com.mindiii.lasross.base.errorResponse.ErrorUtils;
import com.mindiii.lasross.module.loginregistration.model.LoginResponse;
import com.mindiii.lasross.network.API;
import com.mindiii.lasross.network.ServiceGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpPresenter {
    private ApiCallback.SignUpCallback signUpCallback;
    private Context mContext;


    public SignUpPresenter(ApiCallback.SignUpCallback signUpCallback, Context mContext) {
        this.signUpCallback = signUpCallback;
        this.mContext = mContext;
    }


    public void callSignUpApi(String name, String email, String pass, String id, String type, String dToken) {
        signUpCallback.onShowBaseLoader();
        API api = ServiceGenerator.createService(API.class);
        Call<LoginResponse> loginResponseCall = api.callSignUpApi(Lasross.appLanguage,name, email, pass, id, "", "2", dToken, "", "2");
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                signUpCallback.onHideBaseLoader();
                try {
                    if (response.isSuccessful()) {
                        signUpCallback.onSuccessSignUp(response.body());
                    } else {
                        APIErrors apiErrors = ErrorUtils.parseError(response);
                        signUpCallback.onError(apiErrors.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                signUpCallback.onHideBaseLoader();
                if (t instanceof IOException) {
                    //signUpCallback.onError(mContext.getString(R.string.internet_connection));
                } else {
                    signUpCallback.onError(mContext.getString(R.string.oops_wrong));
                }
            }
        });
    }


    public void callSocialCheck(String name, String email, String id, String url, String type, String dToken) {
        signUpCallback.onShowBaseLoader();
        API api = ServiceGenerator.createService(API.class);
        Call<LoginResponse> loginResponseCall = api.callSignUpApi(Lasross.appLanguage,name, email, "", id, type, "2", dToken, url, "2");
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                signUpCallback.onHideBaseLoader();
                try {
                    if (response.isSuccessful()) {
                        signUpCallback.onSuccessSocial(response.body());
                    } else {
                        APIErrors apiErrors = ErrorUtils.parseError(response);
                        signUpCallback.onError(apiErrors.getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                signUpCallback.onHideBaseLoader();
                if (t instanceof IOException) {
                    signUpCallback.onError(mContext.getString(R.string.internet_connection));
                } else {
                    signUpCallback.onError(mContext.getString(R.string.oops_wrong));
                }
            }
        });
    }
}