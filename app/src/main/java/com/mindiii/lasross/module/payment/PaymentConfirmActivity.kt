package com.mindiii.lasross.module.payment
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.WindowManager
import com.mindiii.lasross.R
import com.mindiii.lasross.base.LasrossParentKotlinActivity
import com.mindiii.lasross.module.home.HomeActivity
import kotlinx.android.synthetic.main.activity_payment_confirm.*
class PaymentConfirmActivity : LasrossParentKotlinActivity(), View.OnClickListener {
    var totalAmount: String = ""
    var orderNumber: String = ""
    private var mLastClickTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_confirm)

        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = this.resources.getColor(R.color.home_header_bg1)
        }
        if (intent.getStringExtra("totalAmount") != null) {
            totalAmount = intent.getStringExtra("totalAmount")!!
            orderNumber = intent.getStringExtra("orderNumber")!!
        }

        updateUi(totalAmount, orderNumber)
    }

    private fun updateUi(totalAmount: String, orderNumber: String) {
        amt.text = totalAmount
        order_id.text = orderNumber
        rlPaymentContinue.setOnClickListener(this)

    }

    override fun onBackPressed() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }

    override fun onClick(v: View?) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return
        }
        mLastClickTime = SystemClock.elapsedRealtime()
        when (v?.id) {
            R.id.rlPaymentContinue -> {
                val intent = Intent(this, HomeActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                finish()
            }
        }

    }
}
