package com.mindiii.lasross.module.payment;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.mindiii.lasross.R;
import com.mindiii.lasross.app.session.Session;
import com.mindiii.lasross.base.ApiCallback;
import com.mindiii.lasross.base.LasrossParentActivity;
import com.mindiii.lasross.module.subscription.SubscriptionResponse;
import com.mindiii.lasross.module.subscription.presenter.SubscriptionPresenter;
import com.mindiii.lasross.module.subscription.presenter.model.SubscribeResponse;
import com.mindiii.lasross.utils.CommonUtils;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Token;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddCardActivity extends LasrossParentActivity implements View.OnClickListener, ApiCallback.SubscriptionCallback {
    private ImageView ivUnChecked, ivChecked, btnBackToCard;
    private EditText etCardNumber, etName;
    private EditText etExpiryDate;
    private EditText etCVV;
    private TextView tvAddCard;
    private String error = "", subscriptionPlanId = "";
    private int  width;
    private int  month1=0;
    private int  year1=0;
    private long mLastClickTime = 0;
    private Session session;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card_activty_37);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.home_header_bg1));
        init();

        if (getIntent().getStringExtra("subscriptionScreen") != null) {
            tvAddCard.setText(R.string.pay);
        }

        if (getIntent().getStringExtra("subscriptionPlanId") != null) {
            subscriptionPlanId = getIntent().getStringExtra("subscriptionPlanId");
        }

        session = new Session(this);
        etExpiryDate.setOnClickListener(this);
        ivUnChecked.setOnClickListener(this);
        ivChecked.setOnClickListener(this);
        btnBackToCard.setOnClickListener(this);
        tvAddCard.setOnClickListener(this);
    }

    private void init() {
        etCardNumber = findViewById(R.id.etCardNumber);
        etExpiryDate = findViewById(R.id.etExpiryDate);
        etCVV = findViewById(R.id.etCVV);
        etName = findViewById(R.id.etName);
        ivUnChecked = findViewById(R.id.ivUnChecked);
        ivChecked = findViewById(R.id.ivChecked);
        btnBackToCard = findViewById(R.id.btnBackToCard);
        tvAddCard = findViewById(R.id.tvAddCard);
        tvAddCard.setEnabled(true);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        width = displaymetrics.widthPixels;
    }

    @Override
    public void onClick(View view) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        switch (view.getId()) {
            case R.id.etExpiryDate:
                    showMonthYearDialog(month1,year1);
                break;
            case R.id.ivUnChecked:
                if (ivUnChecked.getVisibility() == View.VISIBLE) {
                    ivChecked.setVisibility(View.VISIBLE);
                    ivUnChecked.setVisibility(View.GONE);
                }
                break;
            case R.id.ivChecked:
                if (ivChecked.getVisibility() == View.VISIBLE) {
                    ivChecked.setVisibility(View.GONE);
                    ivUnChecked.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tvAddCard:
                    String name = etName.getText().toString().trim();
                    String cardNumber = etCardNumber.getText().toString().trim();
                    String expiryDate = etExpiryDate.getText().toString().trim();
                    String cvv = etCVV.getText().toString().trim();

                     /*
                     Please enter valid name
                     Please enter expiry date
                     CVV number must be 3 digit long*/

                    if (name.isEmpty()) {
                        CommonUtils.showCustomAlert(this, getResources().getString(R.string.enter_card_holder_name));
                        return;
                    } else if (cardNumber.isEmpty()) {
                        CommonUtils.showCustomAlert(this, getResources().getString(R.string.enter_card_no));
                        return;
                    }  else if (cardNumber.length() < 16) {
                        CommonUtils.showCustomAlert(this, getResources().getString(R.string.card_no_sixteen_digit));
                        return;
                    }else  if (expiryDate.isEmpty()){
                        CommonUtils.showCustomAlert(this, getString(R.string.expiry_date_text));
                    }
                    else if (cvv.isEmpty()) {
                        CommonUtils.showCustomAlert(this, getResources().getString(R.string.enter_cvv));
                        return;
                    } else if (cvv.length() < 3) {
                        CommonUtils.showCustomAlert(this, getResources().getString(R.string.cvv_no_three_digit));
                        return;
                    } else {
                        if (CommonUtils.isNetworkAvailable(this)) {
                            showLoader();
                            AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();
                            asyncTaskRunner.execute();
                        }else {
                            showInternetAlertDialog(this);
                        }

                    }

                break;
            case R.id.btnBackToCard:
                finish();
                break;
        }
    }

    @Override
    public void onSuccessSubscription(SubscriptionResponse subscriptionResponse) {

    }

    @Override
    public void onSuccessSubscribe(SubscribeResponse response) {
        if (response.getStatus().equals("fail")) {
            toastMessage(response.getMessage());
        } else {
            showSubscribeDialog(this, getString(R.string.plan_subscribe_success));
        }
    }

    @Override
    public void onTokenChangeError(String errorMessage) {
        showDialog(this);
    }

    @Override
    public void onShowBaseLoader() {
        showLoader();
    }

    @Override
    public void onHideBaseLoader() {
        hideLoader();
    }

    @Override
    public void onError(String errorMessage) {

    }

    @SuppressLint("StaticFieldLeak")
    private void saveCreditCard(final String id) {
        new AsyncTask<Void, Void, Customer>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // showLoader();
            }

            @Override
            protected Customer doInBackground(Void... voids) {
                //sk_test_jVM872jPfk462GPwYDH7mr84
                Stripe.apiKey = getResources().getString(R.string.StripeKeyTest);
                Customer customer = null;
                try {
                    String stripeCustomerId = session.getRegistration().getStripe_customer_id(); //"cus_Fl6RC2yZA8vTna";  //session.getRegistration().getStripe_customer_id();
                    customer = Customer.retrieve(stripeCustomerId);
                    Map<String, Object> params = new HashMap<>();
                    params.put("source", id);
                    customer.getSources().create(params);

                } catch (StripeException e) {
                    e.printStackTrace();
                }
                return customer;
            }

            @Override
            protected void onPostExecute(Customer customer) {
                super.onPostExecute(customer);
                tvAddCard.setEnabled(false);
                hideLoader();
                if (customer != null) {
                    if (getIntent().getStringExtra("subscriptionScreen") != null) {
                        subscribe(subscriptionPlanId);
                    } else {
                        showAlertDialog(AddCardActivity.this, getString(R.string.card_add_success));
                    }
                } else {
                    toastMessage("Stripe Error");
                }
            }
        }.execute();
    }

    private void subscribe(String plan_id) {
        new SubscriptionPresenter(this, this).subscribePlanApi(plan_id);
    }

    void showAlertDialog(Context mContext, String msg) {
        final Session session = new Session(mContext);
        final Dialog dialog = new Dialog(mContext);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.session_expired_dialog);

        TextView tvTitleOfVal = dialog.findViewById(R.id.tvTitleOfVal);
        tvTitleOfVal.setText(msg);

        TextView tvOk = dialog.findViewById(R.id.tvOK);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
    }

    //*************show  MonthYear  Dialog *******************//
    private void showMonthYearDialog(int month11, int year11) {
        int year=0;
        int month=0;
        if (month1!=0){
            month=month11;
            year=year11;
        }else {
            year = Calendar.getInstance().get(Calendar.YEAR);
            month = Calendar.getInstance().get(Calendar.MONTH);
        }

        final Dialog yearDialog;
        yearDialog = new Dialog(this);
        yearDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        yearDialog.setContentView(R.layout.dialog_month_year);
        Objects.requireNonNull(yearDialog.getWindow()).setLayout((width * 10) / 11, WindowManager.LayoutParams.WRAP_CONTENT);
        Button set = yearDialog.findViewById(R.id.button1);
        Button cancel = yearDialog.findViewById(R.id.button2);
        set.setText(getString(R.string.set));
        final NumberPicker yearPicker = yearDialog.findViewById(R.id.numberPicker1);
        final NumberPicker monthPicker = yearDialog.findViewById(R.id.numberPicker2);
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setWrapSelectorWheel(false);
        monthPicker.setValue(month);
        monthPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        yearPicker.setMaxValue(2050);
        yearPicker.setMinValue(Calendar.getInstance().get(Calendar.YEAR));
        yearPicker.setWrapSelectorWheel(false);
        yearPicker.setValue(year);
        yearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        set.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                AddCardActivity.this.year1 = yearPicker.getValue();
                AddCardActivity.this.month1 = monthPicker.getValue();

                String lastTwoDigits = String.valueOf(AddCardActivity.this.year1).substring(2);
                if (AddCardActivity.this.month1 < 10) {
                    String twodigitMonth = "0" + monthPicker.getValue();
                    etExpiryDate.setText(twodigitMonth + "/" + lastTwoDigits);
                } else {
                    etExpiryDate.setText(monthPicker.getValue() + "/" + lastTwoDigits);
                }

                yearDialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yearDialog.dismiss();
            }
        });
        yearDialog.show();
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncTaskRunner extends AsyncTask<Void, Void, Token> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //  showLoader();
        }

        @SuppressLint("WrongThread")
        @Override
        protected Token doInBackground(Void... voids) {
            Stripe.apiKey = getResources().getString(R.string.StripeKeyTest);

            Token token = null;
            Map<String, Object> tokenParams = new HashMap<>();
            Map<String, Object> cardParams = new HashMap<>();
            cardParams.put("number", etCardNumber.getText().toString().trim());
            cardParams.put("exp_month", month1);
            cardParams.put("exp_year", year1);
            cardParams.put("cvc", etCVV.getText().toString().trim());
            tokenParams.put("card", cardParams);
            try {
                token = Token.create(tokenParams);
            } catch (Exception e) {
                hideLoader();
                error = e.getLocalizedMessage();
                Log.e("error", Objects.requireNonNull(e.getMessage()));
            }
            return token;
        }

        @Override
        protected void onPostExecute/*cardResponce.data!=null &&*/(Token token) {
            super.onPostExecute(token);
            // hideLoader();
            if (token != null) {
                saveCreditCard(token.getId());
            } else {
                CommonUtils.showCustomAlert(AddCardActivity.this, error);
                //toastMessage(error);
            }
        }
    }
}
