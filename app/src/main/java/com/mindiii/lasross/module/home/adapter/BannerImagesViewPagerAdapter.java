package com.mindiii.lasross.module.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
 import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.mindiii.lasross.R;
import com.mindiii.lasross.module.settings.model.BannerSilder;

import java.util.ArrayList;

public class BannerImagesViewPagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<BannerSilder> mBannerSilderList;
   private String basePath;
  public   BannerItemClickListner mBannerItemClickListner;
    public BannerImagesViewPagerAdapter(Context context, ArrayList<BannerSilder> mBannerSilderList, String basePath,BannerItemClickListner mBannerItemClickListner) {
        this.context = context;
        this.basePath = basePath;
        this.mBannerItemClickListner = mBannerItemClickListner;
          this.mBannerSilderList = mBannerSilderList;
    }

    @Override
    public int getCount() {
        return mBannerSilderList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //   return super.instantiateItem(container, position);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout, null);
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView offer_title = view.findViewById(R.id.offer_title);
        TextView offer_description = view.findViewById(R.id.offer_description);
        offer_title.setText(mBannerSilderList.get(position).getTitle());
        offer_description.setText(mBannerSilderList.get(position).getDescription());

        RelativeLayout banner_item = view.findViewById(R.id.banner_item);


        banner_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBannerItemClickListner.onItemClick();
            }
        });
        String url=basePath+"thumb/"+mBannerSilderList.get(position).getBanner_image();
        Glide.with(context).load(url).into(imageView);
        ViewPager vp = (ViewPager) container;
        vp.addView(view, position);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((RelativeLayout) object);
    }

 public    interface BannerItemClickListner{
        public void onItemClick();
    }
}
