package com.mindiii.lasross.module.address.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mindiii.lasross.R
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.ClickListener
import com.mindiii.lasross.base.LasrossParentActivity
import com.mindiii.lasross.module.address.adapter.MyAddressAdapter
import com.mindiii.lasross.module.address.model.AddAddressResponse
import com.mindiii.lasross.module.address.model.UserAddressListResponse
import com.mindiii.lasross.module.address.presenter.MyAddressPresenter
import com.mindiii.lasross.module.delivery.DeliveryActivity
import com.mindiii.lasross.module.home.HomeActivity
import com.mindiii.lasross.utils.CommonUtils
import com.mindiii.lasross.utils.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.my_addresses_activty_38.*
import java.util.*

class MyAddressesActivity38 : LasrossParentActivity(), View.OnClickListener, ApiCallback.MyAddressCallback {
    lateinit var myAddressAdapter: MyAddressAdapter
    private var count: Int = 0
    private var offset: Int = 0
    private var layoutManager1: LinearLayoutManager? = null
    lateinit var userAddressList: ArrayList<UserAddressListResponse.DataBean.UserAddresslistBean>
    private var checkValue: String? = null
    private var totalItem: String? = null
    private var totalAmt: String? = null
    private var from: String? = null
    private var total: String? = null
    private var warehouse_lat: String? = null
    private var warehouse_lng: String? = null
    private var warehouse_tax: String? = null
    private var positionInt = -1

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        count = 0
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_addresses_activty_38)
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.home_header_bg1)
        btnAddNewAddress.setOnClickListener(this)
        ivMyAddressBack.setOnClickListener(this)
        userAddressList = ArrayList()
        userAddressList.clear()
        callUserAddressListApi(offset.toString())
        tvTotalAddress.text = "0 SAVED ADDRESS"

        if (CommonUtils.isNetworkAvailable(this)!!) {
            llRecyclerView.visibility = View.VISIBLE
            tvAddressNotFound.visibility = View.VISIBLE
            llBottomLayout.visibility = View.VISIBLE
        } else {
            llRecyclerView.visibility = View.GONE
            tvAddressNotFound.visibility = View.GONE
            llBottomLayout.visibility = View.GONE
        }
        //setAdapter()

        if (intent.getStringExtra("showClearButton") != null) {
            btnClearOrderMyAdd.visibility = View.GONE
        } else {
            btnClearOrderMyAdd.visibility = View.VISIBLE
        }

        btnClearOrderMyAdd.setOnClickListener(this)

        if (intent.getStringExtra("CheckValue") != null) {
            checkValue = intent.getStringExtra("CheckValue")
            totalItem = intent.getStringExtra("totalItem")
            totalAmt = intent.getStringExtra("totalAmt")
            from = intent.getStringExtra("from")
            total = intent.getStringExtra("total")
            warehouse_lat = intent.getStringExtra("warehouse_lat")
            warehouse_lng = intent.getStringExtra("warehouse_lng")
            warehouse_tax = intent.getStringExtra("waaddAddressResponserehouse_tax")
        }


        checkValue?.let { setAdapter(it) }
        /*pagination*/
        layoutManager1 = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.layoutManager = layoutManager1
        val scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager1) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                if (page != 0) {
                    offset += 18 //load 18 items in recyclerView
                    callUserAddressListApi(offset.toString())
                }
            }
        }
        recyclerView.addOnScrollListener(scrollListener)

        if (count == 0) {
            tvAddressNotFound.visibility = View.VISIBLE
            llRecyclerView.visibility = View.GONE
            //recyclerView.visibility = View.GONE
        } else {
            tvAddressNotFound.visibility = View.GONE
            llRecyclerView.visibility = View.VISIBLE
            //recyclerView.visibility = View.VISIBLE
        }
    }

    private fun setAdapter(checkValue: String) {
        userAddressList.clear()
        myAddressAdapter = MyAddressAdapter(checkValue, userAddressList, this, object : ClickListener.MyAddressListener {
            override fun onItemDelete(position: Int) {
                if (CommonUtils.isNetworkAvailable(this@MyAddressesActivity38)!!) {
                    callRemoveAddressApi(userAddressList.get(position).userAddressId)
                    myAddressAdapter.notifyItemRemoved(position)
                } else {
                    showInternetAlertDialog(this@MyAddressesActivity38)
                }
            }

            override fun onItemUpdate(position: Int) {
                if (CommonUtils.isNetworkAvailable(this@MyAddressesActivity38)!!) {
                    startActivity(Intent(this@MyAddressesActivity38, AddAddressActivity39::class.java)
                            .putExtra("shipAddress", userAddressList[position].ship_address_location)
                            .putExtra("latitude", userAddressList[position].ship_address_latitude)
                            .putExtra("longitude", userAddressList[position].ship_address_longitude)
                            .putExtra("phoneNumber", userAddressList[position].bill_address_phone)
                            .putExtra("companyType", userAddressList[position].ship_address_company_name)
                            .putExtra("addressID", userAddressList[position].userAddressId)
                            .putExtra("strComeFrom", "UpdateBtn"))
                    finish()
                } else {
                    showInternetAlertDialog(this@MyAddressesActivity38)
                }
            }

            override fun onCheckBoxClick(position: Int) {
                positionInt = position
                if (positionInt != -1) {
                    if (checkValue.equals("CheckFound"))
                        btnAddNewAddress.text = resources.getString(R.string.continues)
                    else
                        btnAddNewAddress.text = resources.getString(R.string.add_new_address)

                } else {
                    btnAddNewAddress.text = resources.getString(R.string.add_new_address)
                }
            }
        })
        recyclerView.adapter = myAddressAdapter
    }

    fun callRemoveAddressApi(position: String) {
        MyAddressPresenter(this, this).callRemoveAddressApi(position)
    }

    fun callUserAddressListApi(myOffset: String) {
        if (CommonUtils.isNetworkAvailable(this)!!) {
            MyAddressPresenter(this, this).callUserAddressListApi(myOffset, "18")
        } else {
            showInternetAlertDialog(this)
        }
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btnAddNewAddress) {
            if (CommonUtils.isNetworkAvailable(this)!!) {
                if (checkValue.equals("CheckFound")) {
                    when {
                        positionInt >= 0 -> {
                            startActivity(Intent(this, DeliveryActivity::class.java)
                                    .putExtra("address", userAddressList[positionInt].ship_address_location)
                                    .putExtra("number", userAddressList[positionInt].bill_address_phone)
                                    .putExtra("companyName", userAddressList[positionInt].ship_address_company_name)
                                    .putExtra("latitude", userAddressList[positionInt].ship_address_latitude)
                                    .putExtra("longitude", userAddressList[positionInt].ship_address_longitude)
                                    .putExtra("addressID", userAddressList[positionInt].userAddressId)
                                    .putExtra("totalItem", totalItem)
                                    .putExtra("totalAmt", totalAmt)
                                    .putExtra("warehouse_lat", warehouse_lat)
                                    .putExtra("warehouse_lng", warehouse_lng)
                                    .putExtra("warehouse_tax", warehouse_tax)
                                    .putExtra("total", total))
                            // finish()

                        }
                        positionInt == -1 -> startActivity(Intent(this, AddAddressActivity39::class.java)
                                .putExtra("strComeFrom", "Addbutton")
                                .putExtra("CheckValue", checkValue))
                        else -> toastMessage("Please select address")
                    }
                } else {
                    startActivity(Intent(this, AddAddressActivity39::class.java)
                            .putExtra("strComeFrom", "Addbutton")
                            .putExtra("CheckValue", checkValue))
                    finish()
                }
            } else {
                showInternetAlertDialog(this)
            }
        } else if (view.id == R.id.ivMyAddressBack) {
            finish()
        } else if (view.id == R.id.btnClearOrderMyAdd) {
            if (CommonUtils.isNetworkAvailable(this)!!) {
                startActivity(Intent(this, HomeActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                finish()
            } else {
                showInternetAlertDialog(this)
            }
        }
    }

    override fun onSuccessUserAddressList(userAddressListResponse: UserAddressListResponse) {
        tvTotalAddress.text = "${userAddressListResponse.data.total_records} SAVED ADDRESS"
        userAddressList.addAll(userAddressListResponse.data.user_addresslist)
        if (userAddressList.size != 0) {
            count = userAddressList.size
            tvTotalAddress.text = userAddressList.size.toString() + " SAVED ADDRESS"
            tvAddressNotFound.visibility = View.GONE
            llRecyclerView.visibility = View.VISIBLE
            //recyclerView.visibility = View.VISIBLE
        } else {
            tvTotalAddress.text = 0.toString() + " SAVED ADDRESS"
            tvAddressNotFound.visibility = View.VISIBLE
            llRecyclerView.visibility = View.GONE
            //recyclerView.visibility = View.GONE
        }
        myAddressAdapter.notifyDataSetChanged()
    }

    override fun onDeleteUserAddress(addAddressResponse: AddAddressResponse?) {
        userAddressList.clear()
        offset = 0
        myAddressAdapter.notifyDataSetChanged()
        callUserAddressListApi(offset.toString())
    }

    override fun onTokenChangeError(errorMessage: String?) {
        showDialog(this)
    }

    override fun onShowBaseLoader() {
        showLoader()
    }

    override fun onHideBaseLoader() {
        hideLoader()
    }

    override fun onError(errorMessage: String?) {
        toastMessage(errorMessage)
    }

    override fun onRestart() {
        super.onRestart()
        userAddressList.clear()
        offset = 0
        myAddressAdapter.notifyDataSetChanged()
        callUserAddressListApi(offset.toString())
        if (CommonUtils.isNetworkAvailable(this)!!) {
            llRecyclerView.visibility = View.VISIBLE
            tvAddressNotFound.visibility = View.VISIBLE
            llBottomLayout.visibility = View.VISIBLE
        } else {
            llRecyclerView.visibility = View.GONE
            tvAddressNotFound.visibility = View.GONE
            llBottomLayout.visibility = View.GONE
        }
    }
}
