package com.mindiii.lasross.module.myorder.presenter

import android.content.Context
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.errorResponse.ErrorUtils
import com.mindiii.lasross.module.myorder.model.MyOrdersModel
import com.mindiii.lasross.network.API
import com.mindiii.lasross.network.ServiceGenerator
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MyOrderPresenter(var mContext: Context, var myOrderListener: ApiCallback.MyOrderCallback) {
    var session: Session = Session(mContext)

    /*fun callMyOrderListApi() {
        var session: Session = Session(mContext)
        myOrderListener.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val myOrderApi = api.callMyOrderList(session.authToken)

        myOrderApi.enqueue(object : Callback<MyOrdersModel> {
            override fun onResponse(call: retrofit2.Call<MyOrdersModel>, response: Response<MyOrdersModel>) {
                myOrderListener.onHideBaseLoader()
                if(response.isSuccessful) {
                    myOrderListener.onSuccessMyOrderList(response.body())
                }
                else
                {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        myOrderListener.onTokenChangeError(apiErrors.message)
                    } else {
                        myOrderListener.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<MyOrdersModel>, t: Throwable) {
                myOrderListener.onHideBaseLoader()
                if (t is IOException) {
                    myOrderListener.onError(mContext.getString(R.string.internet_connection))
                } else {
                    myOrderListener.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }*/

    fun callMyOrderListApi() {
        myOrderListener.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val genderApi = api.callMyOrderList(session.authToken)
        genderApi.enqueue(object : Callback<MyOrdersModel> {
            override fun onResponse(call: retrofit2.Call<MyOrdersModel>, response: Response<MyOrdersModel>) {
                myOrderListener.onHideBaseLoader()
                if (response.isSuccessful) {
                    myOrderListener.onSuccessMyOrderList(response.body())
                } else {
                    val apiErrors = ErrorUtils.parseError(response)

                    if (apiErrors.message == "Invalid token") {
                        myOrderListener.onTokenChangeError(apiErrors.message)
                    } else {
                        myOrderListener.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<MyOrdersModel>, t: Throwable) {
                myOrderListener.onHideBaseLoader()
                if (t is IOException) {
                    myOrderListener.onError(mContext.getString(R.string.internet_connection))
                } else {
                    myOrderListener.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }
}