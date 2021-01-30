package com.mindiii.lasross.module.cart.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mindiii.lasross.R
import com.mindiii.lasross.base.ClickListener
import com.mindiii.lasross.module.cart.model.Cart
import com.squareup.picasso.Picasso

class MyCartAdapter(private var cart: List<Cart>, var mContext: Context,
                    var cartItemClickListener: ClickListener.CartItemClickListener) : RecyclerView.Adapter<MyCartAdapter.MyHolder>() {

    private var mLastClickTime: Long = 0

    override fun getItemCount(): Int {
        return cart.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val (_, cart_item_quantity, _, category_name, _
                , _, _, currency_symbol, in_stock, _
                , _, product_image, product_name, _, regular_price, sale_price,
                _, _, variant) = cart[position]
        holder.tvItemNameVariety.text = category_name
        holder.tvQuantity.text = cart_item_quantity
        holder.tvItemName.text = product_name
        holder.tvItemSize.text = variant[0].variant_value.variant_value
        holder.tvColor.text = variant[1].variant_value.variant_value

        if (in_stock.equals("0")) {
            holder.tvOutOfStockMyCart.visibility = View.VISIBLE
            holder.tvMinus.isClickable = false
            holder.tvPlus.isClickable = false
        } else {
            //intOutOfStock = 0
            holder.tvOutOfStockMyCart.visibility = View.GONE
            holder.tvMinus.isClickable = true
            holder.tvPlus.isClickable = true
        }


        if (sale_price == "0.00") {
            holder.tvItemPrice.text = "$currency_symbol $regular_price"
        } else {
            holder.tvItemPrice.text = "$currency_symbol $sale_price"
        }

        if (product_image.isEmpty()) {
            Picasso.with(mContext)
                    .load(R.drawable.default_profile_img)
                    .into(holder.ivCartImage)
        } else {
            Picasso.with(mContext)
                    .load(product_image)
                    .into(holder.ivCartImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.my_cart_adapter_layout, parent, false)
        val holder: MyHolder
        holder = MyHolder(view)
        return holder
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var ivCartImage: ImageView = itemView.findViewById(R.id.ivCartImage)
        internal var ivDelete: ImageView = itemView.findViewById(R.id.ivDelete)
        internal var tvItemNameVariety: TextView = itemView.findViewById(R.id.tvItemNameVariety)
        internal var tvItemName: TextView = itemView.findViewById(R.id.tvItemName)
        internal var tvItemSize: TextView = itemView.findViewById(R.id.tvItemSize)
        internal var tvColor: TextView = itemView.findViewById(R.id.tvColor)
        internal var tvItemPrice: TextView = itemView.findViewById(R.id.tvItemPrice)
        var tvPlus: LinearLayout = itemView.findViewById(R.id.tvPlus)
        internal var tvQuantity: TextView = itemView.findViewById(R.id.tvQuantity)
        var tvMinus: LinearLayout = itemView.findViewById(R.id.tvMinus)
        var tvOutOfStockMyCart: TextView = itemView.findViewById(R.id.tvOutOfStockMyCart)

        init {
            tvPlus.setOnClickListener(this)
            tvMinus.setOnClickListener(this)
            ivDelete.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                return
            }
            mLastClickTime = SystemClock.elapsedRealtime()
            when (p0!!.id) {
                R.id.tvPlus -> {
                    cartItemClickListener.onItenIncreaseClick(adapterPosition)
                }
                R.id.tvMinus -> {
                    cartItemClickListener.onItenDecreaseClick(adapterPosition)
                }
                R.id.ivDelete -> {
                    cartItemClickListener.onItenDeleteClick(adapterPosition)
                }
            }
        }
    }
}