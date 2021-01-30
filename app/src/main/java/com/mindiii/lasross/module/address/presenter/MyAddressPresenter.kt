package com.mindiii.lasross.module.address.presenter

import android.content.Context
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.errorResponse.ErrorUtils
import com.mindiii.lasross.module.address.model.AddAddressResponse
import com.mindiii.lasross.module.address.model.UserAddressListResponse
import com.mindiii.lasross.network.API
import com.mindiii.lasross.network.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MyAddressPresenter(var mContext: Context, var myAddressCallback: ApiCallback.MyAddressCallback) {

    fun callUserAddressListApi(offset: String, limit: String) {
        val session = Session(mContext)
        myAddressCallback.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val userAddressListCallApi = api.callUserAddressList(session.authToken, offset, limit)
        userAddressListCallApi.enqueue(object : Callback<UserAddressListResponse> {
            override fun onResponse(call: Call<UserAddressListResponse>, response: Response<UserAddressListResponse>) {
                myAddressCallback.onHideBaseLoader()
                if (response.isSuccessful) {
                    myAddressCallback.onSuccessUserAddressList(response.body())
                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        myAddressCallback.onTokenChangeError(apiErrors.message)
                    } else {
                        myAddressCallback.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<UserAddressListResponse>, t: Throwable) {
                myAddressCallback.onHideBaseLoader()
                if (t is IOException) {
                    //myAddressCallback.onError(mContext.getString(R.string.internet_connection))
                } else {
                    myAddressCallback.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }

    fun callRemoveAddressApi(userAddressId: String) {
        val session = Session(mContext)
        myAddressCallback.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val deleteAddressCallApi = api.callDeleteUserAddress(session.authToken, userAddressId)
        deleteAddressCallApi.enqueue(object : Callback<AddAddressResponse> {
            override fun onResponse(call: Call<AddAddressResponse>, response: Response<AddAddressResponse>) {
                myAddressCallback.onHideBaseLoader()
                if (response.isSuccessful) {
                    myAddressCallback.onDeleteUserAddress(response.body())
                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        myAddressCallback.onTokenChangeError(apiErrors.message)
                    } else {
                        myAddressCallback.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<AddAddressResponse>, t: Throwable) {
                myAddressCallback.onHideBaseLoader()
                if (t is IOException) {
                    //myAddressCallback.onError(mContext.getString(R.string.internet_connection))
                } else {
                    myAddressCallback.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }
}