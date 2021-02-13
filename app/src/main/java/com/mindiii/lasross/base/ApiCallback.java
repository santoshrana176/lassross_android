package com.mindiii.lasross.base;


import android.app.Dialog;

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

import org.intellij.lang.annotations.Language;


public interface ApiCallback {

    interface ProductListCallback extends BaseInterface {
        void onSuccessProductList(ProductResponse sendOtpResponse);

        void onSuccessLogout(LogoutResponse logoutResponse);

        void onSuccessAddRemoveWishList(AddRemoveWishListResponse addRemoveWishListResponse);

        void OnSuccessMenuSlider(MenuSliderResponse menuSliderResponse);
        void onSuccesBannerSliderImage(SliderBannerResponse menuSliderResponse);

        void OnSuccessDealList(DealListResponse dealListResponse);

        void onTokenChangeError(String errorMessage);

        void onSuccessWeeklyOffer(BannerWeeklyOfferResponse bannerWeeklyOfferResponse);
    }

    interface CoatsListCallback extends BaseInterface {
        void onSuccessProductList(ProductResponse sendOtpResponse);

        void onSuccessAddRemoveWishList(AddRemoveWishListResponse addRemoveWishListResponse);

        void onTokenChangeError(String errorMessage);
    }

    interface UserWishListCallback extends BaseInterface {
        void onSuccessUserWishList(WishListResponse wishListResponse);

        void onSuccessAddRemoveWishList(AddRemoveWishListResponse forgotPasswordResponse);

        void onSuccessAllClearWishList(AllClearResponse allClearResponse);

        void onTokenChangeError(String errorMessage);
    }

    interface LoginCallback extends BaseInterface {
        void onSuccessLogin(LoginResponse loginResponse);

        void onSuccessSocial(LoginResponse loginResponse);

        void onForgotPassword(ForgotPasswordResponse forgotPasswordResponse, Dialog dialog);
    }

    interface SignUpCallback extends BaseInterface {
        void onSuccessSocial(LoginResponse loginResponse);

        void onSuccessSignUp(LoginResponse loginResponse);
    }

    interface VariantFilterCallback extends BaseInterface {
        void onSuccessVariantList(VarientListResponse varientListResponse);
    }

    interface UpdateProfileCallback extends BaseInterface {
        void onSuccessUpdateProfile(LoginResponse loginResponse);

        void onTokenChangeError(String errorMessage);
    }

    interface GetProfileCallback extends BaseInterface {
        void onSuccessGetProfile(LoginResponse loginResponse);

        void onTokenChangeError(String errorMessage);

        void onSuccessGetCart(CartListResponse cartListResponse);

        void onSuccessNotificationList(NotificationListModel notificationListModel);
    }

    interface GetAddressCallback extends BaseInterface {
        void onSuccessUserAddressList(UserAddressListResponse userAddressListResponse);

        void onTokenChangeError(String errorMessage);
    }

    interface ProductDetailCallback extends BaseInterface {
        void onSuccessDetail(ProductDetailResponse productDetailResponse);

        void onSuccessAddRemoveWishList(AddRemoveWishListResponse addRemoveWishListResponse);

        void onTokenChangeError(String errorMessage);
    }

    interface AddToCartCallback extends BaseInterface {
        void onSuccessAddToCart(AddTocartResponse addTocartResponse);

        void onTokenChangeError(String errorMessage);
    }

    interface GetCartCallback extends BaseInterface {
        void onSuccessGetCart(CartListResponse cartListResponse);

        void onSuccessUpdateCartItem(CartItemIncreaseResponse cartItemIncreaseResponse);

        void onSuccessUpdateCartDecreaseItem(CartItemIncreaseResponse cartItemIncreaseResponse);

        void onTokenChangeError(String errorMessage);

        void onDeleteCartItem(DeleteCartItemResponse deleteCartItemResponse);

        void onSuccessClearAllItem(CartClearAllResponse cartClearAllResponse);
    }


    interface AddAddressCallback extends BaseInterface {
        void onSuccessAddAddress(AddAddressResponse addAddressResponse);

        void onUpdateAddress(AddAddressResponse addAddressResponse);

        void onTokenChangeError(String errorMessage);
    }

    interface MyAddressCallback extends BaseInterface {
        void onSuccessUserAddressList(UserAddressListResponse userAddressListResponse);

        void onTokenChangeError(String errorMessage);

        void onDeleteUserAddress(AddAddressResponse addAddressResponse);
    }

    interface DeliveryCallback extends BaseInterface {
        void onSuccessDeliveryDetails(DeliveryResponse deliveryResponse);

        void onTokenChangeError();
    }

    interface PaymentCallBack extends BaseInterface {
        void onSuccessPayment(FinalPaymentResponse finalPaymentResponse);

        void onTokenChangeError(String errorMessage);
    }

    interface MyOrderCallback extends BaseInterface {
        void onSuccessMyOrderList(MyOrdersModel myOrdersModel);

        void onTokenChangeError(String errorMessage);
    }

    interface OrderDetailCallback extends BaseInterface {
        void onSuccessProductRating(AddAddressResponse addAddressResponse);

        void onSuccessOrderDetail(OrderDetailModel orderDetailModel);

        void onTokenChangeError(String errorMessage);
    }

    interface SubscriptionCallback extends BaseInterface {
        void onSuccessSubscription(SubscriptionResponse subscriptionResponse);

        void onSuccessSubscribe(SubscribeResponse response);

            void onTokenChangeError(String errorMessage);
    }

    interface NotificationCallback extends BaseInterface {
        void onSuccessNotificationList(NotificationListModel notificationListModel);

        void onSuccessReadNotification(ReadNotificationModel readNotificationModel);

        void onTokenChangeError(String errorMessage);
    }

    interface SettingsCallback extends BaseInterface {
        void onSuccessChangePassword(AddAddressResponse response);

        void onTokenChangeError(String errorMessage);

        void onSuccessLogout(LogoutResponse logoutResponse);
        void onSuccesNotifcationOnOff(NotificationAlertResponse logoutResponse);

        void onSuccessCurrentSubscribedPlan(SubscribeResponse response);

        void onSuccessTermsPolicy(TermsPolicyResponse termsPolicyResponse);
    }

    interface LeaveFeedbackCallback extends BaseInterface {
        void onSuccessSendFeedback(FeedbackResponse feedbackResponse);

        void onTokenChangeError(String errorMessage);
    }

    interface ActivePlanCallback extends BaseInterface {
        void onCancelSubscription(CancelSubscriptionResponse cancelSubscriptionResponse);

        void onTokenChangeError(String errorMessage);
    }

    interface AllReviewsCallback extends BaseInterface {
        void onSuccessReviews(AllReviewsModel allReviewsModel);

        void onTokenChangeError(String errorMessage);
    }

    interface TermsPolicyCallback extends BaseInterface {
        void onSuccessTermsPolicyResponse(TermsPolicyResponse termsPolicyResponse);
    }

    interface LanguageCallback extends BaseInterface {
        void onSuccessLanguageResponse(LanguageModel languageModel);

        void onTokenChangeError(String errorMessage);
    }

}