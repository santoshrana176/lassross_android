package com.mindiii.lasross.module.orderdetail.fragment

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindiii.lasross.R
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.BaseKotlinFragment
import com.mindiii.lasross.base.ClickListener
import com.mindiii.lasross.module.address.model.AddAddressResponse
import com.mindiii.lasross.module.faq.FAQActivity
import com.mindiii.lasross.module.orderdetail.adapter.OrderDetailAdapter
import com.mindiii.lasross.module.orderdetail.model.OrderDetailModel
import com.mindiii.lasross.module.orderdetail.model.Product
import com.mindiii.lasross.module.orderdetail.model.TrackingStatu
import com.mindiii.lasross.module.orderdetail.presenter.OrderDetailPresenter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_order_details.*
import kotlinx.android.synthetic.main.reset_password_dialog_artboard_35.ivClose
import kotlinx.android.synthetic.main.write_review_dialog.*

class OrderDetailsFragment : BaseKotlinFragment(), View.OnClickListener, ApiCallback.OrderDetailCallback, ClickListener.OrderDetailsListner
{
    var rating: Float = 0.0f
    var desc: String? = ""
    lateinit var productId: String
    lateinit var status: String
    var adapterPosition: Int? = null
    private var param1: String? = null
    private var orderId: String? = null
    lateinit var tvFAQUpperText: TextView
    lateinit var orderDetailList: ArrayList<Product>
    lateinit var orderDetailAdapter: OrderDetailAdapter
    lateinit var trackingStatusList: ArrayList<TrackingStatu>
    private var mLastClickTime: Long = 0
    var activityFAQ: FAQActivity? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_order_details, container, false)
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val window = getActivity()!!.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(this.resources.getColor(R.color.home_header_bg1))
        orderDetailList = ArrayList()
        trackingStatusList = ArrayList()
        activityFAQ?.setOrderDetail()

        callOrderDetailApi()
        setOrderDetailAdapter()
        rlGotoDeliveryDetails.setOnClickListener(this)

        val textview = activity.findViewById(R.id.tvFAQUpperText) as TextView
        textview.text = resources.getString(R.string.order_detail)
        val imageview = activity.findViewById(R.id.ivMyCartOrder) as ImageView
        imageview.visibility = View.GONE
        val textViewCartCount = activity.findViewById(R.id.tvCartItemCountOrders) as TextView
        textViewCartCount.visibility = View.GONE

        Picasso.with(requireContext())
                .load(R.drawable.dress1)
                .resize(500, 650)
                .into(ivOrderDetail)

    }

    companion object {
        @JvmStatic
        fun newInstance(orderId: String): OrderDetailsFragment {
            val fragment = OrderDetailsFragment()
            val args = Bundle()
            fragment.setInstanceData(orderId)
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityFAQ = context as FAQActivity
    }

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
    // comment and rating reviews
    fun openReviewBox(productId: String, rating: Float, description: String, item_id: String) {
        val dialog = mContext?.let { Dialog(it) }//,android.R.style.Theme_Dialog);
        dialog!!.setContentView(R.layout.write_review_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.ivClose.setOnClickListener { dialog.dismiss() }
        if (rating != 0.0f) {
            dialog.myRatingBar1.rating = rating

            if (description.isEmpty()) {
                dialog.etReviews.text = getString(R.string.no_review_given).toEditable()
                dialog.etReviews.isEnabled = false
            } else if (!description.isEmpty()) {
                dialog.etReviews.text = description.toEditable()
                dialog.etReviews.isEnabled = false
            }
            dialog.myRatingBar1.setFocusable(false);
            dialog.tvSendReview.visibility = View.GONE
        } else {
            dialog.tvSendReview.visibility = View.VISIBLE
            dialog.myRatingBar1.setIsIndicator(false)
            dialog.myRatingBar1.setFocusable(true)
        }

        dialog.tvSendReview.setOnClickListener {
            val ratings = dialog.myRatingBar1.getRating()
            val description = dialog.etReviews.text.toString()
            if (ratings == 0.0f) {
                Toast.makeText(mContext, getString(R.string.rating), Toast.LENGTH_SHORT).show()

            } else {
                RatingReviewApiCall(productId, ratings, description, item_id)
                dialog.dismiss()
            }
        }
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window?.attributes)
        dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        lp.gravity = Gravity.TOP
        lp.windowAnimations = R.style.DialogAnimation
        dialog.window!!.attributes = lp
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun RatingReviewApiCall(productId: String, ratings: Float, description: String, item_id: String) {
        OrderDetailPresenter(mContext!!, this).callRatingReviewApi(productId, ratings, description, item_id)
    }

    // onclick
    override fun onClick(p0: View?) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return
        }
        mLastClickTime = SystemClock.elapsedRealtime()
        if (p0 != null) {
            when (p0.id) {
                R.id.rlGotoDeliveryDetails -> {
                    activity.addFragment(OrderTrackingDetailFragment.newInstance(trackingStatusList), true, R.id.profileFrame)
                }
            }
        }
    }

    private fun setInstanceData(orderId: String) {
        this.orderId = orderId
    }


    override fun onHideBaseLoader() {
        activity.hideLoader()
    }

    override fun onSuccessProductRating(addAddressResponse: AddAddressResponse) {
        activity.toastMessage(addAddressResponse.message)

        callOrderDetailApi()

    }

    override fun onTokenChangeError(errorMessage: String?) {
        mContext?.let { activity.showDialog(it) }
    }

    override fun onShowBaseLoader() {
        activity.showLoader()
    }

    override fun onError(errorMessage: String?) {
        activity.toastMessage(errorMessage.toString())
    }

    fun callOrderDetailApi() {
        OrderDetailPresenter(requireContext(), this).callOrderDetailApi(orderId.toString())
    }

    fun setOrderDetailAdapter() {
        orderDetailAdapter = OrderDetailAdapter(orderDetailList, requireContext(), this)
        rvOrderDetail.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvOrderDetail.adapter = orderDetailAdapter
    }

    override fun onSuccessOrderDetail(orderDetailModel: OrderDetailModel) {
        if (orderDetailModel != null) {
            orderDetailList.clear()
            val lastIndexOfTrackingList = orderDetailModel.orderDetail.tracking_status.size - 1
            status = orderDetailModel.orderDetail.tracking_status.get(lastIndexOfTrackingList).order_status
            for (i in 0..lastIndexOfTrackingList)
                trackingStatusList.add(orderDetailModel.orderDetail.tracking_status[i])

            val date = DateFormatChange(orderDetailModel.orderDetail.tracking_status[lastIndexOfTrackingList].created_on)
            tvDateOrderDetail.text = date

            val orderStatus = orderDetailModel.orderDetail.tracking_status[lastIndexOfTrackingList].order_status_title
            tvStatus.text = orderStatus
            /*if (status.equals("0")) {
                tvStatus.text = getString(R.string.ordered_and_approved)
            } else if (status.equals("1")) {
                tvStatus.text = getString(R.string.packed)
            } else if (status.equals("2")) {
                tvStatus.text = getString(R.string.shipped)
            } else if (status.equals("3")) {
                tvStatus.text = getString(R.string.delivered)
            }*/


            tvSubTotalOrderDetail.text = "€ " + activity.getTwoValueAfterDecimal(orderDetailModel.orderDetail.item_total)
            tvTaxOrderDetail.text = "€ " + activity.getTwoValueAfterDecimal(orderDetailModel.orderDetail.tax_amount)

            if (orderDetailModel.orderDetail.discount_amount == "0.00") {
                rlOrderDetailDiscount.visibility = View.GONE
                viewOrderDetailDiscount.visibility = View.GONE
            } else {
                rlOrderDetailDiscount.visibility = View.VISIBLE
                viewOrderDetailDiscount.visibility = View.VISIBLE
                tvOrderDetailDiscountCharges.text = "€ " + activity.getTwoValueAfterDecimal(orderDetailModel.orderDetail.discount_amount)
            }

            if (orderDetailModel.orderDetail.free_shipping.equals("Yes")) {
                tvShippingPrcOrderDetail.text = "FREE SHIPPING"
            } else {
                tvShippingPrcOrderDetail.text = "€ " + activity.getTwoValueAfterDecimal(orderDetailModel.orderDetail.shipping_price)
            }

            tvTotalOrderDetail.text = "€ " + activity.getTwoValueAfterDecimal(orderDetailModel.orderDetail.grand_total)

            tvOrderDetailDiscount.text = "Discount (" + activity.getTwoValueAfterDecimal(orderDetailModel.orderDetail.discount_percent) + "%)"
            tvOrderDetailTax.text = "Tax (" + activity.getTwoValueAfterDecimal(orderDetailModel.orderDetail.tax_percentage) + "%)"

            tvOrderId.text = orderDetailModel.orderDetail.order_number
            tvNameOrderDetail.text = orderDetailModel.orderDetail.order_address.order_bill_address_first_name
            tvAddressOrderDetail.text = orderDetailModel.orderDetail.order_address.order_bill_address_location
            tvMobileOrderDetail.text = orderDetailModel.orderDetail.order_address.order_bill_address_phone
            orderDetailList.addAll(orderDetailModel.orderDetail.products)
            orderDetailAdapter.notifyDataSetChanged()
        }
    }

    override fun onItemClick(productId: String?, rating: Float, description: String, AdapterPosition: Int, item_id: String?) {
        openReviewBox(productId.toString(), rating, description, item_id.toString())
        this.adapterPosition = AdapterPosition

    }

}