package com.mindiii.lasross.module.myorder.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.mindiii.lasross.R
import com.mindiii.lasross.base.ClickListener
import com.mindiii.lasross.module.myorder.adapter.MyOrdersAdapter.Holder
import com.mindiii.lasross.module.myorder.model.Order
import com.squareup.picasso.Picasso

class MyOrdersAdapter(private val list: MutableList<Order>,
                      private val context: Context, val myOrderListener: ClickListener.MyOrderListener)
    : RecyclerView.Adapter<Holder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): Holder {
        return Holder(LayoutInflater.from(viewGroup.context).inflate(R.layout.my_order_adapter_layout, viewGroup, false))
    }

    override fun onBindViewHolder(holder: Holder, i: Int) {

        val itemList = list.get(i)
        holder.tvItemNameVariety.setText(itemList.products.category_name)
        holder.tvItemName.setText(itemList.products.product_name)
        holder.tvItemDeliverDate.setText(itemList.order_current_status)

        itemList.products.AverageRating?.let {
            holder.myRatingBar.rating = it.toFloat()
        }

        itemList.products.productFeatureImage?.let {
            Picasso.with(context)
                    .load(it)
                    .resize(500, 600)
                    .into(holder.ivImage)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var ivImage: ImageView
        internal var tvItemNameVariety: TextView
        internal var tvItemName: TextView
        internal var tvItemDeliverDate: TextView
        internal var btnReview: Button
        internal var rlMyOrder: RelativeLayout
        internal var myRatingBar: RatingBar


        init {
            ivImage = itemView.findViewById(R.id.ivImage)
            tvItemNameVariety = itemView.findViewById(R.id.tvItemNameVariety)
            tvItemName = itemView.findViewById(R.id.tvItemName)
            tvItemDeliverDate = itemView.findViewById(R.id.tvItemDeliverDate)
            btnReview = itemView.findViewById(R.id.btnReview)
            rlMyOrder = itemView.findViewById(R.id.rlMyOrder)
            myRatingBar = itemView.findViewById(R.id.myRatingBar)
            rlMyOrder.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            myOrderListener.onItemClick(adapterPosition)
        }

    }
}