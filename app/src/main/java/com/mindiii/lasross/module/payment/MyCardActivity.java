package com.mindiii.lasross.module.payment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.mindiii.lasross.BuildConfig;
import com.mindiii.lasross.R;
import com.mindiii.lasross.app.session.Session;
import com.mindiii.lasross.base.ApiCallback;
import com.mindiii.lasross.base.ClickListener.CardClickListener;
import com.mindiii.lasross.base.LasrossParentActivity;
import com.mindiii.lasross.module.home.HomeActivity;
import com.mindiii.lasross.module.payment.adapter.CardAdapter;
import com.mindiii.lasross.module.payment.model.FinalPaymentResponse;
import com.mindiii.lasross.module.payment.model.StripeSaveCardResponce;
import com.mindiii.lasross.module.payment.presenter.MyCArdPaymentPresenter;
import com.mindiii.lasross.utils.CommonUtils;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.ExternalAccount;
import com.stripe.model.ExternalAccountCollection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCardActivity extends LasrossParentActivity implements View.OnClickListener, ApiCallback.PaymentCallBack {
    private RecyclerView cardsList;
    private ImageView tvCartBack;
    private TextView tvSaveCard;
    private TextView tvMyCart;
    private TextView amount;
    private TextView tvNotFoundCard;
    private Button btnAddNewCard, btnUpdateCard, btnClearOrderMyCards;
    private StripeSaveCardResponce cardResponce;
    private CardAdapter cardAdapter;
    private String source_type = "";
    private String shipping_id = "";
    private String payment_mode = "";
    private String cardId = "";
    private String addressID = "";
    private long mLastClickTime = 0;
    private Session session;
    private int index = -1;
    private String cardSelection = "";
    private String from = "";
    private String totalAmount = "";
    //private String orderNumber="";
    private RelativeLayout rlt_totalAmount;
    private LinearLayout llMiddleLayoutMyCards, llBottomLayout;
    private String orderNumber;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_card);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.home_header_bg1));
        session = new Session(this);

        if (getIntent().getStringExtra("cardSelection") != null) {
            cardSelection = getIntent().getStringExtra("cardSelection");
        }
        if (getIntent().getStringExtra("from") != null) {
            from = getIntent().getStringExtra("from");
        }
        if (getIntent().getStringExtra("addressID") != null) {
            totalAmount = getIntent().getStringExtra("totalAmount");
            shipping_id = getIntent().getStringExtra("addressID");
            payment_mode = getIntent().getStringExtra("payment_mode");
        }
        init();

        if ("PaymentActivity".equals(from)) {
            rlt_totalAmount.setVisibility(View.VISIBLE);
            amount.setText(" " + totalAmount);

            tvMyCart.setText(R.string.payment_);

        } else {
            rlt_totalAmount.setVisibility(View.GONE);
        }

        if (getIntent().getStringExtra("showClearButton") != null) {
            btnClearOrderMyCards.setVisibility(View.GONE);
        } else {
            btnClearOrderMyCards.setVisibility(View.VISIBLE);
        }

        tvCartBack.setOnClickListener(this);
        btnAddNewCard.setOnClickListener(this);
        btnUpdateCard.setOnClickListener(this);
        btnClearOrderMyCards.setOnClickListener(this);

        showCreditCardInfo();
    }

    private void init() {
        cardsList = findViewById(R.id.cardsList);
        rlt_totalAmount = findViewById(R.id.rlt_totalAmount);
        llMiddleLayoutMyCards = findViewById(R.id.llMiddleLayoutMyCards);
        llBottomLayout = findViewById(R.id.llBottomLayout);
        amount = findViewById(R.id.amount);
        tvMyCart = findViewById(R.id.tvMyCart);
        tvCartBack = findViewById(R.id.tvCartBack);
        tvSaveCard = findViewById(R.id.tvSaveCard);
        tvNotFoundCard = findViewById(R.id.tvNotFoundCard);
        btnAddNewCard = findViewById(R.id.btnAddNewCard);
        btnUpdateCard = findViewById(R.id.btnUpdateCard);
        btnClearOrderMyCards = findViewById(R.id.btnClearOrderMyCards);

        /*if (CommonUtils.isNetworkAvailable(this)){
            llMiddleLayoutMyCards.setVisibility(View.VISIBLE);
            llBottomLayout.setVisibility(View.VISIBLE);
            tvNotFoundCard.setVisibility(View.VISIBLE);
        }
        else
        {
            llMiddleLayoutMyCards.setVisibility(View.GONE);
            llBottomLayout.setVisibility(View.GONE);
            tvNotFoundCard.setVisibility(View.GONE);
        }*/
    }

    ///""""""""" Saved credit card api """"""""//
    @SuppressLint("StaticFieldLeak")
    protected void showCreditCardInfo() {
        if (CommonUtils.isNetworkAvailable(this)) {
            cardResponce = new StripeSaveCardResponce();
            new AsyncTask<Void, Void, ExternalAccount>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    showLoader();
                }

                @Override
                protected ExternalAccount doInBackground(Void... voids) {
               //  Stripe.apiKey = getResources().getString(R.string.StripeKeyTest);
                  Stripe.apiKey = BuildConfig.STRIPE_SECRET_KEY;
                    ExternalAccount customer = null;
                    try {
                        String stripeCustomerId = session.getRegistration().getStripe_customer_id();//"cus_Fl6RC2yZA8vTna";//session.getRegistration().getStripe_customer_id();
                        Map<String, Object> cardParams = new HashMap<String, Object>();
                        cardParams.put("object", "card");
                        customer = Customer.retrieve(stripeCustomerId).getDefaultSourceObject();
                    //.all(cardParams);
                    } catch (StripeException ignored) {
                    }
                    return customer;
                }

                @Override
                protected void onPostExecute(final ExternalAccount externalAccountCollection) {
                    super.onPostExecute(externalAccountCollection);
                    hideLoader();
                    runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            if (externalAccountCollection != null) {
                                cardResponce = new Gson().fromJson(externalAccountCollection.toJson(), StripeSaveCardResponce.class);
                                Log.e("Size: ", "" + cardResponce.getData().size());
                                if (from.equals("PaymentActivity")){
                                    tvSaveCard.setText(R.string.select_cards);
                                }else {
                                    if (cardResponce.getData().size()==1){
                                        tvSaveCard.setText(cardResponce.getData().size() + " " +getString(R.string.saved_card));
                                    }else {
                                        tvSaveCard.setText(cardResponce.getData().size() + " " +getString(R.string.saved_cards));
                                    }
                                }
                                if (cardResponce.getData().size() > 0) {
                                    tvSaveCard.setVisibility(View.VISIBLE);
                                    cardsList.setVisibility(View.VISIBLE);
                                    tvNotFoundCard.setVisibility(View.GONE);
                                    btnUpdateCard.setVisibility(View.GONE);
                                    btnAddNewCard.setVisibility(View.VISIBLE);
                                } else {
                                    tvSaveCard.setVisibility(View.GONE);
                                    cardsList.setVisibility(View.GONE);
                                    tvNotFoundCard.setVisibility(View.VISIBLE);
                                    btnUpdateCard.setVisibility(View.GONE);
                                    btnAddNewCard.setVisibility(View.VISIBLE);
                                }
                                for (int i = 0; i < cardResponce.getData().size(); i++) {
                                    cardResponce.getData().get(i).setMoreDetail(true);
                                }
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyCardActivity.this);
                                cardsList.setLayoutManager(linearLayoutManager);
                                cardAdapter = new CardAdapter(MyCardActivity.this, cardResponce.getData(), new CardClickListener() {
                                    @Override
                                    public void deleteOnClick(int position) {
                                        String id = cardResponce.getData().get(position).getId();
                                        removedSaveCardApi(id);
                                    }

                                    @Override
                                    public void setCardDataOnClick(int position, String crdId) {
                                        index = position;
                                        cardId = crdId;
                                        System.out.println("%%%%%%%%%%%%%%%%" + cardId);
                                        if (cardSelection.equals("CardSelected")) {
                                            btnAddNewCard.setVisibility(View.GONE);
                                            btnUpdateCard.setVisibility(View.VISIBLE);
                                        }
                                    }
                                }, cardSelection);
                                cardAdapter.notifyDataSetChanged();
                                cardsList.setHasFixedSize(true);
                                cardsList.setAdapter(cardAdapter);
                            }
                        }
                    });
                }
            }.execute();
        } else {
            showInternetAlertDialog(this);
        }
    }

    //""""""""""  Remove Saved credit card """"""""""""//
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("StaticFieldLeak")
    private void removedSaveCardApi(final String id) {
        if (CommonUtils.isNetworkAvailable(MyCardActivity.this)) {
            new AsyncTask<Void, Void, ExternalAccountCollection>() {

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    showLoader();
                }

                @Override
                protected ExternalAccountCollection doInBackground(Void... voids) {
               //   Stripe.apiKey = getResources().getString(R.string.StripeKeyTest);
                        Stripe.apiKey = BuildConfig.STRIPE_SECRET_KEY;
                    ExternalAccountCollection customer = null;
                    try {
                      //  customer = Customer.retrieve(session.getRegistration().getStripe_customer_id()); // session.getRegistration().getStripe_customer_id();
                        Map<String, Object> retrieveParams = new HashMap<>();
                        List<String> expandList = new ArrayList<>();
                        expandList.add("sources");
                        retrieveParams.put("expand",expandList);
                        Customer  c = Customer.retrieve(
                                session.getRegistration().getStripe_customer_id(),
                                retrieveParams,
                                null
                        );
                        customer=c.getSources();
                        customer.retrieve(id).delete();
                    } catch (StripeException e) {
                        e.printStackTrace();
                        hideLoader();
                        toastMessage(e.getLocalizedMessage());
                    }
                    return customer;
                }

                @Override
                protected void onPostExecute(ExternalAccountCollection customer) {
                    super.onPostExecute(customer);
                    hideLoader();
                    if (customer != null) {
                        showCreditCardInfo();
                    }
                }
            }.execute();
        } else {
            showInternetAlertDialog(this);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (CommonUtils.isNetworkAvailable(this)) {
            llMiddleLayoutMyCards.setVisibility(View.VISIBLE);
            llBottomLayout.setVisibility(View.VISIBLE);
            //tvNotFoundCard.setVisibility(View.VISIBLE);
            showCreditCardInfo();
        } else {
            llMiddleLayoutMyCards.setVisibility(View.GONE);
            llBottomLayout.setVisibility(View.GONE);
            tvNotFoundCard.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        switch (view.getId()) {
            case R.id.tvCartBack:
                onBackPressed();
                break;
            case R.id.btnUpdateCard:
                if (CommonUtils.isNetworkAvailable(MyCardActivity.this)) {
                    paymentApi("2", "card", cardId, shipping_id);
                } else {
                    showInternetAlertDialog(this);
                }
                break;
            case R.id.btnAddNewCard:
                if (CommonUtils.isNetworkAvailable(MyCardActivity.this)) {
                    startActivity(new Intent(MyCardActivity.this, AddCardActivity.class));
                } else {
                    showInternetAlertDialog(this);
                }
                break;
            case R.id.btnClearOrderMyCards:
                if (CommonUtils.isNetworkAvailable(MyCardActivity.this)) {
                    startActivity(new Intent(this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();

                } else {
                    showInternetAlertDialog(this);
                }
                break;
        }
    }

    @Override
    public void onSuccessPayment(FinalPaymentResponse finalPaymentResponse) {
        if (!finalPaymentResponse.getStatus().equals("fail")) {
            orderNumber = finalPaymentResponse.getData().getOrder_number();
            startActivity(new Intent(this, PaymentConfirmActivity.class)
                    .putExtra("totalAmount", totalAmount)
                    .putExtra("orderNumber", orderNumber));
            finish();

        } else {
            showSubscribeDialog(this, finalPaymentResponse.getMessage());
        }
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