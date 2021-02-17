package com.mindiii.lasross.module.subscription.presenter

import android.content.Context
import com.mindiii.lasross.Lasross
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.errorResponse.ErrorUtils
import com.mindiii.lasross.module.subscription.SubscriptionResponse
import com.mindiii.lasross.module.subscription.presenter.model.SubscribeResponse
import com.mindiii.lasross.network.API
import com.mindiii.lasross.network.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class SubscriptionPresenter(var mContext: Context, var subscription: ApiCallback.SubscriptionCallback) {

    // get subscription plan details
    fun subscriptionDetail() {
        val session = Session(mContext)

        subscription.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val subscriptionApiCall = api.subscriptionApi(Lasross.appLanguage,session.authToken)
        subscriptionApiCall.enqueue(object : Callback<SubscriptionResponse> {
            override fun onResponse(call: Call<SubscriptionResponse>, response: Response<SubscriptionResponse>) {
                subscription.onHideBaseLoader()
                if (response.isSuccessful) {
                    subscription.onSuccessSubscription(response.body())

                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        subscription.onTokenChangeError(apiErrors.message)
                    } else {
                        subscription.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<SubscriptionResponse>, t: Throwable) {

                subscription.onHideBaseLoader()
                if (t is IOException) {
                    subscription.onError(mContext.getString(R.string.internet_connection))
                } else {
                    subscription.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })

    }

    // subsribe the selected plan
    fun subscribePlanApi(plan_id: String) {
        val session = Session(mContext)
        subscription.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val subscriptionApiCall = api.planSubscribeApi(Lasross.appLanguage,session.authToken, plan_id)
        subscriptionApiCall.enqueue(object : Callback<SubscribeResponse> {
            override fun onResponse(call: Call<SubscribeResponse>, response: Response<SubscribeResponse>) {

                subscription.onHideBaseLoader()
                if (response.isSuccessful) {
                    subscription.onSuccessSubscribe(response.body())

                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        subscription.onTokenChangeError(apiErrors.message)
                    } else {
                        subscription.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<SubscribeResponse>, t: Throwable) {
                subscription.onHideBaseLoader()
                if (t is IOException) {
                    subscription.onError(mContext.getString(R.string.internet_connection))
                } else {
                    subscription.onError(mContext.getString(R.string.oops_wrong))
                }
            }

        })

    }
}