package com.mindiii.lasross.module.home.presenter;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.mindiii.lasross.Lasross;
import com.mindiii.lasross.R;
import com.mindiii.lasross.app.session.Session;
import com.mindiii.lasross.base.ApiCallback;
import com.mindiii.lasross.base.errorResponse.APIErrors;
import com.mindiii.lasross.base.errorResponse.ErrorUtils;
import com.mindiii.lasross.module.home.model.AddRemoveWishListResponse;
import com.mindiii.lasross.module.home.model.BannerWeeklyOfferResponse;
import com.mindiii.lasross.module.home.model.DealListResponse;
import com.mindiii.lasross.module.home.model.LogoutResponse;
import com.mindiii.lasross.module.home.model.MenuSliderResponse;
import com.mindiii.lasross.module.home.model.ProductResponse;
import com.mindiii.lasross.module.settings.model.SliderBannerResponse;
import com.mindiii.lasross.network.API;
import com.mindiii.lasross.network.ServiceGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomePresenter {

    private ApiCallback.ProductListCallback productListCallback;
    private Context mContext;
    private Session session;

    public HomePresenter(ApiCallback.ProductListCallback productListCallback, Context mContext) {
        this.productListCallback = productListCallback;
        this.mContext = mContext;
        session = new Session(mContext);
    }

    // get produt list api
    public void callGetProductListApi(String productName, String limit, String offset, String size, String color, String price_from,
                                      String price_to, String popular, String rating, String latest, String price_low,
                                      String pice_high, String category, String deal_id) {
        productListCallback.onShowBaseLoader();
        String token = session.getAuthToken();
        final API api = ServiceGenerator.createService(API.class);
        Call<ProductResponse> genderApi = api.callProductListApi(token, Lasross.appLanguage, productName,
                limit, offset, size, color, price_from, price_to, popular, rating, latest, price_low, pice_high, category
                , session.getRegistration().getUserId(), deal_id, "2");
        genderApi.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProductResponse> call, @NonNull Response<ProductResponse> response) {
                //productListCallback.onHideBaseLoader();
                if (response.isSuccessful()) {
                    productListCallback.onSuccessProductList(response.body());
                    productListCallback.onHideBaseLoader();
                } else {
                    try {
                        productListCallback.onHideBaseLoader();
                        APIErrors apiErrors = ErrorUtils.parseError(response);
                        if (apiErrors.getMessage().equals("Invalid token")) {
                            productListCallback.onTokenChangeError(apiErrors.getMessage());
                        } else {
                            productListCallback.onError(apiErrors.getMessage());
                        }
                        //productListCallback.onError(apiErrors.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                        productListCallback.onHideBaseLoader();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductResponse> call, @NonNull Throwable t) {
                productListCallback.onHideBaseLoader();
                if (t instanceof IOException) {
                    //productListCallback.onError(mContext.getString(R.string.internet_connection));
                } else {
                    productListCallback.onError(mContext.getString(R.string.oops_wrong));
                }
            }
        });
    }

    // get deal list api
    public void callDealListApi() {
        productListCallback.onShowBaseLoader();
        final API api = ServiceGenerator.createService(API.class);
        Call<DealListResponse> genderApi = api.callDealListApi(Lasross.appLanguage, session.getAuthToken());
        genderApi.enqueue(new Callback<DealListResponse>() {
            @Override
            public void onResponse(@NonNull Call<DealListResponse> call, @NonNull Response<DealListResponse> response) {
                productListCallback.onHideBaseLoader();
                if (response.isSuccessful()) {
                    productListCallback.OnSuccessDealList(response.body());
                } else {
                    APIErrors apiErrors = ErrorUtils.parseError(response);
                    if (apiErrors.getMessage().equals("Invalid token")) {
                        productListCallback.onTokenChangeError(apiErrors.getMessage());
                    } else {
                        productListCallback.onError(apiErrors.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DealListResponse> call, @NonNull Throwable t) {
                productListCallback.onHideBaseLoader();
                if (t instanceof IOException) {
                    //productListCallback.onError(mContext.getString(R.string.internet_connection));
                } else {
                    productListCallback.onError(mContext.getString(R.string.oops_wrong));
                }
            }
        });
    }

    // slide menu api
    public void callSlideMenuApi() {
        productListCallback.onShowBaseLoader();
        final API api = ServiceGenerator.createService(API.class);
        Call<MenuSliderResponse> genderApi = api.callSlideMenuApi(Lasross.appLanguage, session.getAuthToken(), "2");
        genderApi.enqueue(new Callback<MenuSliderResponse>() {
            @Override
            public void onResponse(@NonNull Call<MenuSliderResponse> call, @NonNull Response<MenuSliderResponse> response) {
                productListCallback.onHideBaseLoader();
                if (response.isSuccessful()) {
                    productListCallback.OnSuccessMenuSlider(response.body());
                } else {
                    APIErrors apiErrors = ErrorUtils.parseError(response);
                    if (apiErrors.getMessage().equals("Invalid token")) {
                        productListCallback.onTokenChangeError(apiErrors.getMessage());
                    } else {
                        productListCallback.onError(apiErrors.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MenuSliderResponse> call, @NonNull Throwable t) {
                productListCallback.onHideBaseLoader();
                if (t instanceof IOException) {
                    //productListCallback.onError(mContext.getString(R.string.internet_connection));
                } else {
                    productListCallback.onError(mContext.getString(R.string.oops_wrong));
                }
            }
        });
    }

    // logout api calling
    public void callLogoutApi() {
        productListCallback.onShowBaseLoader();
        final API api = ServiceGenerator.createService(API.class);
        Call<LogoutResponse> genderApi = api.callLogoutApi(Lasross.appLanguage, session.getAuthToken());
        genderApi.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(@NonNull Call<LogoutResponse> call, @NonNull Response<LogoutResponse> response) {
                productListCallback.onHideBaseLoader();
                if (response.isSuccessful()) {
                    productListCallback.onSuccessLogout(response.body());
                } else {
                    APIErrors apiErrors = ErrorUtils.parseError(response);
                    if (apiErrors.getMessage().equals("Invalid token")) {
                        productListCallback.onTokenChangeError(apiErrors.getMessage());
                    } else {
                        productListCallback.onError(apiErrors.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LogoutResponse> call, @NonNull Throwable t) {
                productListCallback.onHideBaseLoader();
                if (t instanceof IOException) {
                    //productListCallback.onError(mContext.getString(R.string.internet_connection));
                } else {
                    productListCallback.onError(mContext.getString(R.string.oops_wrong));
                }
            }
        });
    }

    // add and remove wishlist api
    public void callAddRemoveWishListApi(String productId) {//ad285ece5b6574fc15a0e9e2b176e024e84f44e3
        String token = session.getAuthToken();//50a72d7892a1b0800c206730bca0366d14b3c73f
        productListCallback.onShowBaseLoader();
        final API api = ServiceGenerator.createService(API.class);
        Call<AddRemoveWishListResponse> genderApi = api.callAddRemoveWishListApi(Lasross.appLanguage, token, productId);
        genderApi.enqueue(new Callback<AddRemoveWishListResponse>() {
            @Override
            public void onResponse(@NonNull Call<AddRemoveWishListResponse> call, @NonNull Response<AddRemoveWishListResponse> response) {
                productListCallback.onHideBaseLoader();
                if (response.isSuccessful()) {
                    productListCallback.onSuccessAddRemoveWishList(response.body());
                } else {
                    Log.e("error:::", "" + response.errorBody().toString());
                    APIErrors apiErrors = ErrorUtils.parseError(response);
                    if (apiErrors.getMessage().equals("Invalid token")) {
                        productListCallback.onTokenChangeError(apiErrors.getMessage());
                    } else {
                        productListCallback.onError(apiErrors.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AddRemoveWishListResponse> call, @NonNull Throwable t) {
                productListCallback.onHideBaseLoader();
                if (t instanceof IOException) {
                    //productListCallback.onError(mContext.getString(R.string.internet_connection));
                } else {
                    productListCallback.onError(mContext.getString(R.string.oops_wrong));
                }
            }
        });
    }


    public void callBannerWeeklyOfferApi() {
        productListCallback.onShowBaseLoader();
        final API api = ServiceGenerator.createService(API.class);
        Call<BannerWeeklyOfferResponse> genderApi = api.callBannerWeeklyOfferApi(session.getAuthToken(),Lasross.appLanguage);
        genderApi.enqueue(new Callback<BannerWeeklyOfferResponse>() {
            @Override
            public void onResponse(@NonNull Call<BannerWeeklyOfferResponse> call, @NonNull Response<BannerWeeklyOfferResponse> response) {
                productListCallback.onHideBaseLoader();
                if (response.isSuccessful()) {
                    productListCallback.onSuccessWeeklyOffer(response.body());
                } else {
                    APIErrors apiErrors = ErrorUtils.parseError(response);
                    if (apiErrors.getMessage().equals("Invalid token")) {
                        productListCallback.onTokenChangeError(apiErrors.getMessage());
                    } else {
                        productListCallback.onError(apiErrors.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BannerWeeklyOfferResponse> call, @NonNull Throwable t) {
                productListCallback.onHideBaseLoader();
                if (t instanceof IOException) {
                    //productListCallback.onError(mContext.getString(R.string.internet_connection));
                } else {
                    productListCallback.onError(mContext.getString(R.string.oops_wrong));
                }
            }
        });
    }

    //home screen bannerSlider api
    // slide menu api
    public void callBannerSliderApi() {
        productListCallback.onShowBaseLoader();
        final API api = ServiceGenerator.createService(API.class);
        String token = session.getAuthToken();
        Call<SliderBannerResponse> genderApi = api.callSlidingBannerApi(Lasross.appLanguage, token);
        genderApi.enqueue(new Callback<SliderBannerResponse>() {
            @Override
            public void onResponse(@NonNull Call<SliderBannerResponse> call, @NonNull Response<SliderBannerResponse> response) {
                productListCallback.onHideBaseLoader();
                if (response.isSuccessful()) {
                    productListCallback.onSuccesBannerSliderImage(response.body());
                } else {
                    APIErrors apiErrors = ErrorUtils.parseError(response);
                    if (apiErrors.getMessage().equals("Invalid token")) {
                        productListCallback.onTokenChangeError(apiErrors.getMessage());
                    } else {
                        productListCallback.onError(apiErrors.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SliderBannerResponse> call, @NonNull Throwable t) {
                productListCallback.onHideBaseLoader();
                if (t instanceof IOException) {
                    //productListCallback.onError(mContext.getString(R.string.internet_connection));
                } else {
                    productListCallback.onError(mContext.getString(R.string.oops_wrong));
                }
            }
        });
    }


}
