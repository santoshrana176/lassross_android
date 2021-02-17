package com.mindiii.lasross.module.wishlist.presenter

import android.content.Context
import com.mindiii.lasross.Lasross
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.errorResponse.ErrorUtils
import com.mindiii.lasross.module.home.model.AddRemoveWishListResponse
import com.mindiii.lasross.module.wishlist.model.AllClearResponse
import com.mindiii.lasross.module.wishlist.model.WishListResponse
import com.mindiii.lasross.network.API
import com.mindiii.lasross.network.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class WishListPresenter(var userWishListCallback: ApiCallback.UserWishListCallback, var mContext: Context) {

    var session: Session = Session(mContext)

    fun callUserWishListApi(offset: String) {
        userWishListCallback.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val genderApi = api.callUserWishList(Lasross.appLanguage,session.authToken, offset, "18")
        genderApi.enqueue(object : Callback<WishListResponse> {
            override fun onResponse(call: Call<WishListResponse>, response: Response<WishListResponse>) {
                userWishListCallback.onHideBaseLoader()
                if (response.isSuccessful) {
                    userWishListCallback.onSuccessUserWishList(response.body())
                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        userWishListCallback.onTokenChangeError(apiErrors.message)
                    } else {
                        userWishListCallback.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<WishListResponse>, t: Throwable) {
                userWishListCallback.onHideBaseLoader()
                if (t is IOException) {
                    userWishListCallback.onError(mContext.getString(R.string.internet_connection))
                } else {
                    userWishListCallback.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }

    fun callAddRemoveWishListApi(productId: String) {
        userWishListCallback.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val genderApi = api.callAddRemoveWishListApi(Lasross.appLanguage,session.authToken, productId)
        genderApi.enqueue(object : Callback<AddRemoveWishListResponse> {
            override fun onResponse(call: Call<AddRemoveWishListResponse>, response: Response<AddRemoveWishListResponse>) {
                userWishListCallback.onHideBaseLoader()
                if (response.isSuccessful()) {
                    userWishListCallback.onSuccessAddRemoveWishList(response.body())
                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        userWishListCallback.onTokenChangeError(apiErrors.message)
                    } else {
                        userWishListCallback.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<AddRemoveWishListResponse>, t: Throwable) {
                userWishListCallback.onHideBaseLoader()
                if (t is IOException) {
                    userWishListCallback.onError(mContext.getString(R.string.internet_connection))
                } else {
                    userWishListCallback.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }


    fun callAllClearWishListApi() {
        userWishListCallback.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val allClearWishListApi = api.callAllClearWishList(Lasross.appLanguage,session.registration!!.auth_token)
        allClearWishListApi.enqueue(object : Callback<AllClearResponse> {
            override fun onResponse(call: Call<AllClearResponse>, response: Response<AllClearResponse>) {
                userWishListCallback.onHideBaseLoader()
                if (response.isSuccessful()) {
                    userWishListCallback.onSuccessAllClearWishList(response.body())
                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        userWishListCallback.onTokenChangeError(apiErrors.message)
                    } else {
                        userWishListCallback.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<AllClearResponse>, t: Throwable) {
                userWishListCallback.onHideBaseLoader()
                if (t is IOException) {
                    userWishListCallback.onError(mContext.getString(R.string.internet_connection))
                } else {
                    userWishListCallback.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }
}