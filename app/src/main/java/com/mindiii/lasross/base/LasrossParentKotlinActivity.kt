package com.mindiii.lasross.base

import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.module.home.HomeActivity
import com.mindiii.lasross.utils.ProgressDialog
import com.mindiii.lasross.utils.Utility
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

open class LasrossParentKotlinActivity : AppCompatActivity() {
    val TAG: String = LasrossParentKotlinActivity::class.java.name
    lateinit var fragment: Fragment
    private var progressDialog: ProgressDialog? = null
    public var fireBaseToken = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lasross_parent_kotlin)
        progressDialog = ProgressDialog(this@LasrossParentKotlinActivity)
        getCurrentFirebaseToken()
    }
    fun transparentStatusBar(activity: AppCompatActivity) {
        if (Build.VERSION.SDK_INT in 19..20) {
            setWindowTransparentFlag(
                    activity,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    true
            )
        }
        if (Build.VERSION.SDK_INT >= 19) {
            activity.window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowTransparentFlag(
                    activity,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    false
            )
            activity.window.statusBarColor = Color.TRANSPARENT
        }
    }

    private fun setWindowTransparentFlag(
            activity: AppCompatActivity,
            bits: Int,
            on: Boolean
    ) {
        val win = activity.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }
    fun DateFormatChange(input: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        val outputFormat = SimpleDateFormat("dd MMM yyyy ");
        System.out.println(outputFormat.format(inputFormat.parse(input)))
        return outputFormat.format(inputFormat.parse(input))
    }

    fun getCurrentFirebaseToken(): String {
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("firebaseToken", "getInstanceId failed", task.exception)
                return@OnCompleteListener
            }

            fireBaseToken = Objects.requireNonNull<InstanceIdResult>(task.result).token
            Log.d("firebaseToken", fireBaseToken)
        })
        return fireBaseToken
    }

    // Method to replace fragment
    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean, containerId: Int) {
        val backStackName = fragment.javaClass.name
        val fm = supportFragmentManager
        var i = fm.backStackEntryCount
        while (i > 0) {
            fm.popBackStackImmediate()
            i--
        }
        val fragmentPopped = supportFragmentManager.popBackStackImmediate(backStackName, 0)
        if (!fragmentPopped) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.fade_out)
            transaction.replace(containerId, fragment, backStackName).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            if (addToBackStack)
                transaction.addToBackStack(backStackName)
            transaction.commit()
        }
    }

    // Method to add fragment
    fun addFragment(fragment: Fragment, addToBackStack: Boolean, containerId: Int) {
        val backStackName = fragment.javaClass.name

        val fragmentManager = supportFragmentManager
       // val fragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0)
      //  if (!fragmentPopped) {
            val transaction = fragmentManager.beginTransaction()
            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.fade_out)
            transaction.add(containerId, fragment, backStackName)
            if (addToBackStack)
                transaction.addToBackStack(backStackName)
            transaction.commit()
      //  }
    }

    internal fun replaceFragmentLoss(fragmentHolder: Fragment, layoutId: Int) {
        try {
            val fragmentManager = supportFragmentManager
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            val fragmentName = fragmentHolder.javaClass.name
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.fade_out)
            fragmentTransaction.add(layoutId, fragment, fragmentName).addToBackStack(fragmentName)
            fragmentTransaction.commitAllowingStateLoss() // important
            fragment = fragmentHolder
            hideKeyBoard()
        } catch (e: Exception) {
            Utility.e("value", e.toString())
        }

    }

    fun hideKeyBoard() {
        try {
            val inputManager = getSystemService(
                    Context.INPUT_METHOD_SERVICE) as InputMethodManager

            inputManager.hideSoftInputFromWindow(currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS)
        } catch (e: Exception) {

        }
    }

    fun hideLoader() {
        progressDialog?.dismiss()
    }

    fun showLoader() {
        if (!isFinishing) {
            progressDialog?.show()
        }
    }

    fun showDialog(mContext: Context) {
        val session = Session(mContext)
        val dialog = Dialog(mContext)
        Objects.requireNonNull<Window>(dialog.window).setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.session_expired_dialog)
        val tvOK: TextView = dialog.findViewById(R.id.tvOK)
        tvOK.setOnClickListener {
            dialog.dismiss()
            session.logout()
        }
        dialog.show()
        val window = dialog.window!!
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
    }

    fun showInternetAlertDialog(mContext: Context) {
        val dialog = Dialog(mContext)
        Objects.requireNonNull<Window>(dialog.window).setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.session_expired_dialog)

        val tvTitleOfVal: TextView = dialog.findViewById(R.id.tvTitleOfVal)
        tvTitleOfVal.setText(getString(R.string.nointernet_connection_mssge))

        val tvOk: TextView = dialog.findViewById(R.id.tvOK)
        tvOk.setOnClickListener(View.OnClickListener { dialog.dismiss() })

        dialog.show()
        val window = dialog.window!!
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
    }

    fun toastMessage(message: String) {
        Toast.makeText(this@LasrossParentKotlinActivity, message, Toast.LENGTH_LONG).show()
    }

    fun showConfirmDialog(mContext: Context, message: String) {
        val dialog = Dialog(mContext)
        Objects.requireNonNull<Window>(dialog.window).setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.session_expired_dialog)
        val tvOK: TextView = dialog.findViewById(R.id.tvOK)
        val tvTitleOfVal: TextView = dialog.findViewById(R.id.tvTitleOfVal)
        tvTitleOfVal.text = message
        tvOK.setOnClickListener {
            //startActivity(Intent(this, HomeActivity::class.java))
            dialog.dismiss()
            startActivity(Intent(mContext, HomeActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
            finish()
        }
        dialog.show()
        val window = dialog.window!!
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
    }

    fun showOnBackPressed(mContext: Context, message: String) {
        val dialog = Dialog(mContext)
        Objects.requireNonNull<Window>(dialog.window).setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.session_expired_dialog)
        val tvOK: TextView = dialog.findViewById(R.id.tvOK)
        val tvTitleOfVal: TextView = dialog.findViewById(R.id.tvTitleOfVal)
        tvTitleOfVal.text = message
        tvOK.setOnClickListener {
            //startActivity(Intent(this, HomeActivity::class.java))
            dialog.dismiss()
            onBackPressed()
            //startActivity(Intent(mContext, HomeActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
            finish()
        }
        dialog.show()
        val window = dialog.window!!
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
    }

    fun showOutOfStockDialog(mContext: Context, message: String) {
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
        }
        dialog.show()
        val window = dialog.window!!
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
    }

    fun showSubscribeDialog(mContext: Context, message: String) {
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
            mContext
        }
        dialog.show()
        val window = dialog.window!!
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
    }


    //*********** day diffrence  *****************//
    fun getDayDifference(departDateTime: String, curruntTime: String): String {
        var returnDay = ""
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        try {
            val startDate = simpleDateFormat.parse(departDateTime)
            val endDate = simpleDateFormat.parse(curruntTime)
            //milliseconds
            var different = endDate.time - startDate.time
            val secondsInMilli: Long = 1000
            val minutesInMilli = secondsInMilli * 60
            val hoursInMilli = minutesInMilli * 60
            val daysInMilli = hoursInMilli * 24
            val weekInMilli = daysInMilli * 7
            val monthInMilli = weekInMilli * 4
            val yearInMilli = monthInMilli * 52


            val elapsedYear = different / yearInMilli
            different = different % yearInMilli

            val elapsedMonth = different / monthInMilli
            different = different % monthInMilli

            val elapsedWeek = different / weekInMilli
            different = different % weekInMilli

            val elapsedDays = different / daysInMilli
            different = different % daysInMilli

            val elapsedHours = different / hoursInMilli
            different = different % hoursInMilli

            val elapsedMinutes = different / minutesInMilli
            different = different % minutesInMilli

            val elapsedSeconds = different / secondsInMilli

            if (elapsedYear >= 1) {
                returnDay = "$elapsedYear year ago"
            } else if (elapsedMonth >= 1) {
                returnDay = "$elapsedMonth month ago"
            } else if (elapsedWeek >= 1) {
                if (elapsedWeek == 1L) {
                    returnDay = "$elapsedWeek week ago"
                } else returnDay = "$elapsedWeek weeks ago"
            } else if (elapsedDays == 1L) {
                returnDay =  /*elapsedDays + */ "1 day ago"
            } else if (elapsedDays > 1 && elapsedDays < 7) {
                returnDay = "$elapsedDays days ago"
            } else if (elapsedDays == 0L) {
                if (elapsedHours == 0L) {
                    if (elapsedMinutes == 0L) {
                        returnDay = /*elapsedSeconds +*/ " Just now"
                    } else {
                        returnDay = "$elapsedMinutes mins ago"
                    }
                } else if (elapsedHours == 1L) {
                    returnDay = "$elapsedHours hour ago"
                } else {
                    returnDay = "$elapsedHours hours ago"
                }
            } else {
                // returnDay = "$elapsedDays days ago"
            }
        } catch (e: ParseException) {
            Log.d("day diffrence", e.message)
        }

        return returnDay
    }

    // get day difference
    fun getDayDifference1(departDateTime: String, curruntTime: String): String {
        var returnDay = ""
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        try {
            val startDate = simpleDateFormat.parse(departDateTime)
            val endDate = simpleDateFormat.parse(curruntTime)
            //milliseconds
            var different = endDate.time - startDate.time
            returnDay = different.toString()

        } catch (e: java.lang.Exception) {

        }
        return returnDay
    }


    fun getTwoValueAfterDecimal(value: String): String {
        var dbl = value.toDouble()
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return df.format(dbl)
    }

    fun getOneValueAfterDecimal(value: String): String {
        var dbl = value.toFloat()
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(dbl)
    }
}