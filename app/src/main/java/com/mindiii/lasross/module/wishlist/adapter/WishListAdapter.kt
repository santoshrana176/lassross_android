package com.mindiii.lasross.module.wishlist.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mindiii.lasross.R
import com.mindiii.lasross.base.ClickListener
import com.mindiii.lasross.module.wishlist.adapter.WishListAdapter.Holder
import com.mindiii.lasross.module.wishlist.model.UserWishlist
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.wish_list_adapter_layout.view.*

class WishListAdapter(private val list: List<UserWishlist>,
                      private val context: Context, val wishListListener: ClickListener.WishListListener)
    : RecyclerView.Adapter<Holder>() {

    private var mLastClickTime: Long = 0

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): Holder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.wish_list_adapter_layout, viewGroup, false)
        val holder: Holder
        holder = Holder(view)
        return holder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, i: Int) {

        val itemList = list[i]
        holder.tvItemNameVariety.text = itemList.category_name
        holder.tvItemName.text = itemList.product_name
        holder.tvItemPrice.text = itemList.currency_symbol + " " + itemList.regular_price
      //  holder.tvMoveToBag.text = context.getString(R.string.move_to_bag)
        holder.tvSellerPrice.setText(itemList.currency_symbol + " " + itemList.sale_price)
        itemList.AverageRating?.let {
            holder.myRatingBar2.rating = it.toFloat()
        }


        Picasso.with(context)
                .load(itemList.product_image)
                //.resize(500, 600)
                .into(holder.ivImage)

        if (itemList.sale_price.equals("0") || itemList.sale_price.equals("0.00")) {
            holder.tvSellerPrice.setVisibility(View.GONE)
            holder.tvItemPrice.visibility = View.VISIBLE
                holder.tvItemPrice.textSize=context.resources.getDimension(R.dimen._7sdp)
        } else {
            holder.tvSellerPrice.setVisibility(View.VISIBLE)
            holder.tvItemPrice.textSize=context.resources.getDimension(R.dimen._5sdp)
            holder.tvItemPrice.visibility = View.VISIBLE
            holder.tvItemPrice.setTextColor(context.resources.getColor(R.color.sort_by_color))
            holder.tvItemPrice.paintFlags = holder.tvItemPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var ivImage: ImageView = itemView.findViewById(R.id.ivImage)
        internal var myRatingBar2: RatingBar = itemView.findViewById(R.id.myRatingBar2)
        internal var tvItemNameVariety: TextView = itemView.findViewById(R.id.tvItemNameVariety)
        internal var tvItemName: TextView = itemView.findViewById(R.id.tvItemName)
        internal var tvItemPrice: TextView = itemView.findViewById(R.id.tvItemPrice)
      //  internal var tvMoveToBag: TextView = itemView.findViewById(R.id.tvMoveToBag)
        internal var tvSellerPrice: TextView = itemView.findViewById(R.id.tvSellerPrice)
        internal var rlGoingToUserDetail: RelativeLayout = itemView.findViewById(R.id.rlGoingToUserDetail)

        init {

            itemView.ivDeleteWishList.setOnClickListener(this)
            itemView.rlGoingToUserDetail.setOnClickListener(this)
          //  itemView.tvMoveToBag.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return
            }
            mLastClickTime = SystemClock.elapsedRealtime()
            when (p0!!.id) {
                R.id.rlGoingToUserDetail -> {
                    wishListListener.onItemClick(adapterPosition)
                }
                R.id.ivDeleteWishList -> wishListListener.onDelete(adapterPosition)
             //   R.id.tvMoveToBag -> wishListListener.onAddToCartClick(adapterPosition)
            }
        }
    }
}

