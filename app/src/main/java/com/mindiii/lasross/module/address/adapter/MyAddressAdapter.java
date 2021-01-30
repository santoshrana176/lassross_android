package com.mindiii.lasross.module.address.adapter;

import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mindiii.lasross.R;
import com.mindiii.lasross.base.ClickListener;
import com.mindiii.lasross.module.address.model.UserAddressListResponse;

import java.util.ArrayList;

public class MyAddressAdapter extends RecyclerView.Adapter<MyAddressAdapter.Holder> {

    private ArrayList<UserAddressListResponse.DataBean.UserAddresslistBean> list;
    private ClickListener.MyAddressListener myAddressListener;
    private Context context;
    private long mLastClickTime = 0;
    private String checkValue;
    private int selectedPosition = -1;

    public MyAddressAdapter(String checkValue, ArrayList<UserAddressListResponse.DataBean.UserAddresslistBean> list, Context context, ClickListener.MyAddressListener myAddressListener) {
        this.list = list;
        this.context = context;
        this.myAddressListener = myAddressListener;
        this.checkValue = checkValue;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        Holder holder;
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_address_adapter_layout, viewGroup, false);
        holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int i) {
        UserAddressListResponse.DataBean.UserAddresslistBean itemList = list.get(i);
        holder.tvName.setText(itemList.getShip_address_company_name());
        holder.tvAddress.setText(itemList.getShip_address_location());
        holder.tvNumber.setText(itemList.getBill_address_phone());
        holder.cbSelectAddress.setChecked(i == selectedPosition);

        if (checkValue.equals("CheckFound")) {
            holder.cbSelectAddress.setVisibility(View.VISIBLE);
            holder.ivDeleteAddress.setVisibility(View.GONE);
            holder.ivUpdateAddress.setVisibility(View.GONE);
        } else {
            holder.cbSelectAddress.setVisibility(View.GONE);
            holder.ivDeleteAddress.setVisibility(View.VISIBLE);
            holder.ivUpdateAddress.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName, tvAddress, tvNumber;
        private CheckBox cbSelectAddress;
        private ImageView ivDeleteAddress, ivUpdateAddress;
        private RelativeLayout relativeLayout;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvNumber = itemView.findViewById(R.id.tvNumber);

            ivDeleteAddress = itemView.findViewById(R.id.ivDeleteAddress);
            ivUpdateAddress = itemView.findViewById(R.id.ivUpdateAddress);

            cbSelectAddress = itemView.findViewById(R.id.cbSelectAddress);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);

            ivDeleteAddress.setOnClickListener(this);
            ivUpdateAddress.setOnClickListener(this);
            relativeLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.ivDeleteAddress:
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    myAddressListener.onItemDelete(getAdapterPosition());
                    break;
                case R.id.ivUpdateAddress:
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    myAddressListener.onItemUpdate(getAdapterPosition());
                    break;
                case R.id.relativeLayout:
                    selectedPosition = getAdapterPosition();
                    myAddressListener.onCheckBoxClick(selectedPosition);
                    notifyDataSetChanged();
                    break;

            }
        }
    }
}