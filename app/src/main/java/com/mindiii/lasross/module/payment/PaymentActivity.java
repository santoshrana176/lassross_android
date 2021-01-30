package com.mindiii.lasross.module.payment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mindiii.lasross.R;
import com.mindiii.lasross.base.ApiCallback;
import com.mindiii.lasross.base.LasrossParentActivity;
import com.mindiii.lasross.module.home.HomeActivity;
import com.mindiii.lasross.module.payment.model.FinalPaymentResponse;
import com.mindiii.lasross.module.payment.presenter.MyCArdPaymentPresenter;
import com.mindiii.lasross.utils.CommonUtils;

public class PaymentActivity extends LasrossParentActivity implements View.OnClickListener, ApiCallback.PaymentCallBack {

    private Button btnPaymentContinue, btnClearOrderPayment;
    private LinearLayout rlCreditCard, rlCod;
    private ImageView tvPaymentBack;
    private CheckBox radioCheckBtn, radioCodBtn;
    private long mLastClickTime = 0;
    private String totalAmount = "";
    private String addressID = "";
    private String payment_mode = "";
    private LinearLayout llChoosePaymentType, llBottomLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(this.getResources().getColor(R.color.home_header_bg1));
        }

        if (getIntent().getStringExtra("totalAmt") != null) {
            totalAmount = getIntent().getStringExtra("totalAmt");
            addressID = getIntent().getStringExtra("addressID");
        }

        init();
        btnPaymentContinue.setOnClickListener(this);
        btnClearOrderPayment.setOnClickListener(this);
        tvPaymentBack.setOnClickListener(this);
        rlCreditCard.setOnClickListener(this);
        rlCod.setOnClickListener(this);
    }

    private void init() {
        btnPaymentContinue = findViewById(R.id.btnPaymentContinue);
        btnClearOrderPayment = findViewById(R.id.btnClearOrderPayment);
        rlCod = findViewById(R.id.rlCod);
        rlCreditCard = findViewById(R.id.rlCreditCard);
        tvPaymentBack = findViewById(R.id.tvPaymentBack);
        radioCheckBtn = findViewById(R.id.radioCheckBtn);
        radioCodBtn = findViewById(R.id.radioCodBtn);
        llChoosePaymentType = findViewById(R.id.llChoosePaymentType);
        llBottomLayout = findViewById(R.id.llBottomLayout);

        if (CommonUtils.isNetworkAvailable(this)) {
            llChoosePaymentType.setVisibility(View.VISIBLE);
            llBottomLayout.setVisibility(View.VISIBLE);
        } else {
            llChoosePaymentType.setVisibility(View.GONE);
            llBottomLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPaymentContinue:
                if (CommonUtils.isNetworkAvailable(this)) {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();

                    if (radioCheckBtn.isChecked()) {
                        startActivity(new Intent(this, MyCardActivity.class)
                                .putExtra("cardSelection", "CardSelected")
                                .putExtra("from", "PaymentActivity")
                                .putExtra("addressID", addressID)
                                .putExtra("totalAmount", totalAmount)
                                .putExtra("payment_mode", payment_mode));
                        // finish();
                    } else if (radioCodBtn.isChecked()) {
                        paymentApi("COD", "", "", addressID);
                    } else {
                        toastMessage(getResources().getString(R.string.select_message));
                    }
                } else {
                    showInternetAlertDialog(this);
                }
                break;

            case R.id.btnClearOrderPayment:
                if (CommonUtils.isNetworkAvailable(this)) {
                    startActivity(new Intent(this, HomeActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                } else {
                    showInternetAlertDialog(this);
                }
                break;

            case R.id.tvPaymentBack:
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                finish();
                break;

            case R.id.rlCreditCard:
                radioCheckBtn.setChecked(true);
                radioCodBtn.setChecked(false);
                payment_mode = "Credit Card";
                break;

            case R.id.rlCod:
                radioCheckBtn.setChecked(false);
                radioCodBtn.setChecked(true);
                payment_mode = "COD";
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onSuccessPayment(FinalPaymentResponse finalPaymentResponse) {
        String orderNumber = finalPaymentResponse.getData().getOrder_number();
        startActivity(new Intent(this, PaymentConfirmActivity.class)
                .putExtra("totalAmount", totalAmount)
                .putExtra("orderNumber", orderNumber));
        finish();

    }

    @Override
    public void onTokenChangeError(String error) {
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
        toastMessage(errorMessage);
    }

    private void paymentApi(String payment_mode, String source_type, String cardId, String shipping_id) {
        new MyCArdPaymentPresenter(this, this)
                .finalPaymentApi(payment_mode, source_type, cardId, shipping_id);
    }
}
