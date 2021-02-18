package com.mindiii.lasross.module.delivery

import android.content.Context
import com.mindiii.lasross.Lasross
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.errorResponse.ErrorUtils
import com.mindiii.lasross.module.delivery.model.DeliveryResponse
import com.mindiii.lasross.network.API
import com.mindiii.lasross.network.ServiceGeneratorkotlin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class DeliveryPresenter(var mContext: Context, var myAddressCallback: ApiCallback.DeliveryCallback) {

    fun callDeliveryDetail(shipping_add_id: String) {
        myAddressCallback.onShowBaseLoader()
        val api = ServiceGeneratorkotlin().createService(API::class.java)
        val loginResponseCall = api.callFinalAmount(Lasross.appLanguage,Session(mContext).authToken, shipping_add_id)
        loginResponseCall.enqueue(object : Callback<DeliveryResponse> {
            override fun onResponse(call: Call<DeliveryResponse>, response: Response<DeliveryResponse>) {
                myAddressCallback.onHideBaseLoader()
                try {
                    if (response.isSuccessful) {
                        myAddressCallback.onSuccessDeliveryDetails(response.body())
                    } else {
                        val apiErrors = ErrorUtils.parseError(response)
                       // myAddressCallback.onError(apiErrors.message)
                        if (apiErrors.message == "Invalid token") {
                            myAddressCallback.onTokenChangeError(apiErrors.message)
                        } else {
                            myAddressCallback.onError(apiErrors.message)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<DeliveryResponse>, t: Throwable) {
                myAddressCallback.onHideBaseLoader()
                if (t is IOException) {
                    myAddressCallback.onError(mContext.getString(R.string.internet_connection))
                } else {
                    myAddressCallback.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }
}