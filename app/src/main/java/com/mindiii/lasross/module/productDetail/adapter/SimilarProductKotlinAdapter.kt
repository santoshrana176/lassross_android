package com.mindiii.lasross.module.productDetail.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mindiii.lasross.R
import com.mindiii.lasross.base.ClickListener
import com.mindiii.lasross.helper.RoundedTransformation
import com.mindiii.lasross.module.productDetail.adapter.SimilarProductKotlinAdapter.Holder
import com.mindiii.lasross.module.productDetail.model.SimilarProduct
import com.squareup.picasso.Picasso


class SimilarProductKotlinAdapter(private var productList: List<SimilarProduct>, var mContext: Context, var similarProductClickListener: ClickListener.SimilarProductClickListener) : RecyclerView.Adapter<Holder>()
{

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): Holder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.realted_product_adapter_layout, viewGroup, false)
        val holder: Holder

        holder = Holder(view)
        return holder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, i: Int) {
        val (_, category_name, _, _, _, currency_symbol, _, is_wishlist, _, product_image, product_name, _, regular_price, sale_price) = productList[i]
        holder.tvItemNameVariety.text = category_name
        holder.tvItemName.text = product_name
        holder.tvItemSalePricePrice.text = "$currency_symbol $sale_price"
        holder.tvItemPrice.text = "$currency_symbol $regular_price"
        if (sale_price == "0.00") {
            holder.tvItemSalePricePrice.visibility = View.GONE
            holder.tvItemPrice.visibility = View.VISIBLE
            holder.tvItemPrice.paintFlags = holder.tvItemPrice.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

        } else {
            holder.tvItemPrice.visibility = View.VISIBLE
            holder.tvItemSalePricePrice.visibility = View.VISIBLE
            holder.tvItemPrice.setTextColor(mContext.resources.getColor(R.color.sort_by_color))
            holder.tvItemPrice.paintFlags = holder.tvItemPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }

        if (product_image.isEmpty()) {
            Picasso.with(mContext)
                    .load(R.drawable.default_profile_img)
                    .resize(500, 500)
                    .transform(RoundedTransformation(50, 0))
                    .into(holder.ivImage)
        } else {
            Picasso.with(mContext)
                    .load(product_image)
                    .resize(500, 500)
                    .transform(RoundedTransformation(50, 0))
                    .into(holder.ivImage)
        }

        if (is_wishlist == "0") {
            holder.ivSimilarProductLike.setImageResource(R.drawable.favourite_icon)
        } else {
            holder.ivSimilarProductLike.setImageResource(R.drawable.ic_favorite_filled)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var ivImage: ImageView
        internal var tvItemNameVariety: TextView
        internal var tvItemName: TextView
        internal var tvItemPrice: TextView
        internal var tvItemSalePricePrice: TextView
        private var fullLayout: LinearLayout
        internal var ivSimilarProductLike: ImageView


        init {
            ivImage = itemView.findViewById(R.id.ivImage)
            tvItemNameVariety = itemView.findViewById(R.id.tvItemNameVariety)
            tvItemName = itemView.findViewById(R.id.tvItemName)
            tvItemPrice = itemView.findViewById(R.id.tvItemPrice)
            tvItemSalePricePrice = itemView.findViewById(R.id.tvItemSalePricePrice)
            fullLayout = itemView.findViewById(R.id.fullLayout)
            ivSimilarProductLike = itemView.findViewById(R.id.ivSimilarProductLike)
            ivSimilarProductLike.setOnClickListener(this)
            fullLayout.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            when (p0!!.id) {
                R.id.fullLayout -> {
                    similarProductClickListener.onProfileDetail(adapterPosition)
                }

                R.id.ivSimilarProductLike -> {
                    similarProductClickListener.onClickToWish(adapterPosition)
                }
            }
        }

    }

}