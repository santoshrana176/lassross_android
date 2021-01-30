package com.mindiii.lasross.module.notification.presenter

import android.content.Context
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.errorResponse.ErrorUtils
import com.mindiii.lasross.module.notification.model.NotificationListModel
import com.mindiii.lasross.module.notification.model.ReadNotificationModel
import com.mindiii.lasross.network.API
import com.mindiii.lasross.network.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class NotificationPresenter(var notificationCallback: ApiCallback.NotificationCallback, var mContext: Context) {

    var session: Session = Session(mContext)

    fun callNotificationListApi() {
        notificationCallback.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val genderApi = api.callNotificationList(session.authToken)
        genderApi.enqueue(object : Callback<NotificationListModel> {
            override fun onResponse(call: Call<NotificationListModel>, response: Response<NotificationListModel>) {
                notificationCallback.onHideBaseLoader()
                if (response.isSuccessful) {
                    notificationCallback.onSuccessNotificationList(response.body())
                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        notificationCallback.onTokenChangeError(apiErrors.message)
                    } else {
                        notificationCallback.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<NotificationListModel>, t: Throwable) {
                notificationCallback.onHideBaseLoader()
                if (t is IOException) {
                    notificationCallback.onError(mContext.getString(R.string.internet_connection))
                } else {
                    notificationCallback.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }

    fun callReadNotificationApi(notificationID: String) {
        notificationCallback.onShowBaseLoader()
        val api = ServiceGenerator.createService(API::class.java)
        val genderApi = api.callReadNotificationApi(session.authToken, notificationID)
        genderApi.enqueue(object : Callback<ReadNotificationModel> {
            override fun onResponse(call: Call<ReadNotificationModel>, response: Response<ReadNotificationModel>) {
                notificationCallback.onHideBaseLoader()
                if (response.isSuccessful) {
                    notificationCallback.onSuccessReadNotification(response.body())
                } else {
                    val apiErrors = ErrorUtils.parseError(response)
                    if (apiErrors.message == "Invalid token") {
                        notificationCallback.onTokenChangeError(apiErrors.message)
                    } else {
                        notificationCallback.onError(apiErrors.message)
                    }
                }
            }

            override fun onFailure(call: Call<ReadNotificationModel>, t: Throwable) {
                notificationCallback.onHideBaseLoader()
                if (t is IOException) {
                    notificationCallback.onError(mContext.getString(R.string.internet_connection))
                } else {
                    notificationCallback.onError(mContext.getString(R.string.oops_wrong))
                }
            }
        })
    }
}