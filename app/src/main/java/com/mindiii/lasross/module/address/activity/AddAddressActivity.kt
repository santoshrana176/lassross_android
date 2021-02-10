package com.mindiii.lasross.module.address.activity

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.mindiii.lasross.R
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.LasrossParentActivity
import com.mindiii.lasross.module.address.model.AddAddressResponse
import com.mindiii.lasross.module.address.presenter.AddAddressPresenter
import com.mindiii.lasross.utils.CommonUtils
import java.util.*
import java.util.regex.Pattern

@SuppressLint("Registered")
class AddAddressActivity : LasrossParentActivity(), View.OnClickListener, ApiCallback.AddAddressCallback {
    private var btnBackToAddress: ImageView? = null
    private var etAddress: EditText? = null
    private var etMobileNo: EditText? = null
    private var etAddressTitle: EditText? = null
    private var tvUpdateAddress: TextView? = null
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    private val TAG = "Place"
    private var pattern: Pattern? = null
    private var checkValue: String? = null
    private var strComeFrom: String? = null
    private var strItemPosition: String? = null
    private var intItemPosition: Int = 0
    private var mLastClickTime: Long = 0
    private var latitude: String? = null
    private var longitude: String? = null
    private var shipAddress: String? = null
    private var phoneNumber: String? = null
    private var companyType: String? = null
    private var addressID: String? = null


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_address_activty_39)

        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.home_header_bg1)
        initview()
        if (intent.getStringExtra("strComeFrom") != null) {
            if (intent.getStringExtra("strComeFrom") == "UpdateBtn") {
                checkValue = intent.getStringExtra("CheckValue")
                strComeFrom = intent.getStringExtra("strComeFrom")
                shipAddress = intent.getStringExtra("shipAddress")
                latitude = intent.getStringExtra("latitude")
                longitude = intent.getStringExtra("longitude")
                phoneNumber = intent.getStringExtra("phoneNumber")
                companyType = intent.getStringExtra("companyType")
                addressID = intent.getStringExtra("addressID")
                etAddressTitle!!.setText(companyType)
                etMobileNo!!.setText(phoneNumber)
                etAddress!!.setText(shipAddress)
                tvUpdateAddress!!.setText(resources.getString(R.string.update_address))
            } else {
                strComeFrom = intent.getStringExtra("strComeFrom")
                checkValue = intent.getStringExtra("CheckValue")
                tvUpdateAddress!!.setText(resources.getString(R.string.add_address))
            }
        }
        btnBackToAddress!!.setOnClickListener(this)
        etAddress!!.setOnClickListener(this)
        tvUpdateAddress!!.setOnClickListener(this)
        pattern = Pattern.compile("^[a-zA-Z ]+$")

    }

    private fun initview() {
        etAddress = findViewById(R.id.etAddress)
        etMobileNo = findViewById(R.id.etMobileNo)
        etAddressTitle = findViewById(R.id.etAddressTitle)
        tvUpdateAddress = findViewById(R.id.tvUpdateAddress)
        btnBackToAddress = findViewById(R.id.btnBackToAddress)
    }

    private fun getAddress() {
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, getString(R.string.place_key), Locale.US)
        }
        val autocompleteFragment = (supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment?)!!

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME))
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {}
            override fun onError(status: Status) {}
        })

        val fields = listOf(Place.Field.ADDRESS, Place.Field.NAME, Place.Field.LAT_LNG)

        val intent = Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this)
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val place = Autocomplete.getPlaceFromIntent(data!!)
                if (place.latLng != null) {
                    latitude = place.latLng!!.latitude.toString()
                    longitude = place.latLng!!.longitude.toString()
                    etAddress!!.setText(place.name + "\n" + place.address)
                }
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                val status = Autocomplete.getStatusFromIntent(data!!)
                assert(status.statusMessage != null)
                Log.e(TAG, status.statusMessage!!)
            }
        }
    }

    override fun onClick(view: View) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
            return
        }
        mLastClickTime = SystemClock.elapsedRealtime()
        when (view.id) {
            R.id.btnBackToAddress -> {
                //finish()
                onBackPressed()
            }
            R.id.etAddress -> {
                getAddress()
            }
            R.id.tvUpdateAddress ->
                if (CommonUtils.isNetworkAvailable(this)!!) {
                    getDataForApi()
                } else run { showInternetAlertDialog(this) }
        }
    }


    private fun getDataForApi() {
        val name = etAddressTitle!!.text.toString().trim()
        val mobileNo = etMobileNo!!.text.toString().trim()
        val address = etAddress!!.text.toString().trim()

        if (name.isEmpty()) {
            CommonUtils.showCustomAlert(this, resources.getString(R.string.enter_address_title))
            return
        } else if (mobileNo.isEmpty()) {
            CommonUtils.showCustomAlert(this, resources.getString(R.string.enter_mobile_no))
            //etEmail.setError("Enter email");
            return
        } else if (mobileNo.length < 10) {
            CommonUtils.showCustomAlert(this, resources.getString(R.string.mobile_no_ten_digit))
            //etEmail.setError("Enter email");
            return
        } else if (address.isEmpty()) {
            CommonUtils.showCustomAlert(this, resources.getString(R.string.str_select_address))
            return
        }

        if (isValidName(name)) {
            if (strComeFrom == "Addbutton") {
                latitude?.let { longitude?.let { it1 -> callAddAddressApi(name, mobileNo, address, it, it1) } }
            } else {
                longitude?.let { latitude?.let { it1 -> callUpdateAddressApi(name, mobileNo, address, it1, it) } }
            }

        } else if (!isValidName(name)) {
            CommonUtils.showCustomAlert(this, getString(R.string.only_alphabate))
        }
    }

    private fun callUpdateAddressApi(fullName: String, mobileNumber: String, myAddress: String, myLati: String, myLong: String) {
        addressID?.let {
            AddAddressPresenter(this, this).callUpdateAddressApi(it,
                    myAddress, myLati, myLong, fullName, mobileNumber)
        }
    }

    private fun callAddAddressApi(fullName: String, mobileNumber: String, myAddress: String, myLati: String, myLong: String) {
        AddAddressPresenter(this, this).callAddAddressApi(fullName, mobileNumber, myAddress, myLati, myLong)
    }

    private fun isValidName(lName: String): Boolean {
        return pattern!!.matcher(lName).matches()
    }

    fun showConfirmAddressAddDialog(message: String) {
        val dialog = Dialog(this)
        Objects.requireNonNull<Window>(dialog.window).setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.session_expired_dialog)
        val tvOK: TextView = dialog.findViewById(R.id.tvOK)
        val tvTitleOfVal: TextView = dialog.findViewById(R.id.tvTitleOfVal)
        tvTitleOfVal.text = message
        tvOK.setOnClickListener {
            if (checkValue == "CheckFound") {
                finish()
            } else {
                startActivity(Intent(this, MyAddressesActivity::class.java)
                        .putExtra("CheckValue", "CheckNotFound")
                        .putExtra("showClearButton", "showClearButtonProfile"))
                finish()
            }
        }
        dialog.show()
        val window = dialog.window!!
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
    }

    override fun onSuccessAddAddress(addAddressResponse: AddAddressResponse?) {
        /*toastMessage(addAddressResponse!!.message)
          if (checkValue == "CheckFound"){
              finish()
          }else{
              startActivity(Intent(this, MyAddressesActivity38::class.java)
                      .putExtra("CheckValue","CheckNotFound")
                      .putExtra("showClearButton","showClearButtonProfile"))
              finish()
          }*/

        showConfirmAddressAddDialog(addAddressResponse!!.message)
    }

    override fun onUpdateAddress(addAddressResponse: AddAddressResponse?) {
        /*toastMessage(addAddressResponse!!.message)
        if (checkValue == "CheckFound"){
            finish()
        }else{
            startActivity(Intent(this, MyAddressesActivity38::class.java)
                    .putExtra("CheckValue","CheckNotFound")
                    .putExtra("showClearButton","showClearButtonProfile"))
            finish()
        }*/
        showConfirmAddressAddDialog(addAddressResponse!!.message)
    }

    override fun onTokenChangeError(errorMessage: String?) {
        showDialog(this)
    }

    override fun onShowBaseLoader() {
        showLoader()
    }

    override fun onError(errorMessage: String?) {
        toastMessage(errorMessage)
    }

    override fun onHideBaseLoader() {
        hideLoader()
    }
}

