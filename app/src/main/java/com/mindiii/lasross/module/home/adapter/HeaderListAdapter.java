package com.mindiii.lasross.module.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mindiii.lasross.R;
import com.mindiii.lasross.helper.RoundRectCornerImageView;
import com.mindiii.lasross.module.home.interfc.HeaderInterface;
import com.mindiii.lasross.module.home.model.ProductResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HeaderListAdapter extends RecyclerView.Adapter<HeaderListAdapter.Holder> {

    public static final int TYPE_HEAD = 0;
    public static final int TYPE_LIST = 1;
    private List<ProductResponse.DataBean.ProductListBean> productListBeans;
    private List<ProductResponse.DataBean.ProductListBean> globlemproductList;
    private Context context;
    private String strBannerImage;
    private HeaderInterface headerInterface;
    private long mLastClickTime = 0;

    public HeaderListAdapter(String strBannerImage, List<ProductResponse.DataBean.ProductListBean> productListBeans, Context context, HeaderInterface headerInterface) {
        this.strBannerImage = strBannerImage;
        this.productListBeans = productListBeans;
        this.context = context;
        this.headerInterface = headerInterface;
        this.globlemproductList = new ArrayList<>();
        if (productListBeans != null) {
            globlemproductList.addAll(productListBeans);
        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase();
        productListBeans.clear();
        if (charText.length() == 0) {
            productListBeans.addAll(globlemproductList);
        } else {
            for (int i = 0; i < globlemproductList.size(); i++) {
                if (globlemproductList.get(i).getProduct_name().toLowerCase().startsWith(charText)) {
                    productListBeans.add(globlemproductList.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        Holder holder;
        if (i == TYPE_LIST) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.header_list_layout, viewGroup, false);
            holder = new Holder(view, i);
            return holder;
        } else if (i == TYPE_HEAD) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.header_main_layout, viewGroup, false);
            holder = new Holder(view, i);
            return holder;
        }
        return null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") final int i) {

        if (holder.view_type == TYPE_LIST) {
            ProductResponse.DataBean.ProductListBean itemList = productListBeans.get(i - 1);
            holder.tvItemNameVariety.setText(itemList.getCategory_name());
            holder.tvItemName.setText(itemList.getProduct_name());
            holder.tvItemPrice.setText(itemList.getCurrency_symbol() + " " + itemList.getRegular_price());
            if (itemList.getProduct_image().isEmpty()) {
                holder.roundRectCornerImageView.setImageResource(R.drawable.dress2);
            } else {
                Picasso.with(context)
                        .load(itemList.getProduct_image())
                        .into(holder.roundRectCornerImageView);
            }
            holder.tvSellerPrice.setText(itemList.getCurrency_symbol() + " " + itemList.getSale_price());
            if (itemList.getSale_price().equals("0.00")) {
                holder.tvSellerPrice.setVisibility(View.GONE);
                holder.tvItemPrice.setVisibility(View.VISIBLE);
                holder.tvDiscountHome.setVisibility(View.GONE);
            } else {
                holder.tvSellerPrice.setVisibility(View.VISIBLE);
                holder.tvItemPrice.setVisibility(View.VISIBLE);
                holder.tvDiscountHome.setVisibility(View.VISIBLE);
                holder.tvDiscountHome.setText(itemList.getDiscount() + "%");
                holder.tvItemPrice.setTextColor(context.getResources().getColor(R.color.sort_by_color));
                holder.tvItemPrice.setPaintFlags(holder.tvItemPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }

            if (itemList.getIs_wishlist().equals("0")) {
                holder.ivAddWishList.setImageResource(R.drawable.favourite_icon);
            } else {
                holder.ivAddWishList.setImageResource(R.drawable.ic_favorite_filled);
            }
        } else if (holder.view_type == TYPE_HEAD) {
            holder.tvUpTo50.setText("Up to 50% OFF !");
            holder.tvMessage.setText("Don't miss out on some very special items at extraordinary sale prices. For a limited time!");
            holder.btnPick.setText("Pick up a Bargain");
            if (strBannerImage == null) {
                holder.ivDiscount.setImageResource(R.drawable.dress2);
            } else {
                Picasso.with(context).load(strBannerImage).into(holder.ivDiscount);
            }
        }
    }

    @Override
    public int getItemCount() {
        return productListBeans.size() + 1;
    }

    public void setListData(List<ProductResponse.DataBean.ProductListBean> product_list) {
        productListBeans = product_list;
        if (productListBeans != null) {
            globlemproductList.addAll(productListBeans);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEAD;

        return TYPE_LIST;
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RoundRectCornerImageView roundRectCornerImageView, ivDiscount;
        TextView tvItemNameVariety, tvItemName, tvItemPrice, tvDiscountHome;
        RelativeLayout fullLayout, rvHeaderHomeTop;

        TextView tvUpTo50, tvMessage;
        Button btnPick;
        //ImageView ivDiscount;
        ImageView ivAddWishList;
        int view_type;
        private TextView tvSellerPrice;


        public Holder(@NonNull View itemView, int viewType) {
            super(itemView);

            if (viewType == TYPE_LIST) {
                tvItemNameVariety = itemView.findViewById(R.id.tvItemNameVariety);
                tvItemName = itemView.findViewById(R.id.tvItemName);
                tvDiscountHome = itemView.findViewById(R.id.tvDiscountHome);
                tvItemPrice = itemView.findViewById(R.id.tvItemPrice);
                fullLayout = itemView.findViewById(R.id.fullLayout);
                roundRectCornerImageView = itemView.findViewById(R.id.ivImage);
                tvSellerPrice = itemView.findViewById(R.id.tvSellerPrice);
                ivAddWishList = itemView.findViewById(R.id.ivAddWishList);
                ivAddWishList.setOnClickListener(this);
                fullLayout.setOnClickListener(this);
                view_type = 1;
            } else if (viewType == TYPE_HEAD) {
                tvUpTo50 = itemView.findViewById(R.id.tvUpTo50);
                tvMessage = itemView.findViewById(R.id.tvMessage);
                btnPick = itemView.findViewById(R.id.btnPick);
                ivDiscount = itemView.findViewById(R.id.ivDiscount);
                rvHeaderHomeTop = itemView.findViewById(R.id.rvHeaderHomeTop);
                rvHeaderHomeTop.setOnClickListener(this);
                view_type = 0;
            }
        }

        @Override
        public void onClick(View v) {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();

            switch (v.getId()) {

                case R.id.fullLayout:
                    headerInterface.onClickListener(getAdapterPosition() - 1);
                    break;
                case R.id.ivAddWishList:
                    headerInterface.OnAddWishListClickListener(getAdapterPosition() - 1);
                    break;
                case R.id.rvHeaderHomeTop:
                    headerInterface.onTopImageClick();
            }
        }
    }
}