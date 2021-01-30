package com.mindiii.lasross.module.activeplan.presenter

import android.content.Context
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.errorResponse.ErrorUtils
import com.mindiii.lasross.module.activeplan.model.CancelSubscriptionResponse
import com.mindiii.lasross.network.API
import com.mindiii.lasross.network.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ActivePlanPresenter(var context: Context, var activePlan: ApiCallback.ActivePlanCallback) {

    fun callCancelSubscriptionApi() {
        activePlan.onShowBaseLoader()
        var session = Session(context)
        val api = ServiceGenerator.createService(API::class.java)
        val callCancel = api.callCancelSubscription(session.authToken)
        callCancel.enqueue(object : Callback<CancelSubscriptionResponse> {
            override fun onResponse(call: Call<CancelSubscriptionResponse>, response: Response<CancelSubscriptionResponse>) {
                activePlan.onHideBaseLoader()
                if (response.isSuccessful) {
                    activePlan.onCancelSubscription(response.body())
                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        activePlan.onTokenChangeError(apiErrors.message)
                    } else {
                        activePlan.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<CancelSubscriptionResponse>, t: Throwable) {
                activePlan.onHideBaseLoader()
                if (t is IOException) {
                    activePlan.onError(context.getString(R.string.internet_connection))
                } else {
                    activePlan.onError(context.getString(R.string.oops_wrong))
                }
            }
        })
    }
}