package com.mindiii.lasross.module.profile.fragment

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.BaseKotlinFragment
import com.mindiii.lasross.module.address.activity.MyAddressesActivity
import com.mindiii.lasross.module.address.model.UserAddressListResponse
import com.mindiii.lasross.module.cart.MyCartActivity
import com.mindiii.lasross.module.cart.model.CartListResponse
import com.mindiii.lasross.module.faq.FAQActivity
import com.mindiii.lasross.module.home.HomeActivity
import com.mindiii.lasross.module.loginregistration.model.LoginResponse
import com.mindiii.lasross.module.notification.model.NotificationListModel
import com.mindiii.lasross.module.payment.MyCardActivity
import com.mindiii.lasross.module.profile.presenter.GetAddressPresenter
import com.mindiii.lasross.module.profile.presenter.GetProfilePresenter
import com.mindiii.lasross.module.settings.SettingActivity
import com.mindiii.lasross.module.wishlist.WishListActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfileFragment : BaseKotlinFragment(), View.OnClickListener, ApiCallback.GetProfileCallback,
        ApiCallback.GetAddressCallback {
    var param1: String? = null
    var param2: String? = null
    private var mLastClickTime: Long = 0
    lateinit var session: Session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClicks(iv_back_Icon, ivProfileEdit, rlMyAddress, rlMyCards, rlMyWishList, rlMyCarts, rlMyOrders, rlSettings)
        session = Session(mContext)
        apiCalling()
        getAddressListApi()
    }

    fun getAddressListApi() {
        GetAddressPresenter(this, requireContext()).callUserAddressListApi("", "")
    }

    fun apiCalling() {
        mContext?.let { GetProfilePresenter(this, it).callGetProfileApi() }
    }

    private fun setOnClicks(vararg views: View) {
        for (view in views) view.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return
        }
        mLastClickTime = SystemClock.elapsedRealtime()

        when (p0!!.id) {
            R.id.iv_back_Icon -> {
                //activity.onBackPressed()
                startActivity(Intent(mContext, HomeActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                activity.finish()
            }
            R.id.ivProfileEdit -> {
                activity.hideKeyBoard()
                activity.addFragment(UpdateProfileFragment.newInstance(Session(mContext).authToken), true, R.id.profileFrame)
            }
            R.id.rlMyAddress -> {
                startActivity(Intent(mContext, MyAddressesActivity::class.java)
                        .putExtra("CheckValue", "CheckNotFound")
                        .putExtra("showClearButton", "showClearButtonProfile"))
            }
            R.id.rlMyCards -> {
                startActivity(Intent(mContext, MyCardActivity::class.java)
                        .putExtra("cardSelection", "CardNotSelected")
                        .putExtra("showClearButton", "showClearButtonProfile"))
            }
            R.id.rlMyWishList -> {
                startActivity(Intent(mContext, WishListActivity::class.java))
            }
            R.id.rlMyCarts -> {
                startActivity(Intent(mContext, MyCartActivity::class.java))
            }
            R.id.rlMyOrders -> {
                startActivity(Intent(mContext, FAQActivity::class.java)
                        .putExtra("screenCheck", "myOrder"))
                /*  activity.addFragment(MyOrdersFragment.newInstance("PROFILE_FRAGMENT"), true, R.id.profileFrame)*/
            }
            R.id.rlSettings -> {
                startActivity(Intent(mContext, SettingActivity::class.java))
            }
        }
    }

    private fun setData(dataBean: LoginResponse.DataBean) {
        /*address = dataBean.userDetail.ship_address_location
        latitude = dataBean.userDetail.ship_address_latitude
        longitude = dataBean.userDetail.ship_address_longitude*/
        tvUserName.text = dataBean.userDetail.full_name
        if (dataBean.userDetail.profile_photo == "")
        //  Picasso.with(mContext).load(dataBean.userDetail.thumbImage).into(ivUserImage)
        else
            Picasso.with(mContext).load(dataBean.userDetail.profile_photo).into(ivUserImage)

    }

    companion object {
        @JvmStatic
        fun newInstance() =
                ProfileFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onHideBaseLoader() {
        activity.hideLoader()
    }

    override fun onTokenChangeError(errorMessage: String?) {
        mContext?.let { activity.showDialog(it) }
    }

    override fun onShowBaseLoader() {
        activity.showLoader()
    }

    override fun onError(errorMessage: String) {
        activity.toastMessage(errorMessage)
    }

    override fun onSuccessUserAddressList(userAddressListResponse: UserAddressListResponse?) {

        if (userAddressListResponse!!.data.user_addresslist.size == 0) {
            tvAddressProfile.text = "N/A"
        } else {
            val lastIndex = userAddressListResponse.data.user_addresslist.size - 1
            val address = userAddressListResponse.data.user_addresslist[0].ship_address_location
            tvAddressProfile.text = address
        }
    }

    override fun onSuccessGetProfile(loginResponse: LoginResponse?) {
        if (loginResponse?.status.equals("success", ignoreCase = true)) {
            session.createRegistration(loginResponse?.data?.userDetail)
            session.setUserLoggedIn()
           //  session.authToken = loginResponse?.data?.userDetail!!.auth_token
            setData(loginResponse!!.data)
        }
    }

    override fun onSuccessNotificationList(notificationListModel: NotificationListModel?) {
    }

    override fun onSuccessGetCart(cartListResponse: CartListResponse?) {
    }

}