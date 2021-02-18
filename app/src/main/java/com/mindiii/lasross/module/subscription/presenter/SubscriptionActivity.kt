package com.mindiii.lasross.module.subscription

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.LasrossParentKotlinActivity
import com.mindiii.lasross.module.home.HomeActivity
import com.mindiii.lasross.module.payment.AddCardActivity
import com.mindiii.lasross.module.payment.model.StripeSaveCardResponce
import com.mindiii.lasross.module.subscription.presenter.SubscriptionPresenter
import com.mindiii.lasross.module.subscription.presenter.adapter.SubscriptionItemDescriptionAdapter
import com.mindiii.lasross.module.subscription.presenter.model.SubscribeResponse
import com.stripe.Stripe
import com.stripe.exception.StripeException
import com.stripe.model.Customer
import com.stripe.model.ExternalAccountCollection
import kotlinx.android.synthetic.main.subscription_screen.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

class SubscriptionActivity : LasrossParentKotlinActivity(), View.OnClickListener, ApiCallback.SubscriptionCallback {
    lateinit var response: SubscriptionResponse
    lateinit var cardResponce: StripeSaveCardResponce
    private var session: Session? = null
    private var mLastClickTime: Long = 0
    private var plan_id: String = ""
    private var from: String = ""
    private lateinit var itemDescriptionList: ArrayList<String>
    private lateinit var subscriptionItemDescriptionAdapter: SubscriptionItemDescriptionAdapter

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.subscription_screen)
        session = Session(this)

        itemDescriptionList = ArrayList()
        if (intent.getStringExtra("from") != null) {
            from = intent.getStringExtra("from")!!
        }

        if (from.equals("Signup Screen")) {
            iv_subcribeBack.visibility = View.GONE
        } else {
            iv_subcribeBack.visibility = View.VISIBLE
        }


        subscriptionApiCAll()
        getCreditCardInfo()

        tvSubscribe.setOnClickListener(this)
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = this.resources.getColor(R.color.home_header_bg1)

        llLeftLayout.setOnClickListener(this)
        llRightLayout.setOnClickListener(this)
        iv_subcribeBack.setOnClickListener(this)
    }

    private fun subscriptionApiCAll() {
        SubscriptionPresenter(this, this).subscriptionDetail()
    }

    private fun subscribe(plan_id: String) {
        SubscriptionPresenter(this, this).subscribePlanApi(plan_id)
    }

    fun onRadioButtonClicked(v: View) {
        val rb1 = findViewById<RadioButton>(R.id.rbDefault)
        val rb2 = findViewById<RadioButton>(R.id.rbPoplarity)
        val rb3 = findViewById<RadioButton>(R.id.rbRating)
        val checked = (v as RadioButton).isChecked
        when (v.getId()) {
            R.id.rbDefault -> {
                if (checked)
                    rb1.setTypeface(null, Typeface.BOLD_ITALIC)
                rb2.setTypeface(null, Typeface.NORMAL)
                rb3.setTypeface(null, Typeface.NORMAL)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(view: View) {
        when (view.id) {
            R.id.tvSubscribe -> {
                if (tvMiddleText.text.toString().equals("Free", ignoreCase = true)||tvMiddleText.text.toString().equals("Libre",ignoreCase = true)) {
                    startActivity(Intent(this, HomeActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                    finish()
                } else if ((tvMiddleText.text.toString().equals("Silver", ignoreCase = true)||tvMiddleText.text.toString().equals("Plata", ignoreCase = true))) {
                    if (cardResponce.data.size > 0) {
                        plan_id = response.data.get(0).subscriptionPlanId
                        subscribe(plan_id)
                    } else {
                        plan_id = response.data.get(0).subscriptionPlanId
                        startActivity(Intent(this, AddCardActivity::class.java)
                                .putExtra("subscriptionScreen", "fromSubscription")
                                .putExtra("subscriptionPlanId", plan_id))
                    }
                } else if ((tvMiddleText.text.toString().equals("Golden", ignoreCase = true)||tvMiddleText.text.toString().equals("Dorada", ignoreCase = true))) {
                    if (cardResponce.data.size > 0) {
                        plan_id = response.data.get(1).subscriptionPlanId
                        subscribe(plan_id)
                    } else {
                        plan_id = response.data.get(1).subscriptionPlanId
                        startActivity(Intent(this, AddCardActivity::class.java)
                                .putExtra("subscriptionScreen", "fromSubscription")
                                .putExtra("subscriptionPlanId", plan_id))
                    }
                }
            }
            R.id.iv_subcribeBack -> {
                onBackPressed()
            }
            R.id.llRightLayout -> {
                if (tvRightText.text.toString().trim().equals("Golden", ignoreCase = true)||tvRightText.text.toString().trim().equals("Dorada", ignoreCase = true)) {
                    setGoldenPlanData()
                } else if (tvRightText.text.toString().trim().equals("Silver", ignoreCase = true)||tvRightText.text.toString().trim().equals("Plata", ignoreCase = true)) {
                    setSilverPlanData()
                } else if (tvRightText.text.toString().trim().equals("Free", ignoreCase = true)||tvRightText.text.toString().trim().equals("Libre", ignoreCase = true)) {
                    setFreePlanData()
                }
            }
            R.id.llLeftLayout -> {
                if (tvLeftText.text.toString().trim().equals("Golden", ignoreCase = true)||tvLeftText.text.toString().trim().equals("Dorada", ignoreCase = true)) {
                    setGoldenPlanData()
                } else if (tvLeftText.text.toString().trim().equals("Silver", ignoreCase = true)||tvLeftText.text.toString().trim().equals("Plata", ignoreCase = true)) {
                    setSilverPlanData()
                } else if (tvLeftText.text.toString().trim().equals("Free", ignoreCase = true)||tvLeftText.text.toString().trim().equals("Libre", ignoreCase = true)) {
                    setFreePlanData()
                }
            }
        }
    }

    fun setAdapterData(i: Int) {
        itemDescriptionList.clear()
        val description = response.data[i].plan_description
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

    fun setGoldenPlanData() {
        ivMiddleImage.setImageResource(R.drawable.goldenplan_icon_orange)
        tvMiddleText.setText(R.string.golden_plan)
        tvMiddlePlan.visibility = View.VISIBLE

        ivLeftImage.setImageResource(R.drawable.silverplan_icon_black)
        tvLeftText.text = getString(R.string.silver)
        tvLeftPlan.visibility = View.VISIBLE

        ivRightImage.setImageResource(R.drawable.free_icon_black)
        tvRightText.setText(R.string.free)
        tvRightPlan.visibility = View.VISIBLE

        tvplanPriceCurrency.text = response.data[1].plan_currency
        tvplanPrice.text = " " + getTwoValueAfterDecimal(response.data[1].plan_price)
        tvplanDuration.text = " / " + response.data[1].plan_duration + " " + response.data[1].plan_duration_type
        tvplanDuration.visibility = View.VISIBLE
        tvplanPriceCurrency.visibility = View.VISIBLE
        setAdapterData(1)

    }

    fun setSilverPlanData() {
        ivMiddleImage.setImageResource(R.drawable.silverplan_icon_orange)
        tvMiddleText.setText(R.string.silver_plan)
        tvMiddlePlan.visibility = View.VISIBLE

        ivLeftImage.setImageResource(R.drawable.free_icon_black)
        tvLeftText.setText(R.string.free)
        tvLeftPlan.visibility = View.VISIBLE

        ivRightImage.setImageResource(R.drawable.goldenplan_icon_black)
        tvRightText.setText(R.string.golden_plan)
        tvRightPlan.visibility = View.VISIBLE

        tvplanPriceCurrency.text = response.data[0].plan_currency
        tvplanPrice.text = " " + getTwoValueAfterDecimal(response.data[0].plan_price)
        //tvplanPrice.text = response.data[0].plan_currency + " " + response.data[0].plan_price
        tvplanDuration.text = " / " + response.data[0].plan_duration + " " + response.data[0].plan_duration_type
        tvplanDuration.visibility = View.VISIBLE
        tvplanPriceCurrency.visibility = View.VISIBLE

        setAdapterData(0)
    }

    fun setFreePlanData() {
        ivMiddleImage.setImageResource(R.drawable.free_icon_orange)
        tvMiddleText.setText(R.string.free)
        tvMiddlePlan.visibility = View.VISIBLE

        ivLeftImage.setImageResource(R.drawable.goldenplan_icon_black)
        tvLeftText.setText(R.string.golden_plan)
        tvLeftPlan.visibility = View.VISIBLE

        ivRightImage.setImageResource(R.drawable.silverplan_icon_black)
        tvRightText.setText(R.string.silver_plan)
        tvplanPrice.setText(getString(R.string.free))
        tvplanDuration.visibility = View.GONE
        tvplanPriceCurrency.visibility = View.GONE

        setAdapterData(2)

    }

    override fun onRestart() {
        super.onRestart()
        getCreditCardInfo()
    }

    override fun onSuccessSubscription(subscriptionResponse: SubscriptionResponse) {
        Log.d("subscriptionResponse", subscriptionResponse.toString())
        response = subscriptionResponse
        tvplanPriceCurrency.text = response.data[0].plan_currency
        tvplanPrice.text = " " + getTwoValueAfterDecimal(response.data[0].plan_price)
        //tvplanPrice.text = response.data[0].plan_currency + " " + response.data[0].plan_price
        tvplanDuration.text = " / " + response.data[0].plan_duration + " " + response.data[0].plan_duration_type

        val description = response.data[0].plan_description
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

    override fun onTokenChangeError(errorMessage: String) {
        showDialog(this)
    }

    override fun onShowBaseLoader() {
        showLoader()
    }

    override fun onHideBaseLoader() {
        hideLoader()
    }

    override fun onError(errorMessage: String) {
        toastMessage(errorMessage)
    }

    override fun onSuccessSubscribe(response: SubscribeResponse?) {
        if (response!!.status.equals("fail")) {
            toastMessage(response.message)
        } else {
           showSubscribeDialog1(this, getString(R.string.plan_subscribe_success))
        }
    }

    fun showSubscribeDialog1(mContext: Context, message: String) {
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
            // startActivity(Intent(mContext, HomeActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
            this.finish()
        }
        dialog.show()
        val window = dialog.window!!
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
    }

    ///""""""""" Saved credit card api """"""""//
    @SuppressLint("StaticFieldLeak")
    protected fun getCreditCardInfo() {
        tvSubscribe.isEnabled = false

        cardResponce = StripeSaveCardResponce()
        object : AsyncTask<Void, Void, ExternalAccountCollection>() {
            override fun onPreExecute() {
                super.onPreExecute()
                showLoader()
            }

            override fun doInBackground(vararg voids: Void): ExternalAccountCollection? {
                Stripe.apiKey = resources.getString(R.string.StripeKeyTest)
                var customer: ExternalAccountCollection? = null
                try {
                    val stripeCustomerId = session!!.getRegistration()!!.getStripe_customer_id()//"cus_Fl6RC2yZA8vTna";//session.getRegistration().getStripe_customer_id();
                    val cardParams = HashMap<String, Any>()
                    cardParams["object"] = "card"
                    customer = Customer.retrieve(stripeCustomerId).sources.all(cardParams)
                } catch (ignored: StripeException) {
                }
                return customer
            }

            override fun onPostExecute(externalAccountCollection: ExternalAccountCollection?) {
                super.onPostExecute(externalAccountCollection)

                runOnUiThread {
                    tvSubscribe.isEnabled = false
                    if (externalAccountCollection != null) {
                        cardResponce = Gson().fromJson(externalAccountCollection.toJson(), StripeSaveCardResponce::class.java)
                        Log.e("Size: ", "" + cardResponce.data.size)


                        for (i in 0 until cardResponce.data.size) {
                            cardResponce.data[i].isMoreDetail = true
                        }
                    }
                }
                hideLoader()
                tvSubscribe.isEnabled = true
            }
        }.execute()
    }
}
