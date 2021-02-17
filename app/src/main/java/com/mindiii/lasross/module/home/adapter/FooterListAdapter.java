package com.mindiii.lasross.module.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mindiii.lasross.R;
import com.mindiii.lasross.base.ClickListener;
import com.mindiii.lasross.helper.RoundRectCornerImageView;
import com.mindiii.lasross.module.home.model.Deal;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FooterListAdapter extends RecyclerView.Adapter<FooterListAdapter.Holder> {

    private List<Deal> dealList;
    private Context context;
    private ClickListener.HomeFooterClick homeFooterClick;

    public FooterListAdapter(List<Deal> dealList, Context context, ClickListener.HomeFooterClick homeFooterClick) {
        this.dealList = dealList;
        this.context = context;
        this.homeFooterClick = homeFooterClick;
    }

    @NonNull
    @Override
    public FooterListAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        FooterListAdapter.Holder holder;

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.footer_list_layout, viewGroup, false);
        holder = new FooterListAdapter.Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FooterListAdapter.Holder holder, int i) {

        Deal itemList = dealList.get(i);
        holder.tvdealTitle.setText(itemList.getDeal_title());
        holder.tvDealPrice.setText(itemList.getDeal_sub_title());
        holder.btnShopNow.setText("Shop Now");

        if (itemList.getDeal_image().isEmpty()) {
            holder.roundRectCornerImageView.setImageResource(R.drawable.dress2);
        } else {
            Glide.with(context).load(itemList.getDeal_image()).into(holder.roundRectCornerImageView);

        /*    Picasso.with(context)
                    .load(itemList.getDeal_image())
                    //.resize(800,500)
                    //.transform(new RoundedTransformation(20, 0))
                    .into(holder.roundRectCornerImageView);*/
        }
    }

    @Override
    public int getItemCount() {
        return dealList.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //ImageView imageView;
        RoundRectCornerImageView roundRectCornerImageView;
        TextView tvdealTitle, tvDealPrice;
        Button btnShopNow;
        RelativeLayout rvFullLayout;

        public Holder(@NonNull View itemView) {
            super(itemView);

            roundRectCornerImageView = itemView.findViewById(R.id.ivBgImage);
            tvdealTitle = itemView.findViewById(R.id.tvdealTitle);
            tvDealPrice = itemView.findViewById(R.id.tvDealPrice);
            btnShopNow = itemView.findViewById(R.id.btnShopNow);
            rvFullLayout = itemView.findViewById(R.id.rvFullLayout);
            rvFullLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            homeFooterClick.onClick(getAdapterPosition());
            //context.startActivity(new Intent(context, CoatsActivity.class));
        }
    }
}
