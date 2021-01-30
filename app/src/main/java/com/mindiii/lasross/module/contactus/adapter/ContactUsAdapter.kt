package com.mindiii.lasross.module.contactus.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.mindiii.lasross.R
import com.mindiii.lasross.module.contactus.model.ContactUsModel

class ContactUsAdapter(private val list: List<ContactUsModel>, private val context: Context) : RecyclerView.Adapter<ContactUsAdapter.Holder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): Holder {
        val view: View
        val holder: Holder
        view = LayoutInflater.from(viewGroup.context).inflate(R.layout.contact_us_adapter, viewGroup, false)
        holder = Holder(view)
        return holder
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: Holder, i: Int) {

        val itemList = list[i]
        holder.tvName.text = itemList.name
        holder.tvHeaderName.text = itemList.headerName

        if (holder.tvHeaderName.text.equals("Phone")) {
            holder.ivContactUs.setImageDrawable(context.getDrawable(R.drawable.ic_phone_call_ico))
        }

        if (list.size - 1 == i) {
            holder.viewLine.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvName: TextView
        var tvHeaderName: TextView
        var ivContactUs: ImageView
        var viewLine: View

        init {
            tvName = itemView.findViewById(R.id.tvName)
            tvHeaderName = itemView.findViewById(R.id.tvHeaderName)
            ivContactUs = itemView.findViewById(R.id.ivContactUs)
            viewLine = itemView.findViewById(R.id.viewLine)
        }
    }
}