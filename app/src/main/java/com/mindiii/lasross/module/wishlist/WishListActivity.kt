package com.mindiii.lasross.module.wishlist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.ClickListener
import com.mindiii.lasross.base.LasrossParentKotlinActivity
import com.mindiii.lasross.module.cart.MyCartActivity
import com.mindiii.lasross.module.home.model.AddRemoveWishListResponse
import com.mindiii.lasross.module.productDetail.ProductDetailActivity
import com.mindiii.lasross.module.wishlist.adapter.WishListAdapter
import com.mindiii.lasross.module.wishlist.model.AllClearResponse
import com.mindiii.lasross.module.wishlist.model.UserWishlist
import com.mindiii.lasross.module.wishlist.model.WishListResponse
import com.mindiii.lasross.module.wishlist.presenter.WishListPresenter
import com.mindiii.lasross.utils.EndlessRecyclerViewScrollListener
import kotlinx.android.synthetic.main.wish_list_activity_layout_20.*

class WishListActivity : LasrossParentKotlinActivity(), ApiCallback.UserWishListCallback, View.OnClickListener
{
    var wishListAdapter: WishListAdapter? = null
    lateinit var userWishList: ArrayList<UserWishlist>
    private var index: Int = 0
    private var count: Int = 0
    private var layoutManager1: LinearLayoutManager? = null
    private var offset: Int = 0
    private var mLastClickTime: Long = 0
    private lateinit var session: Session


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wish_list_activity_layout_20)
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = this.resources.getColor(R.color.home_header_bg1)
        userWishList = ArrayList()
        ivBackButton.setOnClickListener(this)
        btnClearAll.setOnClickListener(this)
        goCart.setOnClickListener(this)

        session = Session(this)

        if (session.cartItemCount.equals("0", ignoreCase = true))
            tvCartItemCountWishlist.setVisibility(View.GONE)
        else {
            tvCartItemCountWishlist.setVisibility(View.VISIBLE)
            tvCartItemCountWishlist.setText(session.cartItemCount)
        }

        callWishListApi(offset.toString())
        setWishListAdapter()

        layoutManager1 = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvWishList1.layoutManager = layoutManager1
        val scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager1) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                if (page != 0) {
                    offset += 18 //load 18 items in recyclerView
                    callWishListApi(offset.toString())
                }
            }
        }
        rvWishList1.addOnScrollListener(scrollListener)
    }

    private fun setWishListAdapter() {
        wishListAdapter = WishListAdapter(userWishList, this, object : ClickListener.WishListListener {
            override fun onAddToCartClick(position: Int) {
            }

            override fun onItemClick(position: Int) {
                val productID = userWishList[position].productId
                val intent = Intent(this@WishListActivity, ProductDetailActivity::class.java)
                intent.putExtra("productId", productID)
                startActivity(intent)
            }

            override fun onDelete(position: Int) {
                index = position
                removeWishListItemAPI(userWishList.get(position).productId)
            }
        })
        rvWishList1.adapter = wishListAdapter
    }

    override fun onHideBaseLoader() {
        hideLoader()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //  startActivity(Intent(this, HomeActivity::class.java))
        // finish()
    }

    @SuppressLint("SetTextI18n")
    override fun onSuccessUserWishList(wishListResponse: WishListResponse?) {
        userWishList.addAll(wishListResponse!!.data?.user_wishlist!! as ArrayList<UserWishlist>)
        count = (wishListResponse.data?.total_records)!!.toInt()
        when {
            wishListResponse.data?.total_records == "0" -> tvWishlistTotalItem.setText("(0 item)")
            wishListResponse.data?.total_records == "1" -> tvWishlistTotalItem.setText("(" + wishListResponse.data?.total_records + " item)")
            else -> tvWishlistTotalItem.text = "(" + wishListResponse.data?.total_records + " items)"
        }

        if (userWishList.size > 0) {
            tvNotFound.visibility = View.GONE
            btnClearAll.visibility = View.VISIBLE
            rvWishList1.visibility = View.VISIBLE
            wishListAdapter!!.notifyDataSetChanged()
        } else {
            tvNotFound.visibility = View.VISIBLE
            btnClearAll.visibility = View.GONE
            rvWishList1.visibility = View.GONE
        }

    }

    override fun onSuccessAllClearWishList(allClearResponse: AllClearResponse?) {
        userWishList.clear()
        offset = 0
        callWishListApi(offset.toString())
    }

    override fun onSuccessAddRemoveWishList(addRemoveWishListResponse: AddRemoveWishListResponse?) {
        userWishList.clear()
        offset = 0
        callWishListApi(offset.toString())
        wishListAdapter?.notifyDataSetChanged()
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

    fun removeWishListItemAPI(productID: String?) {
        if (productID != null) {
            WishListPresenter(this, this).callAddRemoveWishListApi(productID)
        }
    }

    fun callWishListApi(myOffset: String) {
        WishListPresenter(this, this).callUserWishListApi(myOffset)
    }

    private fun callClearAllApi() {
        WishListPresenter(this, this).callAllClearWishListApi()
    }

    override fun onClick(view: View?) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return
        }
        mLastClickTime = SystemClock.elapsedRealtime()
        if (view != null) {
            when (view.id) {
                R.id.ivBackButton -> /*startActivity(Intent(this, HomeActivity::class.java))*/onBackPressed()
                R.id.btnClearAll -> callClearAllApi()
                R.id.goCart -> startActivity(Intent(this, MyCartActivity::class.java))

            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        userWishList.clear()
        offset = 0
        callWishListApi(offset.toString())
        if (session.cartItemCount.equals("0", ignoreCase = true))
            tvCartItemCountWishlist.setVisibility(View.GONE)
        else {
            tvCartItemCountWishlist.setVisibility(View.VISIBLE)
            tvCartItemCountWishlist.setText(session.cartItemCount)
        }
        //tvCartItemCountWishlist.text = Session(this).getCartItemCount()
    }
}