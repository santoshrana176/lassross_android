package com.mindiii.lasross.module.payment.presenter

import android.content.Context
import com.mindiii.lasross.Lasross
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.errorResponse.ErrorUtils
import com.mindiii.lasross.module.payment.model.FinalPaymentResponse
import com.mindiii.lasross.network.API
import com.mindiii.lasross.network.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MyCArdPaymentPresenter(var mContext: Context, var payment: ApiCallback.PaymentCallBack) {

    fun finalPaymentApi(payment_mode: String, source_type: String, source: String, shipping_add_id: String) {
        val session = Session(mContext)
        payment.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val paymentCallApi = api.finalPaymentApi(Lasross.appLanguage,session.authToken, payment_mode, source_type, source, shipping_add_id)
        paymentCallApi.enqueue(object : Callback<FinalPaymentResponse> {
            override fun onFailure(call: Call<FinalPaymentResponse>, t: Throwable) {
                payment.onHideBaseLoader()
                if (t is IOException) {
                    payment.onError(mContext.getString(R.string.internet_connection))
                } else {
                    payment.onError(mContext.getString(R.string.oops_wrong))
                }
            }

            override fun onResponse(call: Call<FinalPaymentResponse>, response: Response<FinalPaymentResponse>) {
                payment.onHideBaseLoader()
                if (response.isSuccessful) {
                    payment.onSuccessPayment(response.body())

                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        payment.onTokenChangeError(apiErrors.message)
                    } else {
                        payment.onError(apiErrors.message)
                    }
                }
            }
        })
    }

}