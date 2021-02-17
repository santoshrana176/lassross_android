package com.mindiii.lasross.module.settings.presenter

import android.content.Context
import com.mindiii.lasross.Lasross
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.errorResponse.ErrorUtils
import com.mindiii.lasross.module.settings.model.FeedbackResponse
import com.mindiii.lasross.network.API
import com.mindiii.lasross.network.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class LeaveFeedbackPresenter(var mContext: Context, var leavefeedback: ApiCallback.LeaveFeedbackCallback) {

    fun callLeaveFeedback(feedback: String) {
        val session = Session(mContext)
        leavefeedback.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val leavefeedApi = api.callFeedBackApi(Lasross.appLanguage,session.authToken, feedback)
        leavefeedApi.enqueue(object : Callback<FeedbackResponse> {
            override fun onResponse(call: Call<FeedbackResponse>, response: Response<FeedbackResponse>) {
                leavefeedback.onHideBaseLoader()
                if (response.isSuccessful) {
                    leavefeedback.onSuccessSendFeedback(response.body())
                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        leavefeedback.onTokenChangeError(apiErrors.message)
                    } else {
                        leavefeedback.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<FeedbackResponse>, t: Throwable) {
                leavefeedback.onHideBaseLoader()
                if (t is IOException) {
                    leavefeedback.onError(mContext.getString(R.string.internet_connection))
                } else {
                    leavefeedback.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }
}