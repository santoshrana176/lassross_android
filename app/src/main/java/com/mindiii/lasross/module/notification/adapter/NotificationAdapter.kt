package com.mindiii.lasross.module.notification.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mindiii.lasross.R
import com.mindiii.lasross.base.ClickListener
import com.mindiii.lasross.base.LasrossParentKotlinActivity
import com.mindiii.lasross.module.notification.adapter.NotificationAdapter.Holder
import com.mindiii.lasross.module.notification.model.Data
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.notification_adapter_layout.view.*

class NotificationAdapter(private val list: List<Data>,
                          private val context: LasrossParentKotlinActivity,
                          private val notificationListener: ClickListener.NotificationListener)
    : RecyclerView.Adapter<Holder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): Holder {
        val view: View
        val holder: Holder
        view = LayoutInflater.from(viewGroup.context).inflate(R.layout.notification_adapter_layout, viewGroup, false)
        holder = Holder(view)
        return holder
    }

    override fun onBindViewHolder(holder: Holder, i: Int) {
        val itemList = list[i]
        holder.tvItemHeaderNotification.text = itemList.notification_title

        val str = context.getDayDifference(itemList.created_on, itemList.current_time)
        holder.tvTimeNotification.text = str

        if (itemList.is_read.equals("0"))
            holder.linearLayout.setBackgroundResource(R.color.notification_unread)
        else
            holder.linearLayout.setBackgroundResource(R.color.white)


        holder.tvItemDetailNotification.text = itemList.notification_message

        if (itemList.product_image.isNotEmpty()) {

            Picasso.with(context)
                    .load(itemList.product_image)
                    .into(holder.ivNotification)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var tvItemHeaderNotification = itemView.tvItemHeaderNotification
        internal var tvTimeNotification = itemView.tvTimeNotification
        internal var tvItemDetailNotification = itemView.tvItemDetailNotification
        internal var ivNotification = itemView.ivNotification
        internal var linearLayout = itemView.linearLayout

        var img = itemView.ivNotification
        internal var rlNotification = itemView.rlNotification.setOnClickListener {
            notificationListener.onItemClick(adapterPosition)
        }


    }
}