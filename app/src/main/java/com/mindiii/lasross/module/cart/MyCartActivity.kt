package com.mindiii.lasross.module.cart

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.ClickListener
import com.mindiii.lasross.base.LasrossParentKotlinActivity
import com.mindiii.lasross.module.address.activity.MyAddressesActivity
import com.mindiii.lasross.module.cart.adapter.MyCartAdapter
import com.mindiii.lasross.module.cart.model.*
import com.mindiii.lasross.module.cart.presenter.GetCartPresenter
import com.mindiii.lasross.utils.CommonUtils
import com.mindiii.lasross.utils.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.activity_my_cart.*


class MyCartActivity : LasrossParentKotlinActivity(), ApiCallback.GetCartCallback, View.OnClickListener {
    private lateinit var cartList: ArrayList<Cart>
    private var myCartAdapter: MyCartAdapter? = null
    private var quantity: Int = 0
    private var index: Int = 0
    private var offset: Int = 0
    private var layoutManager1: LinearLayoutManager? = null
    private var mLastClickTime: Long = 0
    private var latitude: String = ""
    private var item_count: Int = 0
    private var longitude: String = ""
    private lateinit var tax: String
    private lateinit var session: Session
    private var intOutOfStock = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_cart)
        cartList = ArrayList()
        cartList.clear()

        session = Session(this)

        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = this.resources.getColor(R.color.home_header_bg1)
        }

        if (CommonUtils.isNetworkAvailable(this)!!) {
            llBottomLayout.visibility = View.VISIBLE
            rlListView.visibility = View.VISIBLE
        } else {
            llBottomLayout.visibility = View.GONE
            rlListView.visibility = View.GONE
            //showInternetAlertDialog(this)
        }


        tvCartBack.setOnClickListener(this)
        btnCardContinue.setOnClickListener(this)
        btnClearCart.setOnClickListener(this)
        apiCalling(offset.toString())
        setAdapter()

        layoutManager1 = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        cartListRecyclerView.layoutManager = layoutManager1
        val scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager1) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                if (page != 0) {
                    offset += 18 //load 18 items in recyclerView
                    apiCalling(offset.toString())
                }
            }
        }
        cartListRecyclerView.addOnScrollListener(scrollListener)
    }

    private fun apiCalling(myOffset: String) {
        if (CommonUtils.isNetworkAvailable(this)!!) {
            GetCartPresenter(this, this).callCartListApi(myOffset)
        } else {
            showInternetAlertDialog(this)
        }
    }

    private fun clearAllItemApi() {
        GetCartPresenter(this, this).callClearAllItemApi()
    }

    private fun setAdapter() {
        myCartAdapter = MyCartAdapter(cartList, this, object : ClickListener.CartItemClickListener {
            override fun onItenDecreaseClick(position: Int) {
                if (CommonUtils.isNetworkAvailable(this@MyCartActivity)!!) {
                    index = position
                    val cartItemId: String = cartList[position].cartItemId
                    val productId: String = cartList[position].productId
                    quantity = cartList[position].cart_item_quantity.toInt()
                    if (quantity != 1) {
                        quantity -= quantity
                        callUpdateDecreaseQuantity(cartItemId, productId, "1")
                    }
                } else run { showInternetAlertDialog(this@MyCartActivity) }
            }

            override fun onItenIncreaseClick(position: Int) {
                if (CommonUtils.isNetworkAvailable(this@MyCartActivity)!!) {

                    index = position
                    val cartItemId: String = cartList[position].cartItemId
                    val productId: String = cartList[position].productId
                    quantity = cartList[position].cart_item_quantity.toInt()
                    quantity += quantity
                    if (cartList[position].cart_item_quantity == cartList[position].product_available_quantity) {
                        val message: String = getString(R.string.we_only_have_)+" "+ cartList[position].product_available_quantity + " "+getString(R.string.qty_for_this)
                        showConfirmDialog(this@MyCartActivity, message)
                    } else {
                        callUpdateIncreaseQuantity(cartItemId, productId, "1")
                    }
                } else run {
                    showInternetAlertDialog(this@MyCartActivity)
                }
            }

            override fun onItenDeleteClick(position: Int) {
                if (CommonUtils.isNetworkAvailable(this@MyCartActivity)!!) {

                    intOutOfStock = 0
                    item_count = item_count - cartList[position].cart_item_quantity.toInt()
                    session.cartItemCount = item_count.toString()
                    deleteCartItemApiCalling(cartList[position].cartItemId)
                } else {
                    showInternetAlertDialog(this@MyCartActivity)
                }
            }

        })
        cartListRecyclerView.adapter = myCartAdapter
    }

    private fun deleteCartItemApiCalling(cartItemId: String) {
        GetCartPresenter(this, this).callDeleteCartItemApi(cartItemId)
    }

    private fun callUpdateIncreaseQuantity(itemID: String, productIds: String, cartQuantity: String) {
        GetCartPresenter(this, this).callUpdateCartListApi(itemID, productIds, cartQuantity)
    }

    private fun callUpdateDecreaseQuantity(itemID: String, productIds: String, cartQuantity: String) {
        GetCartPresenter(this, this).callUpdateDecreaseApi(itemID, productIds, cartQuantity)
    }

    override fun onHideBaseLoader() {
        hideLoader()
    }

    override fun onSuccessGetCart(cartListResponse: CartListResponse?) {
        if (cartListResponse!!.data.shipping_detail.warehouse_latitude != null) {
            latitude = cartListResponse!!.data.shipping_detail.warehouse_latitude
            longitude = cartListResponse.data.shipping_detail.warehouse_longitude
            item_count = cartListResponse.data.pricing_detail.total_item.toInt()
        }
        tax = cartListResponse.data.shipping_detail.tax
        cartList.addAll(cartListResponse!!.data.cart_list)
        myCartAdapter!!.notifyDataSetChanged()
        setData(cartListResponse)
    }

    @SuppressLint("SetTextI18n")
    private fun setData(cartListResponse: CartListResponse?) {

        for (i in 0 until cartList.size) {
            if (cartList[i].in_stock.equals("0")) {
                intOutOfStock++
            }
        }
        tvItem.text = cartListResponse!!.data.pricing_detail.total_item

        tvTotalAmount.text = cartListResponse.data.pricing_detail.currency_symbol + " " + cartListResponse.data.pricing_detail.total_amount
        tvTotal.text = cartListResponse.data.pricing_detail.currency_symbol + " " + cartListResponse.data.pricing_detail.total_amount
        when {
            cartList.size == 1 -> {
                tvMyCartTotalItem.text = "(" + cartList.size + " "+getString(R.string.item)+")"
                tvMyCartTotalItem.visibility = View.VISIBLE
            }
            cartList.isEmpty() -> {
                tvMyCartTotalItem.text = getString(R.string.item_zero)
                tvMyCartTotalItem.visibility = View.VISIBLE
            }
            else -> {
                tvMyCartTotalItem.text = "(" + cartList.size +" "+getString(R.string.item)+")"
                tvMyCartTotalItem.visibility = View.VISIBLE
            }
        }

        if (cartList.size < 1) {
            llNoMyCartItem.visibility = View.VISIBLE
            cartListRecyclerView.visibility = View.GONE
            llPriceView.visibility = View.GONE
            llBottomLayout.visibility = View.GONE
            myCartAdapter!!.notifyDataSetChanged()
        } else {
            llNoMyCartItem.visibility = View.GONE
            cartListRecyclerView.visibility = View.VISIBLE
            llPriceView.visibility = View.VISIBLE
            llBottomLayout.visibility = View.VISIBLE
            myCartAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onTokenChangeError(errorMessage: String?) {
        showDialog(this)
    }

    override fun onShowBaseLoader() {
        showLoader()
    }

    override fun onError(errorMessage: String?) {
        errorMessage?.let { toastMessage(it) }
    }

    override fun onClick(p0: View?) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return
        }
        mLastClickTime = SystemClock.elapsedRealtime()
        when (p0!!.id) {
            R.id.tvCartBack -> {
                /*startActivity(Intent(this, HomeActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                finish()*/
                onBackPressed()

            }
            R.id.btnCardContinue -> {
                if (CommonUtils.isNetworkAvailable(this)!!) {
                    if (intOutOfStock > 0) {
                        showOutOfStockDialog(this, getString(R.string.remove_out_off_stock))
                    } else {
                        startActivity(Intent(this, MyAddressesActivity::class.java)
                                .putExtra("CheckValue", "CheckFound")
                                .putExtra("from", "MyCart")
                                .putExtra("totalItem", tvItem.text.toString())
                                .putExtra("totalAmt", tvTotalAmount.text.toString())
                                .putExtra("warehouse_lat", latitude)
                                .putExtra("warehouse_lng", longitude)
                                .putExtra("warehouse_tax", tax)
                                .putExtra("total", tvTotal.text.toString()))
                    }
                } else {
                    showInternetAlertDialog(this)
                }
            }
            R.id.btnClearCart -> {
                if (CommonUtils.isNetworkAvailable(this)!!) {
                    session.setCartItemCount("0")
                    clearAllItemApi()
                } else {
                    showInternetAlertDialog(this)
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    @SuppressLint("SetTextI18n")
    override fun onSuccessUpdateCartItem(cartItemIncreaseResponse: CartItemIncreaseResponse?) {
        if (cartItemIncreaseResponse!!.data != null) {
            cartList[index].cart_item_quantity = cartItemIncreaseResponse!!.data.cart_list.cart_item_quantity
            myCartAdapter!!.notifyDataSetChanged()
            tvItem.text = cartItemIncreaseResponse.data.pricing_detail.total_item
            session.setCartItemCount(cartItemIncreaseResponse.data.pricing_detail.total_item)
            //cartItemCountHome = cartItemIncreaseResponse.data.pricing_detail.total_item
            tvTotalAmount.text = cartItemIncreaseResponse.data.pricing_detail.currency_symbol + " " + cartItemIncreaseResponse.data.pricing_detail.total_amount
            tvTotal.text = cartItemIncreaseResponse.data.pricing_detail.currency_symbol + " " + cartItemIncreaseResponse.data.pricing_detail.total_amount
        } else {
            toastMessage(cartItemIncreaseResponse.message)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onSuccessUpdateCartDecreaseItem(cartItemIncreaseResponse: CartItemIncreaseResponse?) {
        cartList[index].cart_item_quantity = cartItemIncreaseResponse!!.data.cart_list.cart_item_quantity
        myCartAdapter!!.notifyDataSetChanged()
        tvItem.text = cartItemIncreaseResponse.data.pricing_detail.total_item
        session.setCartItemCount(cartItemIncreaseResponse.data.pricing_detail.total_item)
        //cartItemCountHome = cartItemIncreaseResponse.data.pricing_detail.total_item
        tvTotalAmount.text = cartItemIncreaseResponse.data.pricing_detail.currency_symbol + " " + cartItemIncreaseResponse.data.pricing_detail.total_amount
        tvTotal.text = cartItemIncreaseResponse.data.pricing_detail.currency_symbol + " " + cartItemIncreaseResponse.data.pricing_detail.total_amount
    }

    override fun onDeleteCartItem(deleteCartItemResponse: DeleteCartItemResponse?) {
        cartList.clear()
        offset = 0
        apiCalling(offset.toString())
    }

    override fun onSuccessClearAllItem(cartClearAllResponse: CartClearAllResponse?) {
        cartList.clear()
        offset = 0
        apiCalling(offset.toString())
    }

    override fun onRestart() {
        super.onRestart()
        cartList.clear()
        offset = 0
        apiCalling(offset.toString())
        if (CommonUtils.isNetworkAvailable(this)!!) {
            llBottomLayout.visibility = View.VISIBLE
            rlListView.visibility = View.VISIBLE
        } else {
            llBottomLayout.visibility = View.GONE
            rlListView.visibility = View.GONE
            //showInternetAlertDialog(this)
        }
    }
}
