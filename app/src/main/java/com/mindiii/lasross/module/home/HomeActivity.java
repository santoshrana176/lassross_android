package com.mindiii.lasross.module.home;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.navigation.NavigationView;
import com.mindiii.lasross.R;
import com.mindiii.lasross.app.session.Session;
import com.mindiii.lasross.base.ApiCallback;
import com.mindiii.lasross.base.ClickListener;
import com.mindiii.lasross.base.LasrossParentActivity;
import com.mindiii.lasross.module.cart.MyCartActivity;
import com.mindiii.lasross.module.cart.model.CartListResponse;
import com.mindiii.lasross.module.faq.FAQActivity;
import com.mindiii.lasross.module.home.adapter.ExpandableListAdapter;
import com.mindiii.lasross.module.home.adapter.ExpandableListStaticAdapter;
import com.mindiii.lasross.module.home.adapter.FooterListAdapter;
import com.mindiii.lasross.module.home.adapter.HeaderListAdapter;
import com.mindiii.lasross.module.home.interfc.HeaderInterface;
import com.mindiii.lasross.module.home.model.AddRemoveWishListResponse;
import com.mindiii.lasross.module.home.model.BannerWeeklyOfferResponse;
import com.mindiii.lasross.module.home.model.Deal;
import com.mindiii.lasross.module.home.model.DealListResponse;
import com.mindiii.lasross.module.home.model.LogoutResponse;
import com.mindiii.lasross.module.home.model.MenuSliderResponse;
import com.mindiii.lasross.module.home.model.ProductResponse;
import com.mindiii.lasross.module.home.presenter.HomePresenter;
import com.mindiii.lasross.module.loginregistration.model.LoginResponse;
import com.mindiii.lasross.module.notification.NotificationsActivity;
import com.mindiii.lasross.module.notification.model.NotificationListModel;
import com.mindiii.lasross.module.productDetail.ProductDetailActivity;
import com.mindiii.lasross.module.profile.ProfileActivity;
import com.mindiii.lasross.module.profile.presenter.GetProfilePresenter;
import com.mindiii.lasross.module.wishlist.WishListActivity;
import com.mindiii.lasross.utils.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class HomeActivity extends LasrossParentActivity implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener, ApiCallback.ProductListCallback
        , View.OnClickListener, ApiCallback.GetProfileCallback {
    NotificationManager notificationManager;
    private RecyclerView rvListUp, rvListDown;
    private List<ProductResponse.DataBean.ProductListBean> productList;
    private List<Deal> dealList;
    private HeaderListAdapter headerListAdapter;
    private FooterListAdapter footerListAdapter;
    private ImageView ivUserPic;
    private TextView tvUserName, tvCartItemCount, tvNotificationCount;
    private EditText tvFilterText;
    private ExpandableListView listView1, listView2;//,listView3;
    private List<MenuSliderResponse.DataBean.CategoryBean> listDataHeader1;
    private List<String> listDataHeader2;//,listDataHeader3;
    private HashMap<String, List<String>> listHash2;//,listHash3;
    private DrawerLayout drawerLayout;
    private Session session;
    private RelativeLayout rlShopLayout;
    private long mLastClickTime = 0;
    private int index, minPrice = 0, maxPrice = 0;
    private String strBannerImage;
    private int previousGroupPosition;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_layout);
        handleNotification(getIntent());

        session = new Session(this);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(this.getResources().getColor(R.color.home_header_bg1));
        }
        // getCurrentFirebaseToken();
        initView();
        prfileApiCalling();

        rlShopLayout.setOnClickListener(this);
        tvFilterText.setOnClickListener(this);
    }

    private void callCartItemCountApi() {
        new GetProfilePresenter(this, this).callCartItemCountForHomeApi("");
    }

    private void callNotificationCountApi() {
        new GetProfilePresenter(this, this).callNotificationCountForHomeApi();
    }

    /* ..............for handle notification of group request...............*/
    private void handleNotification(Intent intent) {
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d("MainActivity: ", "Key: " + key + " Value: " + value);
            }
        }


        Bundle bundle = getIntent().getExtras();
        if (intent.getStringExtra("reference_id") != null) {

            String refrence_id = intent.getStringExtra("reference_id");
            String type = intent.getStringExtra("type");
            String body = intent.getStringExtra("body");
            String title = intent.getStringExtra("title");

            startActivity(new Intent(this, FAQActivity.class)
                    .putExtra("screenCheck", type)
                    .putExtra("orderId", refrence_id));

        } else {
            if (bundle != null) {
                if (bundle.getString("reference_id") != null) {
                    String refrence_id = intent.getStringExtra("reference_id");
                    String type = intent.getStringExtra("type");
                    startActivity(new Intent(this, FAQActivity.class)
                            .putExtra("screenCheck", type)
                            .putExtra("orderId", refrence_id));
                }
            }
        }
    }

    private void prfileApiCalling() {
        new GetProfilePresenter(this, this).callGetProfileApi();
    }

    private void initView() {
        listDataHeader1 = new ArrayList<>();
        listDataHeader2 = new ArrayList<>();
        listHash2 = new HashMap<>();
        productList = new ArrayList<>();
        dealList = new ArrayList<>();
        rvListUp = findViewById(R.id.rvListUp);
        rvListDown = findViewById(R.id.rvListDown);
        rvListUp.setHasFixedSize(true);
        rvListDown.setHasFixedSize(true);
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        tvUserName = findViewById(R.id.tvUserName);
        tvCartItemCount = findViewById(R.id.tvCartItemCount);
        tvNotificationCount = findViewById(R.id.tvNotificationCount);
        listView1 = findViewById(R.id.elvExpList1);
        listView2 = findViewById(R.id.elvExpList2);
        tvFilterText = findViewById(R.id.tvFilterText);
        ivUserPic = findViewById(R.id.ivUserPic);
        ImageView ivSort = findViewById(R.id.ivSort);
        ImageView ivMenu = findViewById(R.id.ivMenu);
        ImageView ivBagIcon = findViewById(R.id.bag_icon);
        ImageView ivNotificationHome = findViewById(R.id.ivNotificationHome);
        rlShopLayout = findViewById(R.id.rlShopLayout);
        RelativeLayout rvUserProfile = findViewById(R.id.rvUserProfile);
        ivNotificationHome.setOnClickListener(this);

        tvUserName.setText(session.getRegistration().getFull_name());
        if (session.getRegistration().getProfile_photo().equals("")) {
            Picasso.with(this).load(session.getRegistration().getProfile_photo()).into(ivUserPic);
        } else {
            Picasso.with(this).load(session.getRegistration().getProfile_photo()).into(ivUserPic);
        }

        //Log.e("image", session.getRegistration().getProfile_photo());


        ivBagIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, MyCartActivity.class));
            }
        });

        rvUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                //finish();
            }
        });

        ///////////////////////menuExpandableStatic()///////////////////////////////////
        menuExpandableStatic();
        callBannerWeeklyOfferApi();
        callCartItemCountApi();
        callNotificationCountApi();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        ivSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });


        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        listView1.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                parent.smoothScrollToPosition(groupPosition);
                if (parent.isGroupExpanded(previousGroupPosition)) {
                    parent.collapseGroup(previousGroupPosition);
                //    parent.expandGroup(groupPosition);
                    //previousGroupPosition = groupPosition;
                    if (groupPosition!=previousGroupPosition){
                        parent.expandGroup(groupPosition);
                        previousGroupPosition=groupPosition;
                    }

                } else {
                    previousGroupPosition = groupPosition;
                    parent.expandGroup(groupPosition);
                }
                return true;
            }
        });

        tvFilterText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (headerListAdapter != null) {
                    headerListAdapter.filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void callProductListApi() {
        if (CommonUtils.isNetworkAvailable(HomeActivity.this)) {
            new HomePresenter(this, this).callGetProductListApi("", "", ""
                    , "", "", "", "", "", "", "", "", "", "", "");
            new HomePresenter(this, this).callSlideMenuApi();
            new HomePresenter(this, this).callDealListApi();
        } else {
            //showInternetAlertDialog(HomeActivity.this);
        }
    }

    private void callBannerWeeklyOfferApi() {
        if (CommonUtils.isNetworkAvailable(HomeActivity.this)) {
            new HomePresenter(this, this).callBannerWeeklyOfferApi();
        } else {
            showInternetAlertDialog(HomeActivity.this);
        }
    }

    private void callLogoutApi() {
        new HomePresenter(this, this).callLogoutApi();
    }

    private void setHeaderAdapterData() {
        headerListAdapter = new HeaderListAdapter(strBannerImage, productList, this, new HeaderInterface() {
            @Override
            public void onClickListener(int i) {
                if (CommonUtils.isNetworkAvailable(HomeActivity.this)) {
                    startActivity(new Intent(HomeActivity.this, ProductDetailActivity.class)
                            .putExtra("productId", productList.get(i).getProductId()));
                } else {
                    showInternetAlertDialog(HomeActivity.this);
                }
            }

            @Override
            public void OnAddWishListClickListener(int position) {
                if (CommonUtils.isNetworkAvailable(HomeActivity.this)) {
                    index = position;
                    String productId = productList.get(index).getProductId();
                    addRemoveApiCalling(productId);
                } else {
                    showInternetAlertDialog(HomeActivity.this);
                }
            }

            @Override
            public void onTopImageClick() {
                startActivity(new Intent(HomeActivity.this, CoatsActivity.class)
                        .putExtra("ChildCatName", "Shop")
                        .putExtra("myCatId", "")
                        .putExtra("dealId", "")
                        .putExtra("minPrice", minPrice)
                        .putExtra("maxPrice", maxPrice)
                        .putExtra("fromHome", "fromHome")
                );
            }
        });

        GridLayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (headerListAdapter.getItemViewType(position)) {
                    case HeaderListAdapter.TYPE_HEAD:
                        return 2;
                    case HeaderListAdapter.TYPE_LIST:
                    default:
                        return 1;
                }
            }
        });
        rvListUp.setLayoutManager(mLayoutManager);
        rvListUp.setAdapter(headerListAdapter);
    }

    private void setFooterAdapterData() {
        footerListAdapter = new FooterListAdapter(dealList, this, new ClickListener.HomeFooterClick() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(HomeActivity.this, CoatsActivity.class)
                        .putExtra("ChildCatName", dealList.get(position).getDeal_title())
                        .putExtra("myCatId", "")
                        .putExtra("dealId", dealList.get(position).getDealId())
                        .putExtra("minPrice", minPrice)
                        .putExtra("maxPrice", maxPrice)
                        .putExtra("fromHome", "fromHome")
                );
            }
        });
        rvListDown.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rvListDown.setAdapter(footerListAdapter);
    }

    private void openDialog() {

        final Dialog dialog = new Dialog(HomeActivity.this);//,android.R.style.Theme_Dialog);
        dialog.setContentView(R.layout.open_dialog);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final RadioGroup radioGroup;
        final RadioButton rbDefault, rbPoplarity, rbRating, rbLatest, rbLowHigh, rbHighLow;

        radioGroup = dialog.findViewById(R.id.radioGroup);
        rbDefault = dialog.findViewById(R.id.rbDefault);
        rbPoplarity = dialog.findViewById(R.id.rbPoplarity);
        rbRating = dialog.findViewById(R.id.rbRating);
        rbLatest = dialog.findViewById(R.id.rbLatest);
        rbLowHigh = dialog.findViewById(R.id.rbLowHigh);
        rbHighLow = dialog.findViewById(R.id.rbHighLow);

        final Typeface semi_bold = ResourcesCompat.getFont(HomeActivity.this, R.font.ibmplexsans_semibold);
        final Typeface regular = ResourcesCompat.getFont(HomeActivity.this, R.font.ibmplexsans_regular);

        rbDefault.setChecked(true);
        rbDefault.setTypeface(semi_bold);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rbDefault) {
                    rbDefault.setTypeface(semi_bold);
                } else {
                    rbDefault.setTypeface(regular);
                }

                if (checkedId == R.id.rbPoplarity) {
                    rbPoplarity.setTypeface(semi_bold);
                } else {
                    rbPoplarity.setTypeface(regular);
                }

                if (checkedId == R.id.rbRating) {
                    rbRating.setTypeface(semi_bold);
                } else {
                    rbRating.setTypeface(regular);
                }

                if (checkedId == R.id.rbLatest) {
                    rbLatest.setTypeface(semi_bold);
                } else {
                    rbLatest.setTypeface(regular);
                }

                if (checkedId == R.id.rbLowHigh) {
                    rbLowHigh.setTypeface(semi_bold);
                } else {
                    rbLowHigh.setTypeface(regular);
                }

                if (checkedId == R.id.rbHighLow) {
                    rbHighLow.setTypeface(semi_bold);
                } else {
                    rbHighLow.setTypeface(regular);
                }
            }
        });

        dialog.setTitle("Add Photo!");
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        lp.windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

    }

    private void setExpandableListViewHeight(ExpandableListView listView, int group) {

        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);

        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();
                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 100;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    private void setExpandableListStaticViewHeight(ExpandableListView listView, int group) {
/*
        ExpandableListStaticAdapter listAdapter = (ExpandableListStaticAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);

        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();
                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 100;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();*/

    }

    private void menuExpandableStatic() {

        listDataHeader2.add("Wishlist");
        listDataHeader2.add("Order");
        listDataHeader2.add("Cart");
        listDataHeader2.add("Logout");

        List<String> wishList = new ArrayList<>();
        List<String> order = new ArrayList<>();
        List<String> cart = new ArrayList<>();
        final List<String> logout = new ArrayList<>();

        listHash2.put(listDataHeader2.get(0), wishList);
        listHash2.put(listDataHeader2.get(1), order);
        listHash2.put(listDataHeader2.get(2), cart);
        listHash2.put(listDataHeader2.get(3), logout);

        ExpandableListStaticAdapter listAdapter2 = new ExpandableListStaticAdapter(HomeActivity.this, listDataHeader2, listHash2);
        listView2.setAdapter(listAdapter2);

        setExpandableListStaticViewHeight(listView2, -1);
        listView2.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int position, long id) {
                if (position == 0) {
                    startActivity(new Intent(HomeActivity.this, WishListActivity.class));
                } else if (position == 1) {
                    startActivity(new Intent(HomeActivity.this, FAQActivity.class)
                            .putExtra("screenCheck", "myOrder"));
                } else if (position == 2) {
                    startActivity(new Intent(HomeActivity.this, MyCartActivity.class));
                }
                /*else if (position == 3) {
                    startActivity(new Intent(HomeActivity.this, FAQActivity.class)
                            .putExtra("screenCheck", "customerhelp"));
                }*/
                else if (position == 3) {
                    showLogoutDialog(HomeActivity.this, getResources().getString(R.string.logout_msg));
                } else if (position == 5) {
                    startActivity(new Intent(HomeActivity.this, FAQActivity.class)
                            .putExtra("screenCheck", "faqs"));
                } else if (position == 6) {
                    startActivity(new Intent(HomeActivity.this, FAQActivity.class)
                            .putExtra("screenCheck", "contactus"));
                }

                setExpandableListStaticViewHeight(parent, position);
                return false;
            }
        });


        listView2.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousItem = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousItem)
                    listView2.collapseGroup(previousItem);
                previousItem = groupPosition;
            }
        });
    }

    // logout dialog
    public void showLogoutDialog(final Activity activity, String message) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.logout_view);
        TextView tvMessagesLogout = dialog.findViewById(R.id.tvMessagesLogout);
        tvMessagesLogout.setText(message);
        TextView tvPopupNoLogout = dialog.findViewById(R.id.tvPopupNoLogout);
        TextView tvPopupYesLogout = dialog.findViewById(R.id.tvPopupYesLogout);
        tvPopupNoLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvPopupYesLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callLogoutApi();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //add remove product to cart api
    private void addRemoveApiCalling(String productId) {
        new HomePresenter(this, this).callAddRemoveWishListApi(productId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        callProductListApi();
        callBannerWeeklyOfferApi();
        callCartItemCountApi();
        callNotificationCountApi();

        tvUserName.setText(session.getRegistration().getFull_name());
        if (session.getRegistration().getProfile_photo().equals(""))
            Picasso.with(this).load(session.getRegistration().getProfile_photo()).into(ivUserPic);
        else
            Picasso.with(this).load(session.getRegistration().getProfile_photo()).into(ivUserPic);
        drawerLayout.closeDrawers();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onSuccessProductList(ProductResponse productResponse) {
        minPrice = Integer.parseInt(productResponse.getMinPrice());
        maxPrice = Integer.parseInt(productResponse.getMaxPrice());
        session.setProductDetail(String.valueOf(minPrice), String.valueOf(maxPrice));
        productList.clear();
        productList.addAll(productResponse.getData().getProduct_list());
        setHeaderAdapterData();
        headerListAdapter.setListData(productResponse.getData().getProduct_list());
        headerListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccessLogout(LogoutResponse logoutResponse) {
        session.logout();
    }

    @Override
    public void onSuccessAddRemoveWishList(AddRemoveWishListResponse addRemoveWishListResponse) {
        productList.get(index).setIs_wishlist(addRemoveWishListResponse.getIs_wishlist());
        headerListAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnSuccessMenuSlider(MenuSliderResponse menuSliderResponse) {
        listDataHeader1.clear();
        listDataHeader1.addAll(menuSliderResponse.getData().getCategory());
        ExpandableListAdapter listAdapter1 = new ExpandableListAdapter(new ClickListener.MenuChildClick() {
            @Override
            public void onClick(String myCatId, String catName) {
                if (CommonUtils.isNetworkAvailable(HomeActivity.this)) {
                    startActivity(new Intent(HomeActivity.this, CoatsActivity.class)
                            .putExtra("ChildCatName", catName)
                            .putExtra("myCatId", myCatId)
                            .putExtra("dealId", "")
                            .putExtra("fromHomeActivity", "fromHome")
                            .putExtra("minPrice", minPrice)
                            .putExtra("maxPrice", maxPrice)
                            .putExtra("fromHome", "fromHome")
                    );
                } else {
                    // showInternetAlertDialog(HomeActivity.this);
                }

            }
        }, HomeActivity.this, listDataHeader1);
        listView1.setAdapter(listAdapter1);
       // setExpandableListViewHeight(listView1, -1);
    }

    @Override
    public void OnSuccessDealList(DealListResponse dealListResponse) {
        dealList.clear();
        dealList.addAll(dealListResponse.getDeal_list());
        if (dealList.size() > 0)
            setFooterAdapterData();
    }

    @Override
    public void onSuccessGetProfile(LoginResponse loginResponse) {
        Session session = new Session(this);
        session.createRegistration(loginResponse.getData().getUserDetail());
      //  session.setAuthToken(loginResponse.getData().getUserDetail().getAuth_token());
        session.setUserLoggedIn();
    }

    @Override
    public void onSuccessWeeklyOffer(BannerWeeklyOfferResponse bannerWeeklyOfferResponse) {
        if (bannerWeeklyOfferResponse != null) {
            if (bannerWeeklyOfferResponse.getData().getBanner_image().equals(""))
                strBannerImage = "";
            else
                strBannerImage = bannerWeeklyOfferResponse.getData().getBanner_image();

        }
        callProductListApi();
    }

    @Override
    public void onSuccessGetCart(CartListResponse cartListResponse) {
        session.setCartItemCount(cartListResponse.getData().getPricing_detail().getTotal_item());
        if (session.getCartItemCount().equalsIgnoreCase("0"))
            tvCartItemCount.setVisibility(View.GONE);
        else {
            tvCartItemCount.setVisibility(View.VISIBLE);
            tvCartItemCount.setText(session.getCartItemCount());
        }
    }

    @Override
    public void onSuccessNotificationList(NotificationListModel notificationListModel) {
        if (notificationListModel.getStatus().equals("success")) {
            if (notificationListModel.getCount().equals("0")) {
                tvNotificationCount.setVisibility(View.GONE);
            } else {
                tvNotificationCount.setVisibility(View.VISIBLE);
                tvNotificationCount.setText(notificationListModel.getCount());
            }
        } else {
            if (notificationListModel.getCount().equals("0")) {
                tvNotificationCount.setVisibility(View.GONE);
            } else {
                tvNotificationCount.setVisibility(View.VISIBLE);
                tvNotificationCount.setText(notificationListModel.getCount());
            }
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
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        switch (view.getId()) {
            case R.id.rlShopLayout:
            case R.id.tvFilterText:
                startActivity(new Intent(HomeActivity.this, CoatsActivity.class)
                        .putExtra("ChildCatName", "Shop")
                        .putExtra("myCatId", "")
                        .putExtra("dealId", "")
                        .putExtra("minPrice", minPrice)
                        .putExtra("maxPrice", maxPrice)
                        .putExtra("fromHome", "fromHome")
                );
                break;
            case R.id.ivNotificationHome:
                startActivity(new Intent(HomeActivity.this, NotificationsActivity.class));
                break;
        }
    }
}