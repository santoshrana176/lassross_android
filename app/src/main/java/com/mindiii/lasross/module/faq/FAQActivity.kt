package com.mindiii.lasross.module.faq

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.LasrossParentKotlinActivity
import com.mindiii.lasross.module.cart.MyCartActivity
import com.mindiii.lasross.module.contactus.fragment.ContactUsFragment
import com.mindiii.lasross.module.customerhelp.fragment.CustomerHelpFragment
import com.mindiii.lasross.module.faq.fragment.FAQFragment
import com.mindiii.lasross.module.myorder.fragment.MyOrdersFragment
import com.mindiii.lasross.module.orderdetail.fragment.OrderDetailsFragment
import kotlinx.android.synthetic.main.faq_activty_28.*


class FAQActivity : LasrossParentKotlinActivity()
{

    var orderId: String = ""
    var boolForBackPress = false
    var boolForBackPress1 = false
    private lateinit var session: Session

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.faq_activty_28)

        session = Session(this)

        //tvCartItemCountOrders.text = Session(this).cartItemCount
        if (session.cartItemCount.equals("0", ignoreCase = true))
            tvCartItemCountOrders.setVisibility(View.GONE)
        else {
            tvCartItemCountOrders.setVisibility(View.VISIBLE)
            tvCartItemCountOrders.setText(session.cartItemCount)
        }

        ivBackButton.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
        if (intent.getStringExtra("orderId") != null) {
            orderId = intent.getStringExtra("orderId")
        }//status

        ivMyCartOrder.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, MyCartActivity::class.java))
        })


        if (intent.getStringExtra("screenCheck") != null) {
            when (intent.getStringExtra("screenCheck")) {
                "faqs" -> {
                    addFragment(FAQFragment.newInstance(), false, R.id.profileFrame)
                    tvFAQUpperText.text = getString(R.string.faq)
                }
                "contactus" -> {
                    addFragment(ContactUsFragment.newInstance(), false, R.id.profileFrame)
                    tvFAQUpperText.text = getString(R.string.contact_us)
                }
                "myOrder" -> {
                    addFragment(MyOrdersFragment.newInstance("myOrder"), false, R.id.profileFrame)
                    tvFAQUpperText.text = getString(R.string.my_orders)
                }

                "Order Status" -> {
                    addFragment(OrderDetailsFragment.newInstance(orderId), false, R.id.profileFrame)
                    tvFAQUpperText.text = getString(R.string.order_detail)
                }
                "customerhelp" -> {
                    addFragment(CustomerHelpFragment(), false, R.id.profileFrame)
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val f = this.supportFragmentManager.findFragmentById(R.id.profileFrame)

        if (f is MyOrdersFragment) {
            if (boolForBackPress) {
                if (f.myOrdersModelsList.size > 0) {
                    f.myOrdersModelsList.clear()
                    f.myOrdersAdapter.notifyDataSetChanged()
                    f.callMyOrderListApi(this)
                }
                boolForBackPress = false
                tvFAQUpperText.text = getString(R.string.my_orders)
                ivMyCartOrder.visibility = View.VISIBLE
                if (session.cartItemCount.equals("0", ignoreCase = true))
                    tvCartItemCountOrders.setVisibility(View.GONE)
                else {
                    tvCartItemCountOrders.setVisibility(View.VISIBLE)
                    tvCartItemCountOrders.setText(session.cartItemCount)
                }
                // tvCartItemCountOrders.visibility = View.VISIBLE
            }
        } else if (f is OrderDetailsFragment) {
            if (boolForBackPress1) {
                if (f.orderDetailList.size > 0) {
                    f.trackingStatusList.clear()
                    f.orderDetailList.clear()
                    f.orderDetailAdapter.notifyDataSetChanged()
                    f.callOrderDetailApi() 
                }
                boolForBackPress1 = false
                tvFAQUpperText.text = getString(R.string.order_detail)
                ivMyCartOrder.visibility = View.GONE
                tvCartItemCountOrders.visibility = View.GONE
            }
        }
    }

    fun setOrderDetail() {
        boolForBackPress = true
    }

    fun setTrackingDetail() {
        boolForBackPress1 = true
    }

    override fun onRestart() {
        super.onRestart()
        if (session.cartItemCount.equals("0", ignoreCase = true))
            tvCartItemCountOrders.setVisibility(View.GONE)
        else {
            tvCartItemCountOrders.setVisibility(View.VISIBLE)
            tvCartItemCountOrders.setText(session.cartItemCount)
        }
    }
}