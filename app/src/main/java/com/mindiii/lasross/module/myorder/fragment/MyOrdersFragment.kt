package com.mindiii.lasross.module.myorder.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.BaseKotlinFragment
import com.mindiii.lasross.base.ClickListener
import com.mindiii.lasross.module.myorder.adapter.MyOrdersAdapter
import com.mindiii.lasross.module.myorder.model.MyOrdersModel
import com.mindiii.lasross.module.myorder.model.Order
import com.mindiii.lasross.module.myorder.presenter.MyOrderPresenter
import com.mindiii.lasross.module.orderdetail.fragment.OrderDetailsFragment
import com.mindiii.lasross.utils.CommonUtils.toastMessage
import kotlinx.android.synthetic.main.fragment_my_order.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MyOrdersFragment : BaseKotlinFragment(), ApiCallback.MyOrderCallback {

    var myOrdersModelsList = mutableListOf<Order>()
    lateinit var myOrdersAdapter: MyOrdersAdapter
    private var param2: String? = null
    private lateinit var session: Session
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val window = getActivity()!!.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(this.resources.getColor(R.color.home_header_bg1))
        }
        session = Session(requireContext())
        setMyOrderAdapter()
        callMyOrderListApi(requireContext())

        //tvCartItemCountOrders
        val textViewCartCount = activity.findViewById(R.id.tvCartItemCountOrders) as TextView
        if (session.cartItemCount.equals("0", ignoreCase = true))
            textViewCartCount.visibility = View.GONE
        else
            textViewCartCount.visibility = View.VISIBLE
        textViewCartCount.setText(session.cartItemCount)
    }

    fun setMyOrderAdapter() {
        rcvMyOrder.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        myOrdersAdapter = MyOrdersAdapter(myOrdersModelsList, requireContext(), ClickListener.MyOrderListener {
            activity.addFragment(OrderDetailsFragment.newInstance(myOrdersModelsList.get(it).orderId), true, R.id.profileFrame)
        })
        rcvMyOrder.adapter = myOrdersAdapter
    }


    override fun onHideBaseLoader() {
        activity.hideLoader()
    }


    fun callMyOrderListApi(context: Context) {
        MyOrderPresenter(context, this).callMyOrderListApi()
    }

    override fun onSuccessMyOrderList(myOrdersModel: MyOrdersModel?) {
        myOrdersModelsList.clear()

        if (myOrdersModel != null) {
            myOrdersModelsList.addAll(myOrdersModel.orderList)

            if (myOrdersModelsList.size > 0) {
                tvNotFoundOrders.visibility = View.GONE
                rcvMyOrder.visibility = View.VISIBLE
                myOrdersAdapter.notifyDataSetChanged()
            } else {
                tvNotFoundOrders.visibility = View.VISIBLE
                rcvMyOrder.visibility = View.GONE
            }

        }
    }

    override fun onResume() {
        super.onResume()
        myOrdersModelsList.clear()
        callMyOrderListApi(requireContext())
    }

    override fun onTokenChangeError(errorMessage: String?) {
        activity.showDialog(requireContext())
    }

    override fun onShowBaseLoader() {
        activity.showLoader()
    }

    override fun onError(errorMessage: String?) {
        errorMessage?.let { toastMessage(context, errorMessage) }
    }

    companion object {
        @JvmStatic
        fun newInstance(from: String) =
                MyOrdersFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, from)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}