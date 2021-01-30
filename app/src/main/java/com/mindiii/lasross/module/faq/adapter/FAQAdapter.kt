package com.mindiii.lasross.module.faq.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mindiii.lasross.R
import com.mindiii.lasross.module.faq.adapter.FAQAdapter.Holder
import com.mindiii.lasross.module.faq.model.FAQModel

class FAQAdapter(private val list: List<FAQModel>, private val context: Context) : RecyclerView.Adapter<Holder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): Holder {
        val view: View
        val holder: Holder
        view = LayoutInflater.from(viewGroup.context).inflate(R.layout.faq_adapter_layout, viewGroup, false)
        holder = Holder(view)
        return holder
    }

    override fun onBindViewHolder(holder: Holder, i: Int) {

        val itemList = list[i]
        holder.tvQuestion.text = itemList.question
        holder.tvAnswer.text = itemList.answer
        holder.relativeLayout.setOnClickListener {
            if (holder.tvAnswer.visibility == View.VISIBLE) {
                holder.ivUpIcon.setImageResource(R.drawable.ic_down_arrow_ico)
                holder.tvAnswer.visibility = View.GONE
            } else {
                holder.ivUpIcon.setImageResource(R.drawable.ic_upper_arrow_ico)
                holder.tvAnswer.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int {

        return list.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var tvQuestion: TextView
        internal var tvAnswer: TextView
        internal var ivUpIcon: ImageView
        internal var relativeLayout: RelativeLayout

        init {

            tvQuestion = itemView.findViewById(R.id.tvQuestion)
            tvAnswer = itemView.findViewById(R.id.tvAnswer)
            ivUpIcon = itemView.findViewById(R.id.ivUpIcon)
            relativeLayout = itemView.findViewById(R.id.relativeLayout)

        }

    }
}