package com.mindiii.lasross.module.productDetail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.mindiii.lasross.R
import com.mindiii.lasross.module.productDetail.model.VariantValue

class CustomeSpinnerAdapter(val context: Context,
                            var arrayList: ArrayList<VariantValue>/*,
                             var mSelectedItemListner:SelectedItem*/
) : BaseAdapter() {

    private val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        /*  var v: View? = null
          if (position === 0) {
              val tv = TextView(context)
              tv.visibility = View.GONE
              v = tv
          } else {
              v = super.getDropDownView(position, null, parent)
          }
          return v!!*/
        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.custom_spinner_item1, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        if (position != 0) {
            vh.label.visibility = View.VISIBLE
            vh.label.text = arrayList.get(position).variant_value

        } else
            vh.label.visibility = View.GONE
        return view
    }

    override fun getItem(position: Int): Any? {
        return arrayList[position];
    }

    override fun getCount(): Int {
        return arrayList.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return super.getDropDownView(position, convertView, parent)

    }

    private class ItemHolder(row: View?) {
        val label: TextView

        init {
            label = row?.findViewById(R.id.text1) as TextView
        }
    }

    interface SelectedItem {
        fun selectedItem(position1: String, name: String)
    }

}