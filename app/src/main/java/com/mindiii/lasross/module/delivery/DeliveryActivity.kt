package com.mindiii.lasross.module.delivery

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.LasrossParentKotlinActivity
import com.mindiii.lasross.module.delivery.model.Data
import com.mindiii.lasross.module.delivery.model.DeliveryResponse
import com.mindiii.lasross.module.home.HomeActivity
import com.mindiii.lasross.module.payment.PaymentActivity
import com.mindiii.lasross.utils.CommonUtils
import kotlinx.android.synthetic.main.activity_delivery.*

class DeliveryActivity : LasrossParentKotlinActivity(), View.OnClickListener, ApiCallback.DeliveryCallback {

    private var mLastClickTime: Long = 0
    private var address: String = ""
    private var addressID: String = ""

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery)
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.home_header_bg1)

        if (intent.getStringExtra("address") != null) {
            val number = intent.getStringExtra("number")
            val companyName = intent.getStringExtra("companyName")

            address = intent.getStringExtra("address")
            addressID = intent.getStringExtra("addressID")
            /*latitude = intent.getStringExtra("latitude")
            longitude = intent.getStringExtra("longitude")
            warehouseLat = intent.getStringExtra("warehouse_lat")
            warehouseLng = intent.getStringExtra("warehouse_lng")
            warehouseTax = intent.getStringExtra("warehouse_tax")
            totalItem = intent.getStringExtra("totalItem")
            totalAmt = intent.getStringExtra("totalAmt")
            total = intent.getStringExtra("total")*/

            //init()

            if (address != null) {
                tvShippingAddress.text = address
                tvShippingNumber.text = number
                tvCompanyName.text = companyName
            }

        } else if (intent.getStringExtra("totalItem") != null) {
            /*totalItem = intent.getStringExtra("totalItem")
            totalAmt = intent.getStringExtra("totalAmt")
            total = intent.getStringExtra("total")
            warehouseLat = intent.getStringExtra("warehouse_lat")
            warehouseLng = intent.getStringExtra("warehouse_lng")
            warehouseTax = intent.getStringExtra("warehouse_tax")*/
        }

        if (!addressID.isEmpty()) {
            getDeliveryDetailsApi(addressID)
        }
        txtAddOrChangeAddress.setOnClickListener(this)
        ivBackDeliveryAddress.setOnClickListener(this)
        btnAddNewAddress.setOnClickListener(this)
        btnClearOrderDelivery.setOnClickListener(this)

        if (CommonUtils.isNetworkAvailable(this)!!) {
          //  scrollViewDelivery.visibility = View.VISIBLE
            llView.visibility = View.VISIBLE
            llDeliveryContinue.visibility = View.VISIBLE
        } else {
          //  scrollViewDelivery.visibility = View.GONE
            llView.visibility = View.GONE
            llDeliveryContinue.visibility = View.GONE
        }
    }


    // getDelivery address
    fun getDeliveryDetailsApi(addressID: String) {
        DeliveryPresenter(this, this).callDeliveryDetail(addressID)
    }


    @SuppressLint("SetTextI18n")
    private fun init() {
        val session = Session(this).registration
        /*tvShippingAddress.text = session.ship_address_location
        tvShippingNumber.text = session.bill_address_phone
        tvCompanyName.text = session.ship_address_company_name*/
    }

    override fun onClick(p0: View?) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return
        }
        mLastClickTime = SystemClock.elapsedRealtime()
        when (p0!!.id) {
            R.id.txtAddOrChangeAddress -> {
                onBackPressed()
                /*startActivity(Intent(this,MyAddressesActivity38::class.java)
                        .putExtra("CheckValue","CheckFound")
                        .putExtra("totalItem",totalItem)
                        .putExtra("totalAmt",totalAmt)
                        .putExtra("warehouse_lat",warehouseLat)
                        .putExtra("warehouse_lng",warehouseLng)
                        .putExtra("warehouse_tax",warehouseTax)
                        .putExtra("total",total))
                 finish()*/
            }
            R.id.ivBackDeliveryAddress -> {
                finish()
            }
            R.id.btnAddNewAddress -> {
                if (CommonUtils.isNetworkAvailable(this)!!) {
                    if (tvShippingAddress.text.toString() != "") {
                        startActivity(Intent(this, PaymentActivity::class.java)
                                .putExtra("totalAmt", tvTotalHere.text.toString())
                                .putExtra("addressID", addressID))
                    } else {
                        toastMessage(getString(R.string.address_select))
                    }
                } else {
                    showInternetAlertDialog(this)
                }

            }
            R.id.btnClearOrderDelivery -> {
                if (CommonUtils.isNetworkAvailable(this)!!) {
                    startActivity(Intent(this, HomeActivity::class.java)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                    finish()
                } else {
                    showInternetAlertDialog(this)
                }
            }
        }
    }

    override fun onHideBaseLoader() {
        hideLoader()
    }



    override fun onShowBaseLoader() {
        showLoader()
    }

    override fun onError(errorMessage: String?) {
        if (errorMessage != null) {
            toastMessage(errorMessage)
        }
    }

    override fun onSuccessDeliveryDetails(deliveryResponse: DeliveryResponse?) {
        updateUI(deliveryResponse!!.data)
    }

    override fun onTokenChangeError(errorMessage: String) {
        showDialog(this)
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(data: Data) {
        tvItems.text = data.itemCount.toString()

        if (data.subtotal_before_discount.equals("")) {
            tvTotalAmt.text = "€ " + getTwoValueAfterDecimal(data.subtotal)
        } else {
            tvTotalAmt.text = "€ " + getTwoValueAfterDecimal(data.subtotal_before_discount)
        }

        //val mainChapterNumber1 = data.total_shipping_price.split("\\.".toRegex(), 2).toTypedArray()[0]

        if (data.free_shipping.equals("Yes")) {
            tvShippingCharges.text = "FREE SHIPPING"
        } else {
            tvShippingCharges.text = "+ € " + getTwoValueAfterDecimal(data.total_shipping_price)
        }



        taxPercent.text = "(" + data.tax_percent + "%)"
        tvTaxCharges.text = "€ " + getTwoValueAfterDecimal(data.tax_amount)

        if (data.total_discount.equals("")) {
            rlDeliveryDiscount.visibility = View.GONE
            viewDeliveryDiscount.visibility = View.GONE
        } else {
            rlDeliveryDiscount.visibility = View.VISIBLE
            viewDeliveryDiscount.visibility = View.VISIBLE
            tvDeliveryDiscountPercent.text = "(" + data.discount_percent + "%)"
            tvDeliveryDiscountCharges.text = "€ " + getTwoValueAfterDecimal(data.total_discount)
        }
        /*tvDeliveryDiscountPercent.text= "("+data.discount_percent +"%)"
        tvDeliveryDiscountCharges.text="€ "+data.total_discount*/


        tvTotalHere.text = "€ " + getTwoValueAfterDecimal(data.grand_total)

    }
}
