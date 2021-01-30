package com.mindiii.lasross.module.cart.presenter

import android.content.Context
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.errorResponse.ErrorUtils
import com.mindiii.lasross.module.cart.model.AddTocartResponse
import com.mindiii.lasross.network.API
import com.mindiii.lasross.network.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class AddToCartPresenter(var mContext: Context, var addToCartCallback: ApiCallback.AddToCartCallback) {
    fun callAddToCartApi(productId: String, productQuantity: String, variants: String) {
        val session = Session(mContext)
        addToCartCallback.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val addToCartApi = api.callAddToCart(session.authToken, productId, productQuantity, variants)
        addToCartApi.enqueue(object : Callback<AddTocartResponse> {
            override fun onResponse(call: Call<AddTocartResponse>, response: Response<AddTocartResponse>) {
                addToCartCallback.onHideBaseLoader()
                if (response.isSuccessful) {
                    addToCartCallback.onSuccessAddToCart(response.body())
                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        addToCartCallback.onTokenChangeError(apiErrors.message)
                    } else {
                        addToCartCallback.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<AddTocartResponse>, t: Throwable) {
                addToCartCallback.onHideBaseLoader()
                if (t is IOException) {
                    //addToCartCallback.onError(mContext.getString(R.string.internet_connection))
                } else {
                    addToCartCallback.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }
}