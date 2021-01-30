package com.mindiii.lasross.module.productDetail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.mindiii.lasross.R
import com.mindiii.lasross.module.productDetail.model.GalleryImage
import com.squareup.picasso.Picasso

class ViewPagerAdapter(private var galleryImage: List<GalleryImage>, var mContext: Context, var tag: Any? = null) : PagerAdapter() {

    override fun getCount(): Int {
        return galleryImage.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(R.layout.swipe_layout_1, container, false)

        val img = view!!.findViewById<ImageView>(R.id.productDetailImage)
        if (galleryImage[position].product_gallery_image.isEmpty()) {
            Picasso.with(mContext)
                    .load(R.drawable.dress2)
                    //.resize(0, img.height)
                    .into(img)
            //img.setImageResource(R.drawable.dress2);
        } else {
            Picasso.with(mContext)
                    .load(galleryImage[position].product_gallery_image)
                    //.resize(0, img.height)
                    .into(img)
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}