package com.mindiii.lasross.module.home.adapter;

import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mindiii.lasross.R;

public class FooterLoader extends RecyclerView.ViewHolder {

    public ProgressBar mProgressBar;

    public FooterLoader(@NonNull View itemView) {
        super(itemView);
        mProgressBar = itemView.findViewById(R.id.progressbar);
    }
}