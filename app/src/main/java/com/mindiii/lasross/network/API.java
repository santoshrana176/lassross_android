package com.mindiii.lasross.network;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mindiii.lasross.Lasross;
import com.mindiii.lasross.module.Filter.model.VarientListResponse;
import com.mindiii.lasross.module.activeplan.model.CancelSubscriptionResponse;
import com.mindiii.lasross.module.address.model.AddAddressResponse;
import com.mindiii.lasross.module.address.model.UserAddressListResponse;
import com.mindiii.lasross.module.allreviews.model.AllReviewsModel;
import com.mindiii.lasross.module.cart.model.AddTocartResponse;
import com.mindiii.lasross.module.cart.model.CartClearAllResponse;
import com.mindiii.lasross.module.cart.model.CartItemIncreaseResponse;
import com.mindiii.lasross.module.cart.model.CartListResponse;
import com.mindiii.lasross.module.cart.model.DeleteCartItemResponse;
import com.mindiii.lasross.module.delivery.model.DeliveryResponse;
import com.mindiii.lasross.module.home.model.AddRemoveWishListResponse;
import com.mindiii.lasross.module.home.model.BannerWeeklyOfferResponse;
import com.mindiii.lasross.module.home.model.DealListResponse;
import com.mindiii.lasross.module.home.model.LogoutResponse;
import com.mindiii.lasross.module.home.model.MenuSliderResponse;
import com.mindiii.lasross.module.home.model.ProductResponse;
import com.mindiii.lasross.module.loginregistration.model.ForgotPasswordResponse;
import com.mindiii.lasross.module.loginregistration.model.LoginResponse;
import com.mindiii.lasross.module.myorder.model.MyOrdersModel;
import com.mindiii.lasross.module.notification.model.NotificationListModel;
import com.mindiii.lasross.module.notification.model.ReadNotificationModel;
import com.mindiii.lasross.module.orderdetail.model.OrderDetailModel;
import com.mindiii.lasross.module.payment.model.FinalPaymentResponse;
import com.mindiii.lasross.module.productDetail.model.ProductDetailResponse;
import com.mindiii.lasross.module.settings.model.FeedbackResponse;
import com.mindiii.lasross.module.settings.model.LanguageModel;
import com.mindiii.lasross.module.settings.model.NotificationAlertResponse;
import com.mindiii.lasross.module.settings.model.SliderBannerResponse;
import com.mindiii.lasross.module.settings.model.TermsPolicyResponse;
import com.mindiii.lasross.module.subscription.SubscriptionResponse;
import com.mindiii.lasross.module.subscription.presenter.model.SubscribeResponse;
import com.mindiii.lasross.module.wishlist.model.AllClearResponse;
import com.mindiii.lasross.module.wishlist.model.WishListResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface API {

    @GET("products/getProductList")
    Call<ProductResponse> callProductListApi(@Header("auth-token") String token,
                                             @Header("language") String language,
                                             @Query("search_term") String search_term,
                                             @Query("limit") String limit,
                                             @Query("offset") String offset,
                                             @Query("size") String size,
                                             @Query("color") String color,
                                             @Query("price_from") String price_from,
                                             @Query("price_to") String price_to,
                                             @Query("popular") String popular,
                                             @Query("rating") String rating,
                                             @Query("latest") String latest,
                                             @Query("price_low") String price_low,
                                             @Query("price_high") String pice_high,
                                             @Query("category") String category,
                                             @Query("user_id") String user_id,
                                             @Query("deal_id") String deal_i,
                                             @Query("device_type") String deviceType);


    @GET("service/logout")
    Call<LogoutResponse> callLogoutApi(@Header("language") String language, @Header("auth-token") String token);

    @FormUrlEncoded
    @POST("service/forgotPassword")
    Call<ForgotPasswordResponse> callForgotPasswordApi(@Header("language") String language, @Field("email") String email);

    @GET("category/getCatSubcat")
    Call<MenuSliderResponse> callSlideMenuApi(@Header("language") String language, @Header("auth-token") String authToken, @Query("device_type") String deviceType);


    @FormUrlEncoded
    @POST("service/login")
    Call<LoginResponse> callLoginApi(@Header("language") String language, @Field("email") String email
            , @Field("password") String password
            , @Field("device_type") String device_type
            , @Field("device_token") String device_token);


    @FormUrlEncoded
    @POST("service/signup")
    Call<LoginResponse> callSignUpApi(@Header("language") String language,
                                      @Field("full_name") String full_name,
                                      @Field("email") String email,
                                      @Field("password") String password
            , @Field("social_id") String social_id
            , @Field("social_type") String social_type
            , @Field("device_type") String device_type
            , @Field("device_token") String device_token
            , @Field("profile_image") String profile_image
            , @Field("signup_from") String signup_from);

    @GET("products/getVariantList")
    Call<VarientListResponse> callVariantFilterApi( @Header("auth-token") String token,@Header("language") String language);

    @GET("user/getUserProfile")
    Call<LoginResponse> callGetProfile(@Header("language") String language, @Header("auth-token") String token);

    @Multipart
    @POST("user/updateUserProfile")
    Call<LoginResponse> callUpdateProfileApi(@Header("language") String language, @Header("auth-token") String token
            , @Part("full_name") RequestBody full_name
            , @Part("userAddressId") RequestBody userAddressId
            , @Part("full_address") RequestBody full_address
            , @Part("longitude") RequestBody longitude
            , @Part("phone_number") RequestBody phone_number
            , @Part("latitude") RequestBody latitude
            , @Part MultipartBody.Part file);  //@Part MultipartBody.Part file


    @FormUrlEncoded
    @POST("user/addRemoveProductWishlist")
        //@Header('Content-Type',"application/x-www-form-urlencoded")
        //@Headers("Content-Type:application/x-www-form-urlencoded; charset=utf-8")
    Call<AddRemoveWishListResponse> callAddRemoveWishListApi(@Header("language") String language,
                                                             @Header("auth-token") String token,
                                                             // @Header("Content-Type") String Content_Type,
                                                             @Field("productId") String productId);

    @GET("products/getProductDetail")
    Call<ProductDetailResponse> callProductDetailApi(@Header("auth-token") String token,@Header("language") String language, @Query("productId") String productId,
                                                     @Query("user_id") String user_id, @Query("device_type") String deviceType);

    @GET("user/userWishlist")
    Call<WishListResponse> callUserWishList(@Header("language") String language, @Header("auth-token") String token
            , @Query("offset") String offset
            , @Query("limit") String limit);

    @POST("user/clearAllWishlist")
    Call<AllClearResponse> callAllClearWishList(@Header("language") String language, @Header("auth-token") String token);

    @FormUrlEncoded
    @POST("Cart/addToCart")
    Call<AddTocartResponse> callAddToCart(@Header("language") String language, @Header("auth-token") String token
            , @Field("productId") String productId
            , @Field("productQuantity") String productQuantity
            , @Field("variants") String variants);

    @GET("cart/getCartItemList")
    Call<CartListResponse> callCartList(@Header("language") String language, @Header("auth-token") String token
            , @Query("offset") String offset
            , @Query("limit") String limit);

    @FormUrlEncoded
    @POST("cart/updateCart")
    Call<CartItemIncreaseResponse> callUpdateCart(@Header("language") String language, @Header("auth-token") String token
            , @Field("cartItemId") String cartItemId
            , @Field("productId") String productId
            , @Field("productQuantity") String productQuantity);

    @FormUrlEncoded
    @POST("cart/updateCartDecrement")
    Call<CartItemIncreaseResponse> callUpdateDecrementCart(@Header("language") String language, @Header("auth-token") String token
            , @Field("cartItemId") String cartItemId
            , @Field("productId") String productId
            , @Field("productQuantity") String productQuantity);

    @FormUrlEncoded
    @POST("cart/deleteCart")
    Call<DeleteCartItemResponse> callDeleteCartItem(@Header("language") String language, @Header("auth-token") String token
            , @Field("cartItemId") String cartItemId);

    @POST("cart/clearCart")
    Call<CartClearAllResponse> callAllClearCartItem(@Header("language") String language, @Header("auth-token") String token);


    @FormUrlEncoded
    @POST("user/addAddress")
    Call<AddAddressResponse> callAddAddress(@Header("language") String language, @Header("auth-token") String token
            , @Field("company_type") String company_type
            , @Field("phone_number") String phone_number
            , @Field("full_address") String full_address
            , @Field("latitude") String latitude
            , @Field("longitude") String longitude);

    @GET("user/userAddresslist")
    Call<UserAddressListResponse> callUserAddressList(@Header("language") String language,
                                                      @Header("auth-token") String token
            , @Query("offset") String offset
            , @Query("limit") String limit);

    @FormUrlEncoded
    @POST("user/removeAddress")
    Call<AddAddressResponse> callDeleteUserAddress(@Header("language") String language, @Header("auth-token") String token
            , @Field("userAddressId") String userAddressId);

    @FormUrlEncoded
    @POST("user/updateUseAddress")
    Call<AddAddressResponse> callUpdateAddress(@Header("language") String language, @Header("auth-token") String token
            , @Field("userAddressId") String userAddressId
            , @Field("full_address") String full_address
            , @Field("latitude") String latitude
            , @Field("longitude") String longitude
            , @Field("company_type") String company_type
            , @Field("phone_number") String phone_number);

    @FormUrlEncoded
    @POST("Payment/final_amount")
    Call<DeliveryResponse> callFinalAmount(@Header("language") String language, @Header("authToken") String token
            , @Field("shipping_add_id") String shipping_add_id);

    @FormUrlEncoded
    @POST("payment/payment")
    Call<FinalPaymentResponse> finalPaymentApi(@Header("language") String language, @Header("auth-token") String token,
                                               @Field("payment_mode") String payment_mode,
                                               @Field("source_type") String source_type,
                                               @Field("source") String source,
                                               @Field("shipping_add_id") String shipping_add_id);


    @GET("order/order_list")
    Call<MyOrdersModel> callMyOrderList(@Header("language") String language, @Header("authToken") String token);

    @FormUrlEncoded
    @POST("products/products_rating")
    Call<AddAddressResponse> ratingAndReviews(@Header("language") String language, @Header("authToken") String token,
                                              @Field("product_id") String product_id,
                                              @Field("rating") String rating,
                                              @Field("order_item_id") String item_id,
                                              @Field("description") String description);


    @FormUrlEncoded
    @POST("order/order_detail")
    Call<OrderDetailModel> callOrderDetailApi(@Header("language") String language, @Header("authToken") String token
            , @Field("order_id") String orderId);


    @GET("subscription/getPlan")
    Call<SubscriptionResponse> subscriptionApi(@Header("language") String language, @Header("authToken") String token);

    @GET("order/notificationList")
    Call<NotificationListModel> callNotificationList(@Header("language") String language, @Header("authToken") String token);

    @FormUrlEncoded
    @POST("user/changePassword")
    Call<AddAddressResponse> changePasswordApi(@Header("language") String language, @Header("authToken") String token
            , @Field("oldPassword") String oldPassword,
                                               @Field("newPassword") String newPassword,
                                               @Field("cPassword") String cPassword);

    @FormUrlEncoded
    @POST("subscription/createSubscription")
    Call</*JsonElement*/SubscribeResponse> planSubscribeApi(@Header("language") String language,
                                       @Header("authToken") String token,
                                       @Field("plan_id") String plan_id);

    @GET("user/userCurrentSubscribedPlan")
    Call<SubscribeResponse> callCurrentSubscribedPlan(@Header("language") String language, @Header("authToken") String token);

    @GET("subscription/cancelSubscription")
    Call<CancelSubscriptionResponse> callCancelSubscription(@Header("language") String language, @Header("authToken") String token);

    @GET("products/get_product_review")
    Call<AllReviewsModel> callProductReviewApi(@Header("language") String language, @Query("product_id") String productId,
                                               @Header("authToken") String token);

    @GET("products/get_banner_weekly_offer")
    Call<BannerWeeklyOfferResponse> callBannerWeeklyOfferApi( @Header("auth-token") String token,@Header("language") String language);

    @FormUrlEncoded
    @POST("order/readNotification")
    Call<ReadNotificationModel> callReadNotificationApi(@Header("language") String language, @Header("authToken") String token
            , @Field("notification_id") String notification_id);

    @GET("deal/getDealList")
    Call<DealListResponse> callDealListApi(@Header("language") String language, @Header("auth-token") String authToken);

    @GET("service/getContent")
//get products/get_banner_slider
    Call<TermsPolicyResponse> callTermsPolicyApi( @Header("auth-token") String token,@Header("language") String language);

    @FormUrlEncoded
    @POST("user/writeFeedback")
    Call<FeedbackResponse> callFeedBackApi(@Header("language") String language, @Header("authToken") String token,
                                           @Field("feedback") String feedback);

    @FormUrlEncoded
    @POST("user/changeLanguage")
    Call<LanguageModel> callLanguageApi(@Header("language") String language, @Header("auth-token") String token,
                                        @Field("language_code") String languageCode);

    @FormUrlEncoded
    @POST("notification/pushAlertStatus")
    Call<NotificationAlertResponse> callNotificationOnOff(@Header("language") String language, @Header("auth-token") String token,
                                                          @Field("status") String status);

    @GET("products/get_banner_slider")
//get
    Call<SliderBannerResponse> callSlidingBannerApi(@Header("language") String language, @Header("auth-token") String token);

}