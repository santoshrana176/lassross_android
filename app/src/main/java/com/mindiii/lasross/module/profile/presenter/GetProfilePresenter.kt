package com.mindiii.lasross.module.profile.presenter

import android.content.Context
import com.mindiii.lasross.Lasross
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.errorResponse.ErrorUtils
import com.mindiii.lasross.module.cart.model.CartListResponse
import com.mindiii.lasross.module.loginregistration.model.LoginResponse
import com.mindiii.lasross.module.notification.model.NotificationListModel
import com.mindiii.lasross.network.API
import com.mindiii.lasross.network.ServiceGenerator
import com.mindiii.lasross.network.ServiceGeneratorkotlin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
/*language---hadder*/
class GetProfilePresenter(var getProfileCallback: ApiCallback.GetProfileCallback, var mContext: Context) {
    fun callGetProfileApi() {
        getProfileCallback.onShowBaseLoader()
        val api = ServiceGeneratorkotlin().createService(API::class.java)
        val loginResponseCall = api.callGetProfile(Lasross.appLanguage,Session(mContext).registration.auth_token)
        loginResponseCall.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                getProfileCallback.onHideBaseLoader()
                try {
                    if (response.isSuccessful) {
                        getProfileCallback.onSuccessGetProfile(response.body())
                    } else {
                        val apiErrors = ErrorUtils.parseError(response)
                        if (apiErrors.message.equals("Invalid token")) {
                            getProfileCallback.onTokenChangeError(apiErrors.message)
                        } else {
                            getProfileCallback.onError(apiErrors.message)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                getProfileCallback.onHideBaseLoader()
                if (t is IOException) {
                    getProfileCallback.onError(mContext.getString(R.string.internet_connection))
                } else {
                    getProfileCallback.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }

    fun callCartItemCountForHomeApi(offset: String) {
        val session = Session(mContext)//authToken -> 573fe3870d32c43ac0a5c1ba2a0227a60777fb97
        val token=session.authToken
        getProfileCallback.onShowBaseLoader()//1f3fe7b8dae2c0631ee2fb0f2d78e59174904f38
        val api = ServiceGenerator.createService(API::class.java)
        val cartListApi = api.callCartList(Lasross.appLanguage,token, offset, "18")
        cartListApi.enqueue(object : Callback<CartListResponse> {
            override fun onResponse(call: Call<CartListResponse>, response: Response<CartListResponse>) {
                getProfileCallback.onHideBaseLoader()
                if (response.isSuccessful) {
                    getProfileCallback.onSuccessGetCart(response.body())
                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        getProfileCallback.onTokenChangeError(apiErrors.message)
                    } else {
                        getProfileCallback.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<CartListResponse>, t: Throwable) {
                getProfileCallback.onHideBaseLoader()
                if (t is IOException) {
                    getProfileCallback.onError(mContext.getString(R.string.internet_connection))
                } else {
                    getProfileCallback.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }

    fun callNotificationCountForHomeApi() {
        val session = Session(mContext)
        getProfileCallback.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val cartListApi = api.callNotificationList(Lasross.appLanguage,session.authToken)
        cartListApi.enqueue(object : Callback<NotificationListModel> {
            override fun onResponse(call: Call<NotificationListModel>, response: Response<NotificationListModel>) {
                getProfileCallback.onHideBaseLoader()
                if (response.isSuccessful) {
                    getProfileCallback.onSuccessNotificationList(response.body())
                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        getProfileCallback.onTokenChangeError(apiErrors.message)
                    } else {
                        getProfileCallback.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<NotificationListModel>, t: Throwable) {
                getProfileCallback.onHideBaseLoader()
                if (t is IOException) {
                    //getProfileCallback.onError(mContext.getString(R.string.internet_connection))
                } else {
                    //getProfileCallback.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }
}