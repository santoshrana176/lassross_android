package com.mindiii.lasross.module.productDetail.presenter

import android.content.Context
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.errorResponse.ErrorUtils
import com.mindiii.lasross.module.home.model.AddRemoveWishListResponse
import com.mindiii.lasross.module.productDetail.model.ProductDetailResponse
import com.mindiii.lasross.network.API
import com.mindiii.lasross.network.ServiceGenerator
import com.mindiii.lasross.network.ServiceGeneratorkotlin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ProductDetailPresenter(var productDetailCallback: ApiCallback.ProductDetailCallback, var mContext: Context) {
    val session = Session(mContext).registration.userId

    fun callProductDetailApi(productId: String) {
        productDetailCallback.onShowBaseLoader()
         val api = ServiceGeneratorkotlin().createService(API::class.java)
        val loginResponseCall = api.callProductDetailApi(productId, session)
        loginResponseCall.enqueue(object : Callback<ProductDetailResponse> {
            override fun onResponse(call: Call<ProductDetailResponse>, response: Response<ProductDetailResponse>) {
                productDetailCallback.onHideBaseLoader()
                try {
                    if (response.isSuccessful) {
                        productDetailCallback.onSuccessDetail(response.body())
                    } else {
                        val apiErrors = ErrorUtils.parseError(response)
                        productDetailCallback.onError(apiErrors.message)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ProductDetailResponse>, t: Throwable) {
                productDetailCallback.onHideBaseLoader()
                if (t is IOException) {
                    productDetailCallback.onError(mContext.getString(R.string.internet_connection))
                } else {
                    productDetailCallback.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }

    fun callAddRemoveWishListApi(productId: String) {
        val session = Session(mContext)
        productDetailCallback.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val genderApi = api.callAddRemoveWishListApi(session.authToken, productId)
        genderApi.enqueue(object : Callback<AddRemoveWishListResponse> {
            override fun onResponse(call: Call<AddRemoveWishListResponse>, response: Response<AddRemoveWishListResponse>) {
                productDetailCallback.onHideBaseLoader()
                if (response.isSuccessful) {
                    productDetailCallback.onSuccessAddRemoveWishList(response.body())
                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        productDetailCallback.onTokenChangeError(apiErrors.message)
                    } else {
                        productDetailCallback.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<AddRemoveWishListResponse>, t: Throwable) {
                productDetailCallback.onHideBaseLoader()
                if (t is IOException) {
                    productDetailCallback.onError(mContext.getString(R.string.internet_connection))
                } else {
                    productDetailCallback.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }
}