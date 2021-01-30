package com.mindiii.lasross.module.notification

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindiii.lasross.R
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.ClickListener
import com.mindiii.lasross.base.LasrossParentKotlinActivity
import com.mindiii.lasross.module.faq.FAQActivity
import com.mindiii.lasross.module.notification.adapter.NotificationAdapter
import com.mindiii.lasross.module.notification.model.Data
import com.mindiii.lasross.module.notification.model.NotificationListModel
import com.mindiii.lasross.module.notification.model.ReadNotificationModel
import com.mindiii.lasross.module.notification.presenter.NotificationPresenter
import kotlinx.android.synthetic.main.notifications_activity_layout_27.*
import java.util.*

class NotificationsActivity : LasrossParentKotlinActivity(), View.OnClickListener, ApiCallback.NotificationCallback {
    /*{
        "status": "fail",
        "message": "Something went wrong. Please try again"
    }*/

    /*{
        "status": "success",
        "count": "62",
        "data": true
    }*/

    lateinit var notificationAdapter: NotificationAdapter
    lateinit var notificationList: ArrayList<Data>

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notifications_activity_layout_27)
        ivBackButton.setOnClickListener {
            onBackPressed()
        }
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = this.resources.getColor(R.color.home_header_bg1)
        notificationList = ArrayList()

        callNotificationListApi(this)
        setNotificationAdapter()
        //addFragment(NotificationsFragment(), false, R.id.flNotification)
    }

    fun callReadNotificationApi(notificationId: String) {
        NotificationPresenter(this, this).callReadNotificationApi(notificationId)
    }

    fun callNotificationListApi(context: Context) {
        NotificationPresenter(this, context).callNotificationListApi()
    }

    override fun onClick(p0: View?) {
    }

    override fun onHideBaseLoader() {
        hideLoader()
    }

    override fun onSuccessNotificationList(notificationListModel: NotificationListModel?) {
        notificationList.addAll(notificationListModel!!.data)
        //setNotificationAdapter()

        if (notificationList.size > 0) {
            tvNotFoundNotification.visibility = View.GONE
            rvNotificationListActivity.visibility = View.VISIBLE
            //setNotificationAdapter()
            notificationAdapter.notifyDataSetChanged()
        } else {
            tvNotFoundNotification.visibility = View.VISIBLE
            rvNotificationListActivity.visibility = View.GONE
        }
    }

    override fun onSuccessReadNotification(readNotificationModel: ReadNotificationModel?) {

    }

    override fun onTokenChangeError(errorMessage: String?) {
        showDialog(this)
    }

    override fun onShowBaseLoader() {
        showLoader()
    }

    override fun onError(errorMessage: String?) {
    }

    fun setNotificationAdapter() {
        notificationAdapter = NotificationAdapter(notificationList, this, object : ClickListener.NotificationListener {
            override fun onItemClick(position: Int) {

                callReadNotificationApi(notificationList[position].notificationId)

                startActivity(Intent(this@NotificationsActivity, FAQActivity::class.java)
                        .putExtra("screenCheck", "Order Status")
                        .putExtra("orderId", notificationList[position].reference_id))
            }

        })
        rvNotificationListActivity.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvNotificationListActivity.adapter = notificationAdapter
    }

    override fun onRestart() {
        super.onRestart()
        notificationList.clear()
        callNotificationListApi(this)
    }
}