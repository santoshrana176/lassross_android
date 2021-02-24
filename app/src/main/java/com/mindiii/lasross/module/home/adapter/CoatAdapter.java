package com.mindiii.lasross.module.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mindiii.lasross.R;
import com.mindiii.lasross.base.ClickListener;
import com.mindiii.lasross.helper.RoundedTransformation;
import com.mindiii.lasross.module.home.adapter.FooterLoader;
import com.mindiii.lasross.module.home.model.ProductResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CoatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEWTYPE_ITEM = 1;
    private final int VIEWTYPE_LOADER = 2;
    private List<ProductResponse.DataBean.ProductListBean> productListBeans;
    private List<ProductResponse.DataBean.ProductListBean> globlemproductList;
    private ClickListener.WishListClickListener wishListClickListener;
    private Context context;
    private boolean showLoader;

    public CoatAdapter(List<ProductResponse.DataBean.ProductListBean> productListBeans, Context context, ClickListener.WishListClickListener wishListClickListener) {
        this.productListBeans = productListBeans;
        this.context = context;
        this.wishListClickListener = wishListClickListener;
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder myViewHolder, int i) {

        if (myViewHolder instanceof FooterLoader) {
            FooterLoader loaderViewHolder = (FooterLoader) myViewHolder;
            if (showLoader) {
                loaderViewHolder.mProgressBar.setVisibility(View.VISIBLE);
            } else {
                loaderViewHolder.mProgressBar.setVisibility(View.GONE);
            }
            return;
        }
        MyViewHolder myviewHolder = ((MyViewHolder) myViewHolder);

        ProductResponse.DataBean.ProductListBean itemList = productListBeans.get(i);
        if (itemList.getIs_wishlist().equals("0")) {
            myviewHolder.ivAddWishList.setImageResource(R.drawable.favourite_icon);
        } else {
            myviewHolder.ivAddWishList.setImageResource(R.drawable.ic_favorite_filled);
        }
        myviewHolder.tvItemNameVariety.setText(itemList.getCategory_name());
        myviewHolder.tvItemName.setText(itemList.getProduct_name());

        myviewHolder.tvItemPrice.setText(itemList.getCurrency_symbol() + " " + itemList.getRegular_price());
        Picasso.with(context)
                .load(itemList.getProduct_image())
           //     .resize(600, 600)
              //  .transform(new RoundedTransformation(50, 0))
                .into(myviewHolder.ivImage);

        myviewHolder.tvSellerPrice.setText(itemList.getCurrency_symbol() + " " + itemList.getSale_price());
        if (itemList.getSale_price().equals("0.00")) {
            myviewHolder.tvSellerPrice.setVisibility(View.GONE);
            myviewHolder.tvItemPrice.setVisibility(View.VISIBLE);
            myviewHolder.tvDiscountHome.setVisibility(View.GONE);
            myviewHolder.tvItemPrice.setPaintFlags(myviewHolder.tvItemPrice.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        } else {
            myviewHolder.tvSellerPrice.setVisibility(View.VISIBLE);
            myviewHolder.tvItemPrice.setVisibility(View.VISIBLE);
            myviewHolder.tvDiscountHome.setVisibility(View.VISIBLE);
            myviewHolder.tvDiscountHome.setText(itemList.getDiscount() + "%");
            myviewHolder.tvItemPrice.setTextColor(context.getResources().getColor(R.color.sort_by_color));
            myviewHolder.tvItemPrice.setPaintFlags(myviewHolder.tvItemPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

    }


    @androidx.annotation.NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case VIEWTYPE_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_list_layout, parent, false);
                return new MyViewHolder(view);

            case VIEWTYPE_LOADER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
                return new FooterLoader(view);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return productListBeans.size();
    }

    public void showLoading(boolean status) {
        showLoader = status;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == productListBeans.size() - 1) {
            return showLoader ? VIEWTYPE_LOADER : VIEWTYPE_ITEM;
        }
        return VIEWTYPE_ITEM;
    }

    public void setList(ArrayList<ProductResponse.DataBean.ProductListBean> productListBeanss) {
        productListBeans.clear();
        productListBeans.addAll(productListBeanss);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivImage, ivAddWishList;
        TextView tvItemNameVariety, tvItemName, tvItemPrice, tvSellerPrice, tvDiscountHome;
        RelativeLayout fullLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvItemNameVariety = itemView.findViewById(R.id.tvItemNameVariety);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvDiscountHome = itemView.findViewById(R.id.tvDiscountHome);
            tvItemPrice = itemView.findViewById(R.id.tvItemPrice);
            tvSellerPrice = itemView.findViewById(R.id.tvSellerPrice);
            ivAddWishList = itemView.findViewById(R.id.ivAddWishList);
            fullLayout = itemView.findViewById(R.id.fullLayout);
            ivAddWishList.setOnClickListener(this);
            fullLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.fullLayout:
                    wishListClickListener.onProductClick(getAdapterPosition());
                    break;
                case R.id.ivAddWishList:
                    wishListClickListener.onWishClick(getAdapterPosition());
                    break;
            }

        }
    }
}
