package com.mindiii.lasross.module.address.presenter

import android.content.Context
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.errorResponse.ErrorUtils
import com.mindiii.lasross.module.address.model.AddAddressResponse
import com.mindiii.lasross.network.API
import com.mindiii.lasross.network.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class AddAddressPresenter(var mContext: Context, var addAddressCallback: ApiCallback.AddAddressCallback) {

    fun callAddAddressApi(companyType: String, phoneNumber: String, fullAddress: String,
                          latitdude: String, longitude: String) {
        val session = Session(mContext)
        addAddressCallback.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val addAddressCallApi = api.callAddAddress(session.authToken, companyType
                , phoneNumber, fullAddress, latitdude, longitude)
        addAddressCallApi.enqueue(object : Callback<AddAddressResponse> {
            override fun onResponse(call: Call<AddAddressResponse>, response: Response<AddAddressResponse>) {
                addAddressCallback.onHideBaseLoader()
                if (response.isSuccessful) {
                    addAddressCallback.onSuccessAddAddress(response.body())
                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        addAddressCallback.onTokenChangeError(apiErrors.message)
                    } else {
                        addAddressCallback.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<AddAddressResponse>, t: Throwable) {
                addAddressCallback.onHideBaseLoader()
                if (t is IOException) {
                    addAddressCallback.onError(mContext.getString(R.string.internet_connection))
                } else {
                    addAddressCallback.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }

    fun callUpdateAddressApi(userAddressId: String, fullAddress: String, latitdude: String,
                             longitude: String, companyType: String, phoneNumber: String) {
        val session = Session(mContext)
        addAddressCallback.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)

        val addAddressCallApi = api.callUpdateAddress(session.authToken, userAddressId
                , fullAddress, latitdude, longitude, companyType, phoneNumber)
        addAddressCallApi.enqueue(object : Callback<AddAddressResponse> {
            override fun onResponse(call: Call<AddAddressResponse>, response: Response<AddAddressResponse>) {
                addAddressCallback.onHideBaseLoader()
                if (response.isSuccessful) {
                    addAddressCallback.onUpdateAddress(response.body())
                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        addAddressCallback.onTokenChangeError(apiErrors.message)
                    } else {
                        addAddressCallback.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<AddAddressResponse>, t: Throwable) {
                addAddressCallback.onHideBaseLoader()
                if (t is IOException) {
                    addAddressCallback.onError(mContext.getString(R.string.internet_connection))
                } else {
                    addAddressCallback.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }
}