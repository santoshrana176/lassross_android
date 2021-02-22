package com.mindiii.lasross.module.settings

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.mindiii.lasross.R
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.LasrossParentKotlinActivity
import com.mindiii.lasross.module.address.model.AddAddressResponse
import com.mindiii.lasross.module.home.model.LogoutResponse
import com.mindiii.lasross.module.settings.model.NotificationAlertResponse
import com.mindiii.lasross.module.settings.model.TermsPolicyResponse
import com.mindiii.lasross.module.settings.presenter.SettingsPresenter
import com.mindiii.lasross.module.subscription.presenter.model.SubscribeResponse
import kotlinx.android.synthetic.main.term_policy_activity.*

class TermsPolicyActivity : LasrossParentKotlinActivity(), ApiCallback.SettingsCallback {
    var titleBar: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.term_policy_activity)

        ivTermPolicyBack.setOnClickListener({
            onBackPressed()
        })
        if (intent.getStringExtra("title") != null)
            titleBar = intent.getStringExtra("title")!!

        if (titleBar.equals("Terms and Conditions"))
            tvTermsPolicyTitle.text = getString(R.string.terms_and_conditions)
        else
            tvTermsPolicyTitle.text = getString(R.string.privacy_policy)
        callTermsPolicyApi()
    }

    private fun initilization(url: String) {


        Log.e("termsCondition", url)
        webViewLayout.settings.loadsImagesAutomatically = true
        webViewLayout.settings.javaScriptEnabled = true
        webViewLayout.settings.domStorageEnabled = true
        webViewLayout.webViewClient = MyBrowser()
        //webViewLayout.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

        webViewLayout.setVisibility(View.VISIBLE)
        webViewLayout.settings.setBuiltInZoomControls(true)
        webViewLayout.settings.setSupportZoom(true)
        webViewLayout.settings.setDefaultZoom(WebSettings.ZoomDensity.FAR)
        webViewLayout.settings.setJavaScriptCanOpenWindowsAutomatically(true)
        webViewLayout.settings.setAllowFileAccess(true)
        webViewLayout.settings.setLoadWithOverviewMode(true)
        webViewLayout.settings.setUseWideViewPort(true)
        webViewLayout.settings.setPluginState(WebSettings.PluginState.ON)
        webViewLayout.settings.setAllowContentAccess(true)

        webViewLayout.loadUrl(url)

    }


    override fun onShowBaseLoader() {
        showLoader()
    }

    override fun onHideBaseLoader() {
        hideLoader()
    }

    override fun onError(errorMessage: String?) {
        TODO("Not yet implemented")
    }

    override fun onSuccessChangePassword(response: AddAddressResponse?) {
        TODO("Not yet implemented")
    }

    override fun onTokenChangeError(errorMessage: String?) {
        showDialog(this)
    }

    override fun onSuccessLogout(logoutResponse: LogoutResponse?) {
        TODO("Not yet implemented")
    }

    override fun onSuccesNotifcationOnOff(logoutResponse: NotificationAlertResponse?) {
        TODO("Not yet implemented")
    }

    override fun onSuccessCurrentSubscribedPlan(response: SubscribeResponse?) {
        TODO("Not yet implemented")
    }

    override fun onSuccessTermsPolicy(termsPolicyResponse: TermsPolicyResponse?) {
        if (titleBar.equals(getString(R.string.Terms_condition))) {
            // webViewLayout.loadUrl(termsPolicyResponse!!.content_url.term_and_condition)
            initilization(termsPolicyResponse!!.content_url.term_and_condition)
        } else {
            initilization(termsPolicyResponse!!.content_url.policy)
            // webViewLayout.loadUrl(termsPolicyResponse!!.content_url.policy)
        }
    }

    fun callTermsPolicyApi() {
        SettingsPresenter(this, this).callTermsPolicyApi()
    }


    private inner class MyBrowser : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            hideLoader()
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            showLoader()
        }

        override fun onPageCommitVisible(view: WebView?, url: String?) {
            super.onPageCommitVisible(view, url)
        }
    }
}
/*
class TermsPolicyActivity : LasrossParentKotlinActivity(), ApiCallback.SettingsCallback {

lateinit var webView:WebView
    var titleBar: String = ""

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mindiii.lasross.R.layout.term_policy_activity)

        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = this.resources.getColor(com.mindiii.lasross.R.color.home_header_bg1)

        webViewLayout.getSettings().setLoadsImagesAutomatically(true)
        webViewLayout.getSettings().setJavaScriptEnabled(true);
        webViewLayout.setWebViewClient(MyBrowser())
        webViewLayout.getSettings().setDefaultTextEncodingName("utf-8")

        callTermsPolicyApi()

        ivTermPolicyBack.setOnClickListener {
            onBackPressed()
        }

        titleBar = intent.getStringExtra("title")
        if (titleBar.equals("Terms and Conditions"))
            tvTermsPolicyTitle.text = getString(R.string.terms_and_conditions)
        else
            tvTermsPolicyTitle.text = getString(R.string.privacy_policy)
    }

    fun callTermsPolicyApi() {
        SettingsPresenter(this, this).callTermsPolicyApi()
    }

    override fun onSuccessTermsPolicy(termsPolicyResponse: TermsPolicyResponse?) {
        if (titleBar.equals(getString(R.string.Terms_condition))) {
              webViewLayout.loadUrl(termsPolicyResponse!!.content_url.term_and_condition)
          } else {
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

    override fun onSuccesNotifcationOnOff(logoutResponse: NotificationAlertResponse?) {
        TODO("Not yet implemented")
    }

    override fun onSuccessCurrentSubscribedPlan(response: SubscribeResponse?) {
    }
}
*/
