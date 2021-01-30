package com.mindiii.lasross.module.activeplan

import android.app.ActionBar
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindiii.lasross.R
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.LasrossParentKotlinActivity
import com.mindiii.lasross.module.activeplan.model.CancelSubscriptionResponse
import com.mindiii.lasross.module.activeplan.presenter.ActivePlanPresenter
import com.mindiii.lasross.module.subscription.presenter.adapter.SubscriptionItemDescriptionAdapter
import com.mindiii.lasross.module.subscription.presenter.model.SubscribeResponse
import kotlinx.android.synthetic.main.active_plan_screen.*
import kotlinx.android.synthetic.main.logout_view.*
import java.util.*


class ActivePlanActivity : LasrossParentKotlinActivity(), View.OnClickListener, ApiCallback.ActivePlanCallback {
    var date = ""
    private lateinit var itemDescriptionList: ArrayList<String>
    private lateinit var subscriptionItemDescriptionAdapter: SubscriptionItemDescriptionAdapter

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.active_plan_screen)
        val bundle = intent.extras
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = this.resources.getColor(R.color.home_header_bg1)

        ivActivePlanBack.setOnClickListener(this)
        itemDescriptionList = ArrayList()

        val subscribeResponse = bundle!!.getSerializable("subscribeResponse") as SubscribeResponse?
        val isCancelled = intent.getStringExtra("isCancelled")

        if (isCancelled!!.equals("0")) {
            tvRenewDateTextActivePlan.text = getString(R.string.next_billing_date)
            tvStopAutoRecurring.setOnClickListener(this)
        } else {
            tvRenewDateTextActivePlan.text = getString(R.string.expiry_date_active_plan)
            tvStopAutoRecurring.visibility = View.GONE
        }

        date = DateFormatChange(subscribeResponse!!.data.end_date)
        tvRenewDateActivePlan.text = date

        if (subscribeResponse.data.plan_title.trim().equals("Golden Plan", ignoreCase = true)) {
            setSubscribeData(R.drawable.goldenplan_icon_orange, R.string.golden_plan, subscribeResponse)
        } else if (subscribeResponse.data.plan_title.trim().equals("Silver Plan", ignoreCase = true)) {
            setSubscribeData(R.drawable.silverplan_icon_orange, R.string.silver_plan, subscribeResponse)
        } else if (subscribeResponse.data.plan_title.trim().equals("Free", ignoreCase = true)) {
            tvSubscriptionPlanImage.setImageResource(R.drawable.free_icon_orange)
            tvSubscriptionPlanTitle.setText(R.string.free)
            tvSubscriptionPlan.visibility = View.GONE
        }
    }

    fun setSubscribeData(imageInt: Int, planNameInt: Int, subscribeResponse: SubscribeResponse) {
        tvSubscriptionPlanImage.setImageResource(imageInt)
        tvSubscriptionPlanTitle.setText(planNameInt)
        tvSubscriptionPlan.visibility = View.VISIBLE

        tvplanPriceCurrency.text = subscribeResponse.data.plan_currency
        tvSubscriptionPrice.text = " " + getTwoValueAfterDecimal(subscribeResponse.data.plan_price)
        tvSubscriptionPlanDurationType.text = " / " + subscribeResponse.data.plan_duration + " " + subscribeResponse.data.plan_duration_type

        val description = subscribeResponse.data.plan_description
        if (description.equals(""))
            rvItemDescriptionListSubscription.visibility = View.GONE
        else
            rvItemDescriptionListSubscription.visibility = View.VISIBLE

        val arrSplit = description.split("| ")
        for (i in 0..arrSplit.size - 1) {
            itemDescriptionList.add(arrSplit[i])
        }

        subscriptionItemDescriptionAdapter = SubscriptionItemDescriptionAdapter(itemDescriptionList, this)
        rvItemDescriptionListSubscription.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvItemDescriptionListSubscription.adapter = subscriptionItemDescriptionAdapter
    }

    override fun onClick(p0: View?) {

        if (p0 != null) {
            when (p0.id) {
                R.id.ivActivePlanBack -> onBackPressed()
                R.id.tvStopAutoRecurring -> showCancelSubscriptionDialog(this, resources.getString(R.string.confirm_cancel_subscription_text))
                //callCancelSubscriptionApi()
            }
        }
    }

    fun callCancelSubscriptionApi() {
        ActivePlanPresenter(this, this).callCancelSubscriptionApi()
    }

    fun showCancelSubscriptionDialog(activity: Activity, message: String) {
        val dialog = Dialog(activity)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        Objects.requireNonNull(dialog.window)!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)

        dialog.setContentView(R.layout.logout_view)

        dialog.tvMessagesLogout.setText(message)

        dialog.tvPopupNoLogout.setOnClickListener(View.OnClickListener { dialog.dismiss() })

        dialog.tvPopupYesLogout.setOnClickListener(View.OnClickListener {
            callCancelSubscriptionApi()
            dialog.dismiss()
        })

        dialog.show()
    }

    override fun onHideBaseLoader() {
        hideLoader()
    }

    override fun onCancelSubscription(response: CancelSubscriptionResponse?) {
        if (response!!.status.equals("fail")) {
            toastMessage(response.message)
        } else {
            showSubscribeDialogActivity(this, getString(R.string.auto_recurr_stop_success))
        }
    }

    fun showSubscribeDialogActivity(mContext: Context, message: String) {
        val dialog = Dialog(mContext)
        Objects.requireNonNull<Window>(dialog.window).setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.session_expired_dialog)
        val tvOK: TextView = dialog.findViewById(R.id.tvOK)
        val tvTitleOfVal: TextView = dialog.findViewById(R.id.tvTitleOfVal)
        tvTitleOfVal.text = message
        tvOK.setOnClickListener {
            dialog.dismiss()
            tvRenewDateTextActivePlan.text = getString(R.string.expiry_date_active_plan)
            tvStopAutoRecurring.visibility = View.GONE
            tvRenewDateActivePlan.text = date
        }
        dialog.show()
        val window = dialog.window!!
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
    }

    override fun onTokenChangeError(errorMessage: String?) {
        showDialog(this)
    }

    override fun onShowBaseLoader() {
        showLoader()
    }

    override fun onError(errorMessage: String?) {
        toastMessage(errorMessage.toString())
    }
}