package com.mindiii.lasross.module.settings.presenter

import android.content.Context
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.errorResponse.ErrorUtils
import com.mindiii.lasross.module.address.model.AddAddressResponse
import com.mindiii.lasross.module.home.model.LogoutResponse
import com.mindiii.lasross.module.settings.model.LanguageModel
import com.mindiii.lasross.module.settings.model.NotificationAlertResponse
import com.mindiii.lasross.module.settings.model.TermsPolicyResponse
import com.mindiii.lasross.module.subscription.presenter.model.SubscribeResponse
import com.mindiii.lasross.network.API
import com.mindiii.lasross.network.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class SettingsPresenter(var mContext: Context, var changePassword: ApiCallback.SettingsCallback) {

    fun changePasseword(oldPassword: String, newPassword: String, cPassword: String) {
        val session = Session(mContext)
        changePassword.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val changePasswordApi = api.changePasswordApi(session.authToken, oldPassword, newPassword, cPassword)
        changePasswordApi.enqueue(object : Callback<AddAddressResponse> {
            override fun onResponse(call: Call<AddAddressResponse>, response: Response<AddAddressResponse>) {
                changePassword.onHideBaseLoader()
                if (response.isSuccessful) {
                    changePassword.onSuccessChangePassword(response.body())
                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        changePassword.onTokenChangeError(apiErrors.message)
                    } else {
                        changePassword.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<AddAddressResponse>, t: Throwable) {

                changePassword.onHideBaseLoader()
                if (t is IOException) {
                    changePassword.onError(mContext.getString(R.string.internet_connection))
                } else {
                    changePassword.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })


    }

    fun callLogoutApi() {
        val session = Session(mContext)
        changePassword.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val genderApi = api.callLogoutApi(session.getAuthToken())
        genderApi.enqueue(object : Callback<LogoutResponse> {
            override fun onResponse(call: Call<LogoutResponse>, response: Response<LogoutResponse>) {
                changePassword.onHideBaseLoader()
                if (response.isSuccessful) {
                    changePassword.onSuccessLogout(response.body())
                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        changePassword.onTokenChangeError(apiErrors.message)
                    } else {
                        changePassword.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                changePassword.onHideBaseLoader()
                if (t is IOException) {
                    changePassword.onError(mContext.getString(R.string.internet_connection))
                } else {
                    changePassword.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }

    fun callNotificationOnOff(status: String) {
        val session = Session(mContext)
        changePassword.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val genderApi = api.callNotificationOnOff(session.getAuthToken(),status)
        genderApi.enqueue(object : Callback<NotificationAlertResponse> {
            override fun onResponse(call: Call<NotificationAlertResponse>, response: Response<NotificationAlertResponse>) {
                changePassword.onHideBaseLoader()
                if (response.isSuccessful) {
                    changePassword.onSuccesNotifcationOnOff(response.body())
                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        changePassword.onTokenChangeError(apiErrors.message)
                    } else {
                        changePassword.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<NotificationAlertResponse>, t: Throwable) {
                changePassword.onHideBaseLoader()
                if (t is IOException) {
                    changePassword.onError(mContext.getString(R.string.internet_connection))
                } else {
                    changePassword.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }

    fun callUserCurrentSubscribedPlanApi() {
        val session = Session(mContext)
        changePassword.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val currentPlan = api.callCurrentSubscribedPlan(session.authToken)
        currentPlan.enqueue(object : Callback<SubscribeResponse> {
            override fun onResponse(call: Call<SubscribeResponse>, response: Response<SubscribeResponse>) {

                changePassword.onHideBaseLoader()
                if (response.isSuccessful) {
                    changePassword.onSuccessCurrentSubscribedPlan(response.body())

                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        changePassword.onTokenChangeError(apiErrors.message)
                    } else {
                        changePassword.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<SubscribeResponse>, t: Throwable) {
                changePassword.onHideBaseLoader()
                if (t is IOException) {
                    changePassword.onError(mContext.getString(R.string.internet_connection))
                } else {
                    changePassword.onError(mContext.getString(R.string.oops_wrong))
                }
            }

        })

    }

    fun callTermsPolicyApi() {
        val session = Session(mContext)
        changePassword.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val currentPlan = api.callTermsPolicyApi()
        currentPlan.enqueue(object : Callback<TermsPolicyResponse> {
            override fun onResponse(call: Call<TermsPolicyResponse>, response: Response<TermsPolicyResponse>) {

                changePassword.onHideBaseLoader()
                if (response.isSuccessful) {
                    changePassword.onSuccessTermsPolicy(response.body())

                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        changePassword.onTokenChangeError(apiErrors.message)
                    } else {
                        changePassword.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<TermsPolicyResponse>, t: Throwable) {
                changePassword.onHideBaseLoader()
                if (t is IOException) {
                    changePassword.onError(mContext.getString(R.string.internet_connection))
                } else {
                    changePassword.onError(mContext.getString(R.string.oops_wrong))
                }
            }

        })

    }

}