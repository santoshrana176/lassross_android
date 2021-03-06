package com.mindiii.lasross.module.settings

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.LasrossParentKotlinActivity
import com.mindiii.lasross.module.address.model.AddAddressResponse
import com.mindiii.lasross.module.home.model.LogoutResponse
import com.mindiii.lasross.module.settings.model.TermsPolicyResponse
import com.mindiii.lasross.module.settings.presenter.SettingsPresenter
import com.mindiii.lasross.module.subscription.presenter.model.SubscribeResponse
import kotlinx.android.synthetic.main.term_policy_activity.*

class TermsPolicyActivity : LasrossParentKotlinActivity(), ApiCallback.SettingsCallback {


    var titleBar: String = ""

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mindiii.lasross.R.layout.term_policy_activity)

        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = this.resources.getColor(com.mindiii.lasross.R.color.home_header_bg1)

        callTermsPolicyApi()

        ivTermPolicyBack.setOnClickListener({
            onBackPressed()
        })

        titleBar = intent.getStringExtra("title")
        tvTermsPolicyTitle.text = titleBar
    }

    fun callTermsPolicyApi() {
        SettingsPresenter(this, this).callTermsPolicyApi()
    }

    override fun onSuccessTermsPolicy(termsPolicyResponse: TermsPolicyResponse?) {
        if (titleBar.equals("Terms and Conditions")) {
            webViewLayout.getSettings().setLoadsImagesAutomatically(true)
            webViewLayout.getSettings().setJavaScriptEnabled(true);
            webViewLayout.setWebViewClient(MyBrowser())
            webViewLayout.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webViewLayout.loadUrl(termsPolicyResponse!!.content_url.term_and_condition)
        } else {
            webViewLayout.getSettings().setLoadsImagesAutomatically(true)
            webViewLayout.getSettings().setJavaScriptEnabled(true);
            webViewLayout.setWebViewClient(MyBrowser())
            webViewLayout.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webViewLayout.loadUrl(termsPolicyResponse!!.content_url.policy)
        }
    }

    private inner class MyBrowser : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }

    override fun onShowBaseLoader() {
        showLoader()
    }

    override fun onHideBaseLoader() {
        hideLoader()
    }

    override fun onError(errorMessage: String?) {
    }

    override fun onSuccessChangePassword(response: AddAddressResponse?) {
    }

    override fun onTokenChangeError(errorMessage: String?) {
        showDialog(this)
    }

    override fun onSuccessLogout(logoutResponse: LogoutResponse?) {
    }

    override fun onSuccessCurrentSubscribedPlan(response: SubscribeResponse?) {
    }
}
