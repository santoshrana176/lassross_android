package com.mindiii.lasross.module.orderdetail.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.mindiii.lasross.R
import com.mindiii.lasross.base.BaseKotlinFragment
import com.mindiii.lasross.module.faq.FAQActivity
import com.mindiii.lasross.module.orderdetail.model.TrackingStatu
import kotlinx.android.synthetic.main.fragment_order_tracking_detail.*
private const val ARG_PARAM1 = "list"
private const val ARG_PARAM2 = "param2"

class OrderTrackingDetailFragment : BaseKotlinFragment() {
    private lateinit var list: ArrayList<TrackingStatu>
    private var param2: String? = null
    var orderDetailsFragment: FAQActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            list = it.getSerializable(ARG_PARAM1) as ArrayList<TrackingStatu>
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_order_tracking_detail, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderDetailsFragment!!.setTrackingDetail()

        val window = getActivity()!!.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = this.resources.getColor(R.color.home_header_bg1)

        val textview = activity.findViewById(R.id.tvFAQUpperText) as TextView
        textview.text = resources.getString(R.string.tracking_my_orders)
        val imageview = activity.findViewById(R.id.ivMyCartOrder) as ImageView
        imageview.visibility = View.GONE
        val textViewCartCount = activity.findViewById(R.id.tvCartItemCountOrders) as TextView
        textViewCartCount.visibility = View.GONE

        if (list.size == 1) {
            val date0 = getCurrentDateInSpecificFormat(list[0].created_on)
            lineOrderPlaced.visibility = View.GONE
            llOrderdPlaced.visibility = View.VISIBLE
            tvOrderPlacedDate.text = date0
        } else if (list.size == 2) {
            val date0 = getCurrentDateInSpecificFormat(list[0].created_on)
            val date1 = getCurrentDateInSpecificFormat(list[1].created_on)
            lineApproved.visibility = View.GONE
            llOrderdPlaced.visibility = View.VISIBLE
            llOrderdAndApproved.visibility = View.VISIBLE
            tvOrderPlacedDate.text = date0
            tvOrderApprovedDate.text = date1

        } else if (list.size == 3) {
            val date0 = getCurrentDateInSpecificFormat(list[0].created_on)
            val date1 = getCurrentDateInSpecificFormat(list[1].created_on)
            val date2 = getCurrentDateInSpecificFormat(list[2].created_on)

            linePacked.visibility = View.GONE
            llOrderdPlaced.visibility = View.VISIBLE
            llOrderdAndApproved.visibility = View.VISIBLE
            llPacked.visibility = View.VISIBLE
            tvOrderPlacedDate.text = date0
            tvOrderApprovedDate.text = date1
            tvPackedDate.text = date2

        } else if (list.size == 4) {
            val date0 = getCurrentDateInSpecificFormat(list[0].created_on)
            val date1 = getCurrentDateInSpecificFormat(list[1].created_on)
            val date2 = getCurrentDateInSpecificFormat(list[2].created_on)
            val date3 = getCurrentDateInSpecificFormat(list[3].created_on)

            lineShipped.visibility = View.GONE
            llOrderdPlaced.visibility = View.VISIBLE
            llOrderdAndApproved.visibility = View.VISIBLE
            llPacked.visibility = View.VISIBLE
            llShipped.visibility = View.VISIBLE
            tvOrderPlacedDate.text = date0
            tvOrderApprovedDate.text = date1
            tvPackedDate.text = date2
            tvShippedDate.text = date3

        } else if (list.size == 5) {
            val date0 = getCurrentDateInSpecificFormat(list[0].created_on)
            val date1 = getCurrentDateInSpecificFormat(list[1].created_on)
            val date2 = getCurrentDateInSpecificFormat(list[2].created_on)
            val date3 = getCurrentDateInSpecificFormat(list[3].created_on)
            val date4 = getCurrentDateInSpecificFormat(list[4].created_on)

            llOrderdPlaced.visibility = View.VISIBLE
            llOrderdAndApproved.visibility = View.VISIBLE
            llPacked.visibility = View.VISIBLE
            llShipped.visibility = View.VISIBLE
            llDeliverd.visibility = View.VISIBLE
            tvOrderPlacedDate.text = date0
            tvOrderApprovedDate.text = date1
            tvPackedDate.text = date2
            tvShippedDate.text = date3
            tvDeliveredDate.text = date4
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        orderDetailsFragment = context as FAQActivity
    }

    companion object {

        @JvmStatic
        fun newInstance(list: ArrayList<TrackingStatu>) =
                OrderTrackingDetailFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_PARAM1, list)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}