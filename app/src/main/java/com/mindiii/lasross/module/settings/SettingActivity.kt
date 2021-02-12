package com.mindiii.lasross.module.settings

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.LasrossParentKotlinActivity
import com.mindiii.lasross.helper.LocaleHelper
import com.mindiii.lasross.module.activeplan.ActivePlanActivity
import com.mindiii.lasross.module.address.model.AddAddressResponse
import com.mindiii.lasross.module.home.model.LogoutResponse
import com.mindiii.lasross.module.profile.ProfileActivity
import com.mindiii.lasross.module.settings.model.LanguageModel
import com.mindiii.lasross.module.settings.model.NotificationAlertResponse
import com.mindiii.lasross.module.settings.model.TermsPolicyResponse
import com.mindiii.lasross.module.settings.presenter.LanguagePresenter
import com.mindiii.lasross.module.settings.presenter.SettingsPresenter
import com.mindiii.lasross.module.subscription.SubscriptionActivity
import com.mindiii.lasross.module.subscription.presenter.model.SubscribeResponse
import com.mindiii.lasross.utils.CommonUtils
import com.mindiii.lasross.utils.LanguageUtils
import kotlinx.android.synthetic.main.custom_dialog_settings.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.logout_view.*
import kotlinx.android.synthetic.main.reset_password_dialog_artboard_35.*
import kotlinx.android.synthetic.main.setting_activty_40.*
import java.util.*


class SettingActivity : LasrossParentKotlinActivity(), View.OnClickListener, ApiCallback.SettingsCallback, ApiCallback.LanguageCallback {

    private var session: Session? = null
    lateinit var changePasswordDialog: Dialog
    var selectedLanguage = ""
    var  status =""
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        session = Session(this)
       // setAppLocale(session!!.getIsEnglishLanguage())
        setContentView(R.layout.setting_activty_40)
    val status1=session!!.notificatioStatus
        if (status1.isEmpty()){
            status="0"
            Glide.with(this).load(R.drawable.ico_toggle_off).into(notificationSwitch);
        }else if (status1.equals("1")){
            status="1"
            Glide.with(this).load(R.drawable.ico_toggle_on).into(notificationSwitch);
        }else{
            status="0"
            Glide.with(this).load(R.drawable.ico_toggle_off).into(notificationSwitch);
        }
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = this.resources.getColor(R.color.home_header_bg1)
        setOnClicks(rlEditProfile, rlChangePassword, subscriptionPlans, iv_back, tvSettingLogout,
                rlTermsCondition, rlPrivacyPolicy, rlFeedback, rlEnglish, rlSpanish,notificationSwitch)
    }

    fun setAppLocale(locale: String) {
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.setLocale(Locale(locale.toLowerCase()))
        res.updateConfiguration(conf, dm)
    }

    private fun setOnClicks(vararg views: View) {
        for (view in views)
            view.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rlEditProfile -> {
                if (CommonUtils.isNetworkAvailable(this)) {
                    startActivity(Intent(this, ProfileActivity::class.java).putExtra("from", "Settings"))
                } else run { showInternetAlertDialog(this) }
            }
            R.id.rlChangePassword -> {
                if (CommonUtils.isNetworkAvailable(this)) {
                    openChangePasswordDialog()
                } else run { showInternetAlertDialog(this) }
            }
            R.id.subscriptionPlans -> {
                if (CommonUtils.isNetworkAvailable(this)) {
                    callUserCurrentSubscribedPlan()
                } else run { showInternetAlertDialog(this) }
            }
            R.id.iv_back -> {
                onBackPressed()
            }
            R.id.notificationSwitch -> {
                 if ( status.equals("1")){
                    notificatioOnOffApi("0")
                }else{
                    notificatioOnOffApi("1")
                }
            }
            R.id.tvSettingLogout -> {
                if (CommonUtils.isNetworkAvailable(this)) {
                    showLogoutDialog(this, resources.getString(R.string.logout_msg))
                } else run { showInternetAlertDialog(this) }

            }
            R.id.rlTermsCondition -> {
                if (CommonUtils.isNetworkAvailable(this)) {
                    startActivity(Intent(this, TermsPolicyActivity::class.java)
                            .putExtra("title", "Terms and Conditions"))
                } else run { showInternetAlertDialog(this) }

            }
            R.id.rlFeedback -> {
                if (CommonUtils.isNetworkAvailable(this)) {
                    startActivity(Intent(this, LeaveFeedbackActivity::class.java))
                } else run { showInternetAlertDialog(this) }

            }
            R.id.rlPrivacyPolicy -> {
                if (CommonUtils.isNetworkAvailable(this)) {
                    startActivity(Intent(this, TermsPolicyActivity::class.java)
                            .putExtra("title", "Privacy Policy"))
                } else run { showInternetAlertDialog(this) }
            }
            R.id.rlEnglish -> {
                //session!!.setIsEnglishLanguage("en")
                callLanguageApi("en")
                //finish()
                selectedLanguage = "en"
            }
            R.id.rlSpanish -> {
                // session!!.setIsEnglishLanguage("es")
                callLanguageApi("es")
                //finish()
                selectedLanguage = "es"

            }
        }
    }
    private fun updateViews(languageCode: String) {
        val context = LocaleHelper.setLocale(this, languageCode)
        val resources: Resources = context.resources
    }
    fun callLanguageApi(countryLanguageCode: String) {
        LanguagePresenter(this, this).callLanguageApi(countryLanguageCode)
    }

    fun callUserCurrentSubscribedPlan() {
        SettingsPresenter(this, this).callUserCurrentSubscribedPlanApi()
    }

    fun showLogoutDialog(activity: Activity, message: String) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        Objects.requireNonNull(dialog.window)!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        dialog.setContentView(R.layout.logout_view)
        dialog.tvMessagesLogout.setText(message)
        dialog.tvPopupNoLogout.setOnClickListener({ dialog.dismiss() })
        dialog.tvPopupYesLogout.setOnClickListener({
            callLogoutApi()
            dialog.dismiss()
        })

        dialog.show()
    }

    private fun callLogoutApi() {
        SettingsPresenter(this, this).callLogoutApi()
    }
    private fun notificatioOnOffApi(status:String) {
        SettingsPresenter(this, this).callNotificationOnOff(status)
    }

    private fun openChangePasswordDialog() {
        changePasswordDialog = Dialog(this)//,android.R.style.Theme_Dialog);
        changePasswordDialog.setContentView(R.layout.reset_password_dialog_artboard_35)
        changePasswordDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        changePasswordDialog.ivClose.setOnClickListener { changePasswordDialog.dismiss() }

        changePasswordDialog.tvDone.setOnClickListener {
            val oldPass = changePasswordDialog.etOldPass.text.toString().trim()
            val newPass = changePasswordDialog.etNewPass.text.toString().trim()
            val confPass = changePasswordDialog.etConfirmPass.text.toString().trim()
            if (validations(oldPass, newPass, confPass)) {
                changePasswordApi(oldPass, newPass, confPass)
            }
        }
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(changePasswordDialog.window?.attributes)
        changePasswordDialog.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        lp.gravity = Gravity.TOP
        lp.windowAnimations = R.style.DialogAnimation
        changePasswordDialog.window!!.attributes = lp
        changePasswordDialog.setCanceledOnTouchOutside(false)
        changePasswordDialog.show()
    }

    fun validations(oldPass: String, newPass: String, confPass: String): Boolean {

        if (oldPass.isEmpty()) {
            CommonUtils.showCustomAlert(this, getString(R.string.Please_enter_old_password))
            return false;
        }
        if (!isValidPass(oldPass)) {
            CommonUtils.showCustomAlert(this, getString(R.string.max_char))
            return false;
        } else if (newPass.isEmpty()) {
            CommonUtils.showCustomAlert(this, getString(R.string.plsase_enter_new_passwprd))
            return false
        } else if (!isValidPass(newPass)) {
            CommonUtils.showCustomAlert(this, getString(R.string.max_six_char))
            //etPass.setError("Password should have minimum 6 characters");
            return false
        } else if (confPass.isEmpty()) {
            CommonUtils.showCustomAlert(this, getString(R.string.confirm_password))
            //etPass.setError("Password should have minimum 6 characters");
            return false
        } else if (!confPass.equals(newPass)) {
            CommonUtils.showCustomAlert(this, getString(R.string.comfirm_password_doesnot_match))
            //etPass.setError("Password should have minimum 6 characters");
            return false
        }
        return true
    }

    private fun isValidPass(pass: String): Boolean {
        return pass.length > 5
    }

    private fun changePasswordApi(oldPass: String, newPass: String, confPass: String) {
        SettingsPresenter(this, this).changePasseword(oldPass, newPass, confPass)
    }

    override fun onShowBaseLoader() {
        showLoader()
    }

    override fun onHideBaseLoader() {
        hideLoader()
    }

    override fun onTokenChangeError(errorMessage: String?) {
        showDialog(this)
    }

    override fun onError(errorMessage: String?) {
        //toastMessage(errorMessage.toString())
        if (this::changePasswordDialog.isInitialized) {
            changePasswordDialog.dismiss()
        }
    }

    override fun onSuccessChangePassword(response: AddAddressResponse?) {
        /*  if (this::changePasswordDialog.isInitialized) {
              changePasswordDialog.dismiss()
          }*/
        showCustomAlert(response?.message.toString(), this)
    }

    fun showCustomAlert(message: String, context: Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.getWindow()?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        dialog.setContentView(R.layout.custom_dialog_settings)
        dialog.setCancelable(false)
        val tvMessages = dialog.tvMessages_1
        tvMessages.setText(message)
        val tvPopupOk = dialog.tvOk
        tvPopupOk.setOnClickListener {
            dialog.dismiss()
            if (this::changePasswordDialog.isInitialized) {
                changePasswordDialog.dismiss()
            }
        }
        dialog.show()
    }

    override fun onSuccessCurrentSubscribedPlan(response: SubscribeResponse?) {
        if (response != null) {
            if (response.message != null) {
                startActivity(Intent(this, SubscriptionActivity::class.java))
            } else {
                val b = Bundle()
                val intent = Intent(this, ActivePlanActivity::class.java)
                b.putSerializable("subscribeResponse", response)
                intent.putExtras(b)
                intent.putExtra("isCancelled", response.data.is_cancelled)
                startActivity(intent)
            }
        }
    }

    override fun onSuccessLanguageResponse(languageModel: LanguageModel?) {
        if (languageModel!!.status.equals("success")) {
            //CommonUtils.showCustomAlert(this, languageModel.message)
            //    toastMessage(languageModel.message)
            //   startActivity(Intent(this, SettingActivity::class.java))
            //  finish()
            updateViews(selectedLanguage)
            if (selectedLanguage.equals("en")) {
                session?.language = "en"
                LanguageUtils.language(this, selectedLanguage,true)
            } else if (selectedLanguage.equals("es")) {
                session?.language = "es"
                LanguageUtils.language(this, selectedLanguage,true)
            }

        } else {
            toastMessage(getString(R.string.something_worng))
        }
    }

    override fun onSuccessLogout(logoutResponse: LogoutResponse?) {
        session!!.logout()
    }

    override fun onSuccesNotifcationOnOff(logoutResponse: NotificationAlertResponse?) {
         // on notification response

        if ( status.equals("1")){
            session!!.notificatioStatus="1"
            Glide.with(this).load(R.drawable.ico_toggle_on).into(notificationSwitch);
        }else{
            session!!.notificatioStatus="1"
            Glide.with(this).load(R.drawable.ico_toggle_off).into(notificationSwitch);
        }
    }

    override fun onSuccessTermsPolicy(termsPolicyResponse: TermsPolicyResponse?) {
    }



}

