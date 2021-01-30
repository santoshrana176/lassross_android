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
import com.mindiii.lasross.base.ClickListener;
import com.mindiii.lasross.module.Filter.model.VarientListResponse;

import java.util.ArrayList;
import java.util.List;

public class VariantAdapter extends RecyclerView.Adapter<VariantAdapter.Holder> {

    private List<VarientListResponse.DataBean.VariantBean> list;
    private Context context;
    private String name;
    private ClickListener.FilterClickListener filterClickListener;
    private ArrayList<String> arraySizeList;

    public VariantAdapter(ArrayList<String> arraySizeList, ClickListener.FilterClickListener filterClickListener,
                          List<VarientListResponse.DataBean.VariantBean> list, Context context, String name) {
        this.list = list;
        this.context = context;
        this.name = name;
        this.filterClickListener = filterClickListener;
        this.arraySizeList = arraySizeList;
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
        if (name.equalsIgnoreCase(list.get(0).getVariant_name())) {
            holder.tvSizeFilter.setText(list.get(0).getVariant_value().get(i).getVariant_value());
        }
        if (name.equalsIgnoreCase(list.get(1).getVariant_name())) {
            holder.tvSizeFilter.setText(list.get(1).getVariant_value().get(i).getVariant_value());
        }

        if (arraySizeList.size() > 0) {
            if (name.equalsIgnoreCase(list.get(0).getVariant_name())) {
                if (arraySizeList.contains(list.get(0).getVariant_value().get(i).getVariantValueId())) {
                    holder.cbLarge.setChecked(true);
                    holder.tvSizeFilter.setTypeface(semi_bold);
                } else {
                    holder.cbLarge.setChecked(false);
                    holder.tvSizeFilter.setTypeface(regular);
                }
            } else if (name.equalsIgnoreCase(list.get(1).getVariant_name())) {
                if (arraySizeList.contains(list.get(1).getVariant_value().get(i).getVariantValueId())) {
                    holder.cbLarge.setChecked(true);
                    holder.tvSizeFilter.setTypeface(semi_bold);
                } else {
                    holder.cbLarge.setChecked(false);
                    holder.tvSizeFilter.setTypeface(regular);
                }
            }
        }
        holder.rvLarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.cbLarge.isChecked()) {
                    holder.cbLarge.setChecked(false);
                    holder.tvSizeFilter.setTypeface(regular);
                    //holder.cbLarge.setTypeface(regular);
                    if (name.equalsIgnoreCase(list.get(0).getVariant_name())) {
                        String valData = list.get(0).getVariant_value().get(i).getVariantValueId();
                        filterClickListener.onSizeRemoveClick(valData);
                    } else if (name.equalsIgnoreCase(list.get(1).getVariant_name())) {
                        String valData = list.get(1).getVariant_value().get(i).getVariantValueId();
                        filterClickListener.onColorRemoveClick(valData);
                    }
                } else {
                    holder.cbLarge.setChecked(true);
                    holder.tvSizeFilter.setTypeface(semi_bold);
                    if (name.equalsIgnoreCase(list.get(0).getVariant_name())) {
                        String valData = list.get(0).getVariant_value().get(i).getVariantValueId();
                        filterClickListener.onSizeClick(valData);
                    } else if (name.equalsIgnoreCase(list.get(1).getVariant_name())) {
                        String valData = list.get(1).getVariant_value().get(i).getVariantValueId();
                        filterClickListener.onColorClick(valData);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list.size() > 0) {
            if (name.equalsIgnoreCase(list.get(0).getVariant_name()))
                return list.get(0).getVariant_value().size();
            else
                return list.get(1).getVariant_value().size();
        } else {
            return -1;
        }
    }

    public void setDataVarient(String names) {
        name = names;
    }


    public class Holder extends RecyclerView.ViewHolder {

        RelativeLayout rvLarge;
        CheckBox cbLarge;
        TextView tvSizeFilter;

        public Holder(@NonNull View itemView) {
            super(itemView);
            rvLarge = itemView.findViewById(R.id.rvLarge);
            cbLarge = itemView.findViewById(R.id.cbLarge);
            tvSizeFilter = itemView.findViewById(R.id.tvSizeFilter);
        }
    }
}