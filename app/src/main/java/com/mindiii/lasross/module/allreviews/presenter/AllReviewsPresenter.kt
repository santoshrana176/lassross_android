package com.mindiii.lasross.module.allreviews.presenter

import android.content.Context
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.errorResponse.ErrorUtils
import com.mindiii.lasross.module.allreviews.model.AllReviewsModel
import com.mindiii.lasross.network.API
import com.mindiii.lasross.network.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class AllReviewsPresenter(var allReviewsPresenter: ApiCallback.AllReviewsCallback, var mContext: Context) {

    var session: Session = Session(mContext)

    fun callAllReviewListApi(productId: String) {
        allReviewsPresenter.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val genderApi = api.callProductReviewApi(productId, session.authToken)
        genderApi.enqueue(object : Callback<AllReviewsModel> {
            override fun onResponse(call: Call<AllReviewsModel>, response: Response<AllReviewsModel>) {
                allReviewsPresenter.onHideBaseLoader()
                if (response.isSuccessful) {
                    allReviewsPresenter.onSuccessReviews(response.body())
                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        allReviewsPresenter.onTokenChangeError(apiErrors.message)
                    } else {
                        allReviewsPresenter.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<AllReviewsModel>, t: Throwable) {
                allReviewsPresenter.onHideBaseLoader()
                if (t is IOException) {
                    allReviewsPresenter.onError(mContext.getString(R.string.internet_connection))
                } else {
                    allReviewsPresenter.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }
}