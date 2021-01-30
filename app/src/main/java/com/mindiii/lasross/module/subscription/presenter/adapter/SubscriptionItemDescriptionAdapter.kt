package com.mindiii.lasross.module.subscription.presenter.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mindiii.lasross.R
import kotlinx.android.synthetic.main.subscription_item_description_adapter_layout.view.*

class SubscriptionItemDescriptionAdapter(private val list: List<String>,
                                         private val context: Context)
    : RecyclerView.Adapter<SubscriptionItemDescriptionAdapter.Holder>() {

    private var mLastClickTime: Long = 0

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): Holder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.subscription_item_description_adapter_layout, viewGroup, false)
        val holder: Holder
        holder = Holder(view)
        return holder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, i: Int) {
        val itemList = list[i]
        holder.tvItemDescriptionSubscription.text = itemList
        if (itemList.equals(""))
            holder.tvItemDescriptionSubscription.visibility = View.GONE
        else
            holder.tvItemDescriptionSubscription.visibility = View.VISIBLE
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var tvItemDescriptionSubscription: TextView = itemView.tvItemDescriptionSubscription
    }
}