package com.mindiii.lasross.module.productDetail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mindiii.lasross.R;

public class DescriptionAdapter extends RecyclerView.Adapter<DescriptionAdapter.Holder> {

    private Context context;

    public DescriptionAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DescriptionAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        DescriptionAdapter.Holder holder;

        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.description_adapter_layout, viewGroup, false);
        holder = new DescriptionAdapter.Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DescriptionAdapter.Holder holder, int i) {
        //  DescriptionModel itemList = list.get(i);

        // if(itemList.getHeader().isEmpty())
        /*   holder.tvDescriptionHeading.setText(" ");
         *//*holder.tvDescriptionDetail.setText(itemList.getDetail());
            Picasso.with(context)
                    .load(R.drawable.dress2)
                    .resize(530, 400)
                    .into(holder.ivDescriptionImage);*//*
      //  else
       //     holder.tvDescriptionHeading.setText(itemList.getHeader());



       // if(itemList.getHeader().isEmpty())
         //   holder.tvDescriptionDetail.setText(" ");
      //  else
      //      holder.tvDescriptionDetail.setText(itemList.getDetail());


       // if (itemList.getPicURL().isEmpty()) {
            Picasso.with(context)
                    .load(R.drawable.dress2)
                    .resize(530, 400)
                    .into(holder.ivDescriptionImage);
            //holder.ivImage.setImageResource(R.drawable.dress2);
        }
     //   else {
            Picasso.with(context)
                    .load(itemList.getPicURL())
                    .resize(530, 400)
                    .into(holder.ivDescriptionImage);
        }*/
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Holder extends RecyclerView.ViewHolder {

        ImageView ivDescriptionImage;
        TextView tvDescriptionHeading, tvDescriptionDetail;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ivDescriptionImage = itemView.findViewById(R.id.ivDescriptionImage);
            tvDescriptionHeading = itemView.findViewById(R.id.tvDescriptionHeading);
            tvDescriptionDetail = itemView.findViewById(R.id.tvDescriptionDetail);
        }
    }
}
