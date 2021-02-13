package com.mindiii.lasross.module.settings

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.mindiii.lasross.R
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.LasrossParentKotlinActivity
import com.mindiii.lasross.module.settings.model.FeedbackResponse
import com.mindiii.lasross.module.settings.presenter.LeaveFeedbackPresenter
import com.mindiii.lasross.utils.CommonUtils
import kotlinx.android.synthetic.main.leave_feedback_activity.*

class LeaveFeedbackActivity : LasrossParentKotlinActivity(), View.OnClickListener, ApiCallback.LeaveFeedbackCallback {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.leave_feedback_activity)

        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = this.resources.getColor(R.color.home_header_bg1)

        setOnClicks(ivLeaveFeedback, etLeaveFeedback, tvSendReview)
    }

    private fun setOnClicks(vararg views: View) {
        for (view in views)
            view.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivLeaveFeedback -> {
                if (CommonUtils.isNetworkAvailable(this)) {
                    onBackPressed()
                } else run { showInternetAlertDialog(this) }
            }

            R.id.tvSendReview ->
                if (etLeaveFeedback.text.toString().equals("")) {
                    //toastMessage(getString(R.string.feedback_required))
                    CommonUtils.showCustomAlert(this,getString(R.string.feedback_required))
                } else {
                    callLeaveFeedbackApi(etLeaveFeedback.text.toString())
                }
        }
    }

    fun callLeaveFeedbackApi(feedback: String) {
        LeaveFeedbackPresenter(this, this).callLeaveFeedback(feedback)
    }

    override fun onHideBaseLoader() {
        hideLoader()
    }

    override fun onSuccessSendFeedback(feedbackResponse: FeedbackResponse?) {
        if (feedbackResponse!!.message.equals("fail")) {
            showOnBackPressed(this, feedbackResponse.message)
        } else {
            showOnBackPressed(this, feedbackResponse.message)
        }
    }

    override fun onTokenChangeError(errorMessage: String?) {
        showDialog(this)
    }

    override fun onShowBaseLoader() {
        showLoader()
    }

    override fun onError(errorMessage: String?) {
    }
}