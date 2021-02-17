package com.mindiii.lasross.module.orderdetail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mindiii.lasross.R
import com.mindiii.lasross.base.ClickListener
import com.mindiii.lasross.module.faq.FAQActivity
import com.mindiii.lasross.module.orderdetail.model.Product
import com.squareup.picasso.Picasso

class OrderDetailAdapter(private val list: List<Product>, private val context: Context, val listener: ClickListener.OrderDetailsListner,val orderStatus: String) : RecyclerView.Adapter<OrderDetailAdapter.Holder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): Holder {
        val view: View
        val holder: Holder
        view = LayoutInflater.from(viewGroup.context).inflate(R.layout.order_detail_adapter_layout, viewGroup, false)
        holder = Holder(view)
        return holder
    }

    override fun onBindViewHolder(holder: Holder, i: Int) {
        var rating: Float = 0.0f
        var desc: String? = ""
        val itemList = list.get(i)
        holder.tvItemNameOrderDetail.setText(itemList.category_name)
        holder.tvItemCategoryOrderDetail.setText(itemList.product_name)


        if (itemList.sale_price == "0.00") {
            holder.tvItemTotalPriceOrderDetail.setText("€ " + (context as FAQActivity).getTwoValueAfterDecimal(itemList.regular_price))
        } else {

            holder.tvItemTotalPriceOrderDetail.setText("€ " + (context as FAQActivity).getTwoValueAfterDecimal(itemList.sale_price))
        }

        // holder.tvItemTotalPriceOrderDetail.setText("€                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             " + itemList.sale_price)
        Picasso.with(context)
                .load(itemList.productFeatureImage)
                .resize(500, 600)
                .into(holder.ivOrderDetail)

        itemList.rating?.let {
            holder.myRatingBar.rating = it.toFloat()
            holder.tvWriteReviewOrderDetail.text = "VIEW REVIEW"
            rating = it.toFloat()
        }
        itemList.description?.let {
            desc = it
        }

        holder.tvItemSizeOrderDetail.text = itemList.variants[0].value
        holder.productColor.text = " " + itemList.variants[1].value
        holder.productQuantity.text = " " + itemList.item_quantity

        holder.tvWriteReviewOrderDetail.setOnClickListener(View.OnClickListener {
            listener.onItemClick(itemList.productId, rating, desc, i, itemList.orderItemId)
        })

        if (orderStatus.equals("4")){
            holder.tvWriteReviewOrderDetail.visibility=View.VISIBLE
        }else{
            holder.tvWriteReviewOrderDetail.visibility=View.GONE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var ivOrderDetail: ImageView
        internal var tvItemNameOrderDetail: TextView
        internal var tvItemCategoryOrderDetail: TextView
        internal var tvItemSizeOrderDetail: TextView
        internal var tvWriteReviewOrderDetail: TextView
        internal var tvItemTotalPriceOrderDetail: TextView
        internal var productColor: TextView
        internal var productQuantity: TextView
        internal var myRatingBar: RatingBar

        init {
            ivOrderDetail = itemView.findViewById(R.id.ivOrderDetail)
            tvItemNameOrderDetail = itemView.findViewById(R.id.tvItemNameOrderDetail)
            tvItemCategoryOrderDetail = itemView.findViewById(R.id.tvItemCategoryOrderDetail)
            tvItemSizeOrderDetail = itemView.findViewById(R.id.tvItemSizeOrderDetail)
            tvWriteReviewOrderDetail = itemView.findViewById(R.id.tvWriteReviewOrderDetail)
            tvItemTotalPriceOrderDetail = itemView.findViewById(R.id.tvItemTotalPriceOrderDetail)
            productColor = itemView.findViewById(R.id.productColor)
            productQuantity = itemView.findViewById(R.id.productQuantity)
            myRatingBar = itemView.findViewById(R.id.myRatingBar)
        }
    }
}