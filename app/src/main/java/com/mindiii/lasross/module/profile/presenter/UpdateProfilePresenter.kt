package com.mindiii.lasross.module.profile.presenter

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.errorResponse.ErrorUtils
import com.mindiii.lasross.module.loginregistration.model.LoginResponse
import com.mindiii.lasross.network.API
import com.mindiii.lasross.network.ServiceGeneratorkotlin
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException


class UpdateProfilePresenter(var updateProfileCallback: ApiCallback.UpdateProfileCallback, var mContext: Context) {

    private fun getRealPathFromURIPath(contentURI: Uri): String? {
        val cursor = mContext.contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) {
            return contentURI.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            return cursor.getString(idx)
        }
    }

    fun callUpdateProfileApi(fullName1: String, image: String, userAddressId1: String, full_address1: String,
                             longitude1: String, phone_number1: String
                             , latitude1: String) {
        updateProfileCallback.onShowBaseLoader()
        var body: MultipartBody.Part? = null
        if (image.isNotEmpty()) {
            var uriString = Uri.parse(image)
            val file = File(getRealPathFromURIPath(uriString))
            val requestBody = RequestBody.create(MediaType.parse("*/*"), file)
            body = MultipartBody.Part.createFormData("profile_image", file.getName(), requestBody)
        }
        val fullName = RequestBody.create(MediaType.parse("text/plain"), fullName1)
        val userAddressId = RequestBody.create(MediaType.parse("text/plain"), userAddressId1)
        val fullAddress = RequestBody.create(MediaType.parse("text/plain"), full_address1)
        val longitude = RequestBody.create(MediaType.parse("text/plain"), longitude1)
        val phoneNumber = RequestBody.create(MediaType.parse("text/plain"), phone_number1)
        val latitude = RequestBody.create(MediaType.parse("text/plain"), latitude1)


        val api = ServiceGeneratorkotlin().createService(API::class.java)
        val loginResponseCall = api.callUpdateProfileApi(Session(mContext).authToken, fullName,
                userAddressId, fullAddress, longitude, phoneNumber, latitude, body)
        loginResponseCall.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                updateProfileCallback.onHideBaseLoader()
                try {
                    if (response.isSuccessful) {
                        updateProfileCallback.onSuccessUpdateProfile(response.body())
                    } else {
                        val apiErrors = ErrorUtils.parseError(response)
                        if (apiErrors.message.equals("Invalid token")) {
                            updateProfileCallback.onTokenChangeError(apiErrors.message)
                        } else {
                            updateProfileCallback.onError(apiErrors.message)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                updateProfileCallback.onHideBaseLoader()
                if (t is IOException) {
                    updateProfileCallback.onError(mContext.getString(R.string.internet_connection))
                } else {
                    updateProfileCallback.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }
}
