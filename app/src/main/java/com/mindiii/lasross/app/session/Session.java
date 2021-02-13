package com.mindiii.lasross.app.session;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.google.gson.Gson;
import com.mindiii.lasross.module.loginregistration.LoginActivity;
import com.mindiii.lasross.module.loginregistration.model.LoginResponse;

import java.util.Locale;


public class Session {
    private static final String PREF_NAME = "BANG";
    private static final String PREF_NAME2 = "AppSession";
    private static final String ScreenName = "screenName";
    private static final String isShow = "isShowPopup";
    private static final String RegisterInfo = "preRegistrationInfo";
    private static final String AuthToken = "authToken";
    private static final String DeviceToken = "deviceToken";
    private static final String IsUserLoggedIn = "userLoggedIn";
    private static final String UserGetRegistered = "userGetRegistered";
    private static final String FilterSizeIds = "setFilterSizeIds";
    private static final String Address = "address";
    private static final String FilterColorIds = "setFilterColorIds";
    private static final String FilterMinPrice = "filterMinPrice";
    private static final String FilterMaxPrice = "filterMaxPrice";
    private static final String FilterPlace = "filterPlace";
    private static final String FilterInterestString = "filterInterestString";
    private static final String FilterFromAge = "filterFromAge";
    private static final String FilterToAge = "filterToAge";
    private static final String FilterMinDistance = "filterMinDistance";
    private static final String FilterMaxDistance = "filterMaxDistance";
    private static final String FilterGender = "filterGender";
    private static final String FilterBodyType = "filterBodyType";
    private static final String FilterEthnicity = "filterEthnicity";
    private static final String FilterSortBy = "sortByType";
    private static final String FilterRandomIndex = "randomIndex";
    private static final String MakeOfferStatus = "makeOfferStatus";
    private static final String isShownSubscription = "no";
    private static final String cartItemCount = "";
    private static final String productDetailMinValue = "minValue";
    private static final String productDetailMaxValue = "maxValue";
    private static final String isEnglishLanguage = "isEnglishLanguage";
    private static final String appLanguage = "AppLanguage";
    private static final String notificationStatus = "notificationStatus";
    private Context context;
    private SharedPreferences mypref, mypref2;
    private SharedPreferences.Editor editor, editor2;

    public Session(Context context) {
        this.context = context;
        mypref = this.context.getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);
        mypref2 = this.context.getSharedPreferences(PREF_NAME2, Context.MODE_MULTI_PROCESS);
        editor = mypref.edit();
        editor2 = mypref2.edit();
        editor.apply();
        editor2.apply();
    }

    public void createRegistration(LoginResponse.DataBean.UserDetailBean loginData) {
        Gson gson = new Gson();
        String json = gson.toJson(loginData);
        editor.putString(RegisterInfo, json);
        // editor.putString(AuthToken, loginData.getAuth_token());
        editor.commit();
    }

    public LoginResponse.DataBean.UserDetailBean getRegistration() {
        Gson gson = new Gson();
        String string = mypref.getString(RegisterInfo, "");
        if (!string.isEmpty())
            return gson.fromJson(string, LoginResponse.DataBean.UserDetailBean.class);
        else
            return null;
    }

    public void setAuthToken(String authToken) {//896320c98cfe4a7b2283a9c1438b93d6c21dbfa8
        editor.putString(AuthToken, authToken);
        editor.apply();
    }

    public String getAuthToken() {
        return mypref.getString(AuthToken, "");
    }


    public void setNotificatioStatus(String authToken) {//896320c98cfe4a7b2283a9c1438b93d6c21dbfa8
        editor.putString(notificationStatus, authToken);
        editor.apply();
    }

    public String getNotificatioStatus() {
        return mypref.getString(notificationStatus, "");
    }





    public void showPopup(boolean isShowPopup) {
        editor.putBoolean(isShow, isShowPopup);
        editor.apply();
    }

    public boolean getIsShowPopup() {
        return mypref.getBoolean(ScreenName, false);
    }

    public String getCartItemCount() {
        return mypref.getString(cartItemCount, "");//cartItemCount;
    }

    public void setCartItemCount(String itemCount) {
        editor.putString(cartItemCount, itemCount);
        editor.apply();
    }
  /*  public void setAuthToken(String authToken) {//896320c98cfe4a7b2283a9c1438b93d6c21dbfa8
        editor.putString(AuthToken, authToken);
        editor.apply();
    }*/

    public String getScreen() {
        return mypref.getString(ScreenName, "");
    }

    public void setScreen(String screenName) {
        editor.putString(ScreenName, screenName);
        editor.apply();
    }

    public void setUserLoggedIn() {
        editor.putBoolean(IsUserLoggedIn, true);
        editor.apply();
    }

    public boolean getUserLoggedIn() {
        return mypref.getBoolean(IsUserLoggedIn, false);
    }

    public String getSubscriptionScreen() {
        return mypref.getString(isShownSubscription, "no");
    }

    public void setSubscriptionScreen(String isShown) {
        editor.putString(isShownSubscription, isShown);
        editor.apply();
    }

    public void logout() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.cancelAll();
        }
        Intent showLogin = new Intent(context, LoginActivity.class);
        showLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        showLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(showLogin);
        editor.putBoolean(IsUserLoggedIn, false);
        editor.clear();
        //editor.commit();
        editor.apply();
    }

    public String getDeviceToke() {
        return mypref.getString(DeviceToken, "");
    }

    public void setDeviceToke(String token) {
        editor.putString(DeviceToken, token);
        editor.apply();
    }

    public String getAddress() {
        return mypref.getString(Address, "");
    }

    public void setAddress(String address) {
        editor.putString(Address, address);
        editor.apply();
    }

    public String getFilterSizeIds() {
        return mypref.getString(FilterSizeIds, "");
    }

    public void setFilterSizeIds(String filterUserName) {
        editor.putString(FilterSizeIds, filterUserName);
        editor.apply();
    }

    public void setFilterPrice(String minPrice, String maxPrice) {
        editor.putString(FilterMinPrice, minPrice);
        editor.putString(FilterMaxPrice, maxPrice);
        editor.apply();
    }

    public String getFilterColorIds() {
        return mypref.getString(FilterColorIds, "");
    }

    public void setFilterColorIds(String selected_interests) {
        editor.putString(FilterColorIds, selected_interests);
        editor.apply();
    }

    public String getFilterMinPrice() {
        return mypref.getString(FilterMinPrice, "");
    }

    public String getFilterMaxPrice() {
        return mypref.getString(FilterMaxPrice, "");
    }


    public void setProductDetail(String minValue, String maxValue) {
        editor.putString(productDetailMinValue, minValue);
        editor.putString(productDetailMaxValue, maxValue);
        editor.apply();
    }


    public String getProductDetailMinValue() {
        return mypref.getString(productDetailMinValue, "");
    }


    public String getProductDetailMaxValue() {
        return mypref.getString(productDetailMaxValue, "");
    }

    public String getIsEnglishLanguage() {
        //changeLang(isEnglishLanguage);
        return mypref.getString(isEnglishLanguage, "en");

    }

    public void setIsEnglishLanguage(String englishLanguage) {
        editor.putString(isEnglishLanguage, englishLanguage);
        editor.apply();
    }

    public void setLanguage(String language) {
        editor.putString(appLanguage, language);
        editor.apply();
    }

    public String getLanguage() {
        return mypref.getString(appLanguage, "");

    }

    public void loadLocale() {
        String langPref = "Language";
        SharedPreferences prefs = context.getSharedPreferences("CommonPrefs",
                Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "");
        changeLang(language);
    }

    public void changeLang(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        Locale myLocale = new Locale(lang);
        saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

    }

    public void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = context.getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.commit();
    }


    void setAppLocale(String locale) {
        Resources res = context.getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(locale.toLowerCase())); // API 17+ only.
        // Use conf.locale = new Locale(...) if targeting lower versions
        res.updateConfiguration(conf, dm);
    }

    public void setFilterPlaceInterest(String place, String interest) {
        editor.putString(FilterPlace, place);
        editor.putString(FilterInterestString, interest);
        editor.apply();
    }

    public String getFilterPlace() {
        return mypref.getString(FilterPlace, "");
    }

    public String getFilterInterestString() {
        return mypref.getString(FilterInterestString, "");
    }

    public int getOfferStatus() {
        return mypref.getInt(MakeOfferStatus, 4);
    }

    public void setOfferStatus(int status) {
        editor.putInt(MakeOfferStatus, status);
        editor.apply();
    }

    public void setFilterAgeDistance(String fromAge, String toAge, String rangMin, String rangMax) {
        editor.putString(FilterFromAge, fromAge);
        editor.putString(FilterToAge, toAge);
        editor.putString(FilterMinDistance, rangMin);
        editor.putString(FilterMaxDistance, rangMax);
        editor.apply();
    }

    public String getFilterFromAge() {
        return mypref.getString(FilterFromAge, "");
    }

    public String getFilterToAge() {
        return mypref.getString(FilterToAge, "");
    }

    public String getFilterMinDistance() {
        return mypref.getString(FilterMinDistance, "");
    }

    public String getFilterMaxDistance() {
        return mypref.getString(FilterMaxDistance, "");
    }

    public void setGenderIntentBodyEthnicity(String gender, String body_type, String ethnicity) {
        editor.putString(FilterGender, gender);
        editor.putString(FilterBodyType, body_type);
        editor.putString(FilterEthnicity, ethnicity);
        editor.apply();
    }

    public String getFilterGender() {
        return mypref.getString(FilterGender, "");
    }

    public String getFilterBodyType() {
        return mypref.getString(FilterBodyType, "");
    }

    public String getFilterEthnicity() {
        return mypref.getString(FilterEthnicity, "");
    }

    public String getFilterSortBy() {
        return mypref.getString(FilterSortBy, "");
    }

    public void setFilterSortBy(String sortByType) {
        editor.putString(FilterSortBy, sortByType);
        editor.apply();
    }

    public String getFilterRandomIndex() {
        return mypref.getString(FilterRandomIndex, "");
    }

    public void setFilterRandomIndex(String index) {
        editor.putString(FilterRandomIndex, index);
        editor.apply();
    }

    public boolean getUserGetRegistered() {
        return mypref.getBoolean(UserGetRegistered, false);
    }

    public void setUserGetRegistered(boolean status) {
        editor.putBoolean(UserGetRegistered, status);
        editor.apply();
    }

}