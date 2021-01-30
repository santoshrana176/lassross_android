package com.mindiii.lasross.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {


    private LasrossParentActivity activity;
    private Dialog mProgressBar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof LasrossParentActivity) {
            activity = (LasrossParentActivity) context;
        }
    }

    private LasrossParentActivity getBaseActivity() {
        return activity;
    }

    protected void hideKeyboard() {
        if (getBaseActivity().getCurrentFocus() == null) return;

        InputMethodManager inputMethodManager = (InputMethodManager) getBaseActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(getBaseActivity().getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }



    /*protected void setLoading(Boolean isLoading) {
        //first cancel progress bar
        if (mProgressBar != null && mProgressBar.isShowing()) {
            mProgressBar.cancel();
        }
        if (isLoading) {
            mProgressBar = CommonUtils.showLoadingDialog(getBaseActivity());
        }
    }*/
}
