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
import com.mindiii.lasross.module.subscription.presenter.SubscriptionPresenter
import com.mindiii.lasross.module.subscription.presenter.adapter.SubscriptionItemDescriptionAdapter
import com.mindiii.lasross.module.subscription.presenter.model.SubscribeResponse
import com.mindiii.lasross.utils.StripeResponse
import com.stripe.Stripe
import com.stripe.exception.StripeException
import com.stripe.model.Customer
import kotlinx.android.synthetic.main.subscription_screen.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

class SubscriptionActivity : LasrossParentKotlinActivity(), View.OnClickListener, ApiCallback.SubscriptionCallback {
    lateinit var response: SubscriptionResponse
    lateinit var mTempList: ArrayList<Data>
    lateinit var cardResponce: com.mindiii.lasross.utils.StripeResponse
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

        mTempList = ArrayList()
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
                if (tvMiddleText.text.toString().equals("Free", ignoreCase = true) || tvMiddleText.text.toString().equals("Libre", ignoreCase = true)) {
                    startActivity(Intent(this, HomeActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                    finish()
                }else {
                    plan_id = mTempList[0].subscriptionPlanId
                    if (cardResponce.sources.data.size > 0) {
                        subscribe(plan_id)
                    }else{
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
                Collections.swap(mTempList, 0, 1);
                Collections.swap(mTempList, 1, 2);
                updateUi(mTempList)
            }
            R.id.llLeftLayout -> {
                Collections.swap(mTempList, 0, 2);
                Collections.swap(mTempList, 1, 2);
                updateUi(mTempList)
                     }
        }
    }

    private fun updateUi(mTempList: ArrayList<Data>) {
        for (i  in 0 until mTempList.size){
            val planTitle = mTempList[i].plan_title
            val titleNames = planTitle.split(" ")
            if (i==0){
                tvMiddleText.text = titleNames[0]//silver
                tvMiddlePlan.text = titleNames[1].trim()//silver

                ivMiddleImage.setImageResource(mTempList[i].imageIdActive)

                tvplanPriceCurrency.text = mTempList[i].plan_currency
                tvplanPrice.text = mTempList[i].plan_price

                if (!titleNames[0].equals("free",true) || !titleNames[0].equals("libre",true))
                tvplanDuration.text = " / " + mTempList[i].plan_duration + " " + mTempList[i].plan_duration_type
                setAdapterData(mTempList[i].plan_description)
            }
            if (i==1){
                tvRightText.text = titleNames[0]//golden
                tvRightPlan.text = titleNames[1].trim()//golden
                ivRightImage.setImageResource(mTempList[i].imageIdDeActive)
            }
            if (i==2){
                tvLeftText.text = titleNames[0]//free
                tvLeftPlan.text = titleNames[1].trim()//free
                ivLeftImage.setImageResource(mTempList[i].imageIdDeActive)
            }
        }
    val title=tvMiddleText.text.toString()
        if (title.equals("free",true)||title.equals("libre",true)){
            tvSubscribe.text = getString(R.string.subscribe_free)
        }else{
            tvSubscribe.text = getString(R.string.subscribe)
        }
    }

    fun setAdapterData(i: String) {
        itemDescriptionList.clear()
        val description =i //response.data[i].plan_description
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

    override fun onRestart() {
        super.onRestart()
        getCreditCardInfo()
    }

    override fun onSuccessSubscription(subscriptionResponse: SubscriptionResponse) {
        Log.d("subscriptionResponse", subscriptionResponse.toString())
        response = subscriptionResponse
        mTempList.clear()
        mTempList.addAll(response.data)
        mTempList[0].imageIdActive=R.drawable.silverplan_icon_orange
        mTempList[0].imageIdDeActive=R.drawable.silverplan_icon_black

        mTempList[1].imageIdActive=R.drawable.goldenplan_icon_orange
        mTempList[1].imageIdDeActive=R.drawable.goldenplan_icon_black

        mTempList[2].imageIdActive=R.drawable.free_icon_orange
        mTempList[2].imageIdDeActive=R.drawable.free_icon_black

        tvplanPriceCurrency.text = response.data[0].plan_currency
        tvplanPrice.text = response.data[0].plan_price
        val planTitle1 = response.data[2].plan_title
        val planFree = planTitle1.split(" ")
        tvplanDuration.text = " / " + response.data[0].plan_duration + " " + response.data[0].plan_duration_type

        tvLeftText.text = planFree[0]//free
        tvLeftPlan.text = planFree[1]//free

        val planTitle2 = response.data[0].plan_title
        val silver = planTitle2.split(" ")

        tvMiddleText.text = silver[0]//silver
        tvMiddlePlan.text = silver[1]//silver

        val planTitle3 = response.data[1].plan_title
        val golden = planTitle3.split(" ")

        tvRightText.text = golden[0]//golden
        tvRightPlan.text = golden[1].trim()//golden


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

        object : AsyncTask<Void, Void, Customer>() {
            override fun onPreExecute() {
                super.onPreExecute()
                showLoader()
            }

            override fun doInBackground(vararg voids: Void): Customer? {
                Stripe.apiKey = resources.getString(R.string.StripeKeyTest)
                val stripeCustomerId = session!!.registration.stripe_customer_id
                val expandList: MutableList<String> = java.util.ArrayList()
                expandList.add("sources")
                val retrieveParams: MutableMap<String, Any> = HashMap()
                retrieveParams["expand"] = expandList
                var customer: Customer? = null
                try {
                    customer = Customer.retrieve(
                            stripeCustomerId,
                            retrieveParams,
                            null
                    )
                } catch (e: StripeException) {
                    e.printStackTrace()
                }

                val params: MutableMap<String, Any> = HashMap()
                params["object"] = "card"
                return customer
            }

            override fun onPostExecute(externalAccountCollection: Customer?) {
                super.onPostExecute(externalAccountCollection)

                runOnUiThread {
                    tvSubscribe.isEnabled = false
                    if (externalAccountCollection != null) {
                        cardResponce = StripeResponse()
                        cardResponce = Gson().fromJson(externalAccountCollection.toJson(), com.mindiii.lasross.utils.StripeResponse::class.java)
                        Log.e("Size: ", "" + cardResponce.sources.data.size)

                        for (i in 0 until cardResponce.sources.data.size) {
                            cardResponce.sources.data[i].isMoreDetail = true
                        }
                    }
                }
                hideLoader()
                tvSubscribe.isEnabled = true
            }
        }.execute()
    }
}
