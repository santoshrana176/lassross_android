package com.mindiii.lasross.module.orderdetail.presenter

import android.content.Context
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.errorResponse.ErrorUtils
import com.mindiii.lasross.module.address.model.AddAddressResponse
import com.mindiii.lasross.module.orderdetail.model.OrderDetailModel
import com.mindiii.lasross.network.API
import com.mindiii.lasross.network.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class OrderDetailPresenter(var mContext: Context, var orderDetailListener: ApiCallback.OrderDetailCallback) {
    var session: Session = Session(mContext)

    fun callOrderDetailApi(orderId: String) {
        orderDetailListener.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val genderApi = api.callOrderDetailApi(session.authToken, orderId)
        genderApi.enqueue(object : Callback<OrderDetailModel> {
            override fun onResponse(call: retrofit2.Call<OrderDetailModel>, response: Response<OrderDetailModel>) {
                orderDetailListener.onHideBaseLoader()
                if (response.isSuccessful) {
                    orderDetailListener.onSuccessOrderDetail(response.body())
                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        orderDetailListener.onTokenChangeError(apiErrors.message)
                    } else {
                        orderDetailListener.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<OrderDetailModel>, t: Throwable) {
                orderDetailListener.onHideBaseLoader()
                if (t is IOException) {
                    orderDetailListener.onError(mContext.getString(R.string.internet_connection))
                } else {
                    orderDetailListener.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }

    fun callRatingReviewApi(productId: String, ratings: Float, description: String, item_id: String) {
        orderDetailListener.onShowBaseLoader()

        val api = ServiceGenerator.createService(API::class.java)
        val ratingApi = api.ratingAndReviews(session.authToken, productId, ratings.toString(), item_id, description)
        ratingApi.enqueue(object : Callback<AddAddressResponse> {
            override fun onResponse(call: Call<AddAddressResponse>, response: Response<AddAddressResponse>) {
                orderDetailListener.onHideBaseLoader()
                if (response.isSuccessful) {
                    orderDetailListener.onSuccessProductRating(response.body())
                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        orderDetailListener.onTokenChangeError(apiErrors.message)
                    } else {
                        orderDetailListener.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<AddAddressResponse>, t: Throwable) {
                orderDetailListener.onHideBaseLoader()

                if (t is IOException) {
                    orderDetailListener.onError(mContext.getString(R.string.internet_connection))
                } else {
                    orderDetailListener.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })

    }


}