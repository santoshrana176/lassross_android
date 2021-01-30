package com.mindiii.lasross.module.Filter.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mindiii.lasross.R;
import com.mindiii.lasross.interfc.ICheckChangeListener;
import com.mindiii.lasross.module.Filter.model.VarientListResponse;

import java.util.List;

public class FilterSizeAdapter extends RecyclerView.Adapter<FilterSizeAdapter.Holder> {

    private List<VarientListResponse.DataBean.VariantBean.VariantValueBean> list;
    private Context context;

    public FilterSizeAdapter(List<VarientListResponse.DataBean.VariantBean.VariantValueBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        Holder holder;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.filter_size_layout, viewGroup, false);
        holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int i) {

        final Typeface semi_bold = ResourcesCompat.getFont(context, R.font.ibmplexsans_semibold);
        final Typeface regular = ResourcesCompat.getFont(context, R.font.ibmplexsans_regular);

        final VarientListResponse.DataBean.VariantBean.VariantValueBean itemList = list.get(i);
        holder.cbLarge.setText(itemList.getVariant_value());
        //holder.tvLargeItemNo.setText(itemList.getTotalItem());

        holder.cbLarge.setClickable(false);
        holder.cbLarge.setEnabled(false);

       /* if (list.get(i).isTicked()) {
            holder.cbLarge.setChecked(true);
            holder.cbLarge.setTypeface(semi_bold);
        } else {
            holder.cbLarge.setChecked(false);
            holder.cbLarge.setTypeface(regular);
        }*/



        /*holder.rvLarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(i).isTicked()) {
                    list.get(i).setTicked(false);
                }
                else list.get(i).setTicked(true);

                notifyDataSetChanged();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RelativeLayout rvLarge;
        CheckBox cbLarge;
        TextView tvLargeItemNo;
        private ICheckChangeListener iCheckChangeListener;

        public Holder(@NonNull View itemView) {
            super(itemView);
            rvLarge = itemView.findViewById(R.id.rvLarge);
            cbLarge = itemView.findViewById(R.id.cbLarge);
            tvLargeItemNo = itemView.findViewById(R.id.tvLargeItemNo);

        }

        @Override
        public void onClick(View view) {

            VarientListResponse.DataBean.VariantBean.VariantValueBean filterSizeModel = list.get(getAdapterPosition());

            switch (view.getId()) {
                case R.id.rvLarge:

                   /* for(int i = 0; i<list.size(); i++){
                        list.get(i).setTicked(false);
                    }
                    filterSizeModel.setTicked(true);*/

                    notifyDataSetChanged();
                    /*if (list.get(i).isTicked()) {
                        list.get(i).setTicked(false);
                    }
                    else list.get(i).setTicked(true);

                    notifyDataSetChanged();*/
                    break;
            }

        }
    }
}
