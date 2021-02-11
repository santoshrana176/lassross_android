package com.mindiii.lasross.module.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mindiii.lasross.R;
import com.mindiii.lasross.module.home.adapter.CoatAdapter;
import com.mindiii.lasross.app.session.Session;
import com.mindiii.lasross.base.ApiCallback;
import com.mindiii.lasross.base.ClickListener;
import com.mindiii.lasross.base.LasrossParentActivity;
import com.mindiii.lasross.module.Filter.activity.FiltersActivity;
import com.mindiii.lasross.module.cart.MyCartActivity;
import com.mindiii.lasross.module.home.model.AddRemoveWishListResponse;
import com.mindiii.lasross.module.home.model.ProductResponse;
import com.mindiii.lasross.module.home.presenter.CoatPresenter;
import com.mindiii.lasross.module.productDetail.ProductDetailActivity;
import com.mindiii.lasross.utils.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;

@SuppressLint("Registered")
public class CoatsActivity extends LasrossParentActivity implements ApiCallback.CoatsListCallback, View.OnClickListener {

    private RecyclerView recyclerView;
    private CoatAdapter coatAdapter;
    private ImageView ivFilterIcon, ivBackButton, ivSort, bag_icon;
    private TextView tvWishList, tvCartItemCountShop;
    private TextView tvNotFound;
    private String searchText = "";
    private String lastText = "";
    private String popularity = "", average_rating = "", latest = "", lowPrice = "", highPrice = "", childCatName = "";
    private int index;
    private List<ProductResponse.DataBean.ProductListBean> productListBeans;
    private String myCatId = "", dealId = "";
    private int offset = 0;
    private GridLayoutManager gridLayoutManager;
    private BottomSheetDialog dialog;
    private String sizeIds = "", colorIds = "", prizeLow = "", priceHigh = "";
    private Session session;
    private boolean IsSearch = true;
    private int minPrice, maxPrice;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coat_activity_layout);
        init();

        minPrice = getIntent().getIntExtra("minPrice", 0);
        maxPrice = getIntent().getIntExtra("maxPrice", 0);

        if (getIntent().getStringExtra("ChildCatName") != null) {
            childCatName = getIntent().getStringExtra("ChildCatName");
          String deal= getIntent().getStringExtra("title");
           if (deal.isEmpty())
            tvWishList.setText(childCatName);
           else
            tvWishList.setText(deal);


            productListBeans.clear();
            myCatId = getIntent().getStringExtra("myCatId");
            dealId = getIntent().getStringExtra("dealId");
            showLoader();
            searchText = "";
            popularity = "";
            average_rating = "";
            latest = "";
            lowPrice = "";
            highPrice = "";
            sizeIds = "";
            colorIds = "";
            prizeLow = "";
            priceHigh = "";
            childCatName = "";
            IsSearch = true;
            apiCalling(searchText, popularity, average_rating, latest, lowPrice, highPrice, sizeIds, colorIds, prizeLow, priceHigh);
        }
        else if (getIntent().getStringExtra("childCategoryName") != null) {
            productListBeans.clear();
            sizeIds = getIntent().getStringExtra("sizeIds");
            colorIds = getIntent().getStringExtra("colorIds");
            prizeLow = getIntent().getStringExtra("prizeLow");
            priceHigh = getIntent().getStringExtra("priceHigh");
            childCatName = getIntent().getStringExtra("childCategoryName");
            myCatId = getIntent().getStringExtra("myCategoryId");
            dealId = getIntent().getStringExtra("dealId");
            lowPrice = getIntent().getStringExtra("lowPrice");
            highPrice = getIntent().getStringExtra("highPrice");
            latest = getIntent().getStringExtra("latest");
            popularity = getIntent().getStringExtra("popularity");
            average_rating = getIntent().getStringExtra("average_rating");
            tvWishList.setText(childCatName);
            searchText = "";
            IsSearch = true;
            apiCalling(searchText, popularity, average_rating, latest, lowPrice, highPrice, sizeIds, colorIds, prizeLow, priceHigh);
        }

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.home_header_bg1));

        /*Click Listener*/
        ivFilterIcon.setOnClickListener(this);
        ivBackButton.setOnClickListener(this);
        ivSort.setOnClickListener(this);
        bag_icon.setOnClickListener(this);
    }

    private void apiCalling(String search, String popularity, String average_rating, String latests, String low_price, String high_price, String sizeId, String colorId, String prizeLows, String priceHighs) {
        new CoatPresenter(this, this).callGetProductListApi(search, "18", String.valueOf(offset), sizeId,
                colorId, prizeLows, priceHighs, popularity, average_rating, latests, low_price, high_price, myCatId, dealId);
    }

    private void setProductAdapter() {
        coatAdapter = new CoatAdapter(productListBeans, this, new ClickListener.WishListClickListener() {
            @Override
            public void onWishClick(int position) {
                if (productListBeans.size() != 0) {
                    try {
                        index = position;
                        String productId = productListBeans.get(position).getProductId();
                        addRemoveApiCalling(productId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onProductClick(int position) {
                if (productListBeans.size() != 0) {
                    try {
                        startActivity(new Intent(CoatsActivity.this, ProductDetailActivity.class)
                                .putExtra("productId", productListBeans.get(position).getProductId()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        recyclerView.setAdapter(coatAdapter);
    }

    private void addRemoveApiCalling(String productId) {
        new CoatPresenter(this, this).callAddRemoveWishListApi(productId);
    }

    private void init() {
        session = new Session(CoatsActivity.this);
        productListBeans = new ArrayList<>();
        EditText edtProductFilterWithName = findViewById(R.id.edtProductFilterWithName);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        ivFilterIcon = findViewById(R.id.ivFilterIcon);
        ivBackButton = findViewById(R.id.ivBackButton);
        tvWishList = findViewById(R.id.tvWishList);
        tvCartItemCountShop = findViewById(R.id.tvCartItemCountShop);
        ivSort = findViewById(R.id.ivSort);
        tvNotFound = findViewById(R.id.tvNotFound);
        bag_icon = findViewById(R.id.bag_icon);
        if (session.getCartItemCount().equals("0"))
            tvCartItemCountShop.setVisibility(View.GONE);
        else {
            tvCartItemCountShop.setVisibility(View.VISIBLE);
            tvCartItemCountShop.setText(session.getCartItemCount());
        }

        setProductAdapter();
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (IsSearch) {
                    if (page != 0) {
                        //coatAdapter.showLoading(true);
                        offset += 18; //load 18 items in recyclerView
                        apiCalling(searchText, popularity, average_rating, latest, lowPrice, highPrice, sizeIds, colorIds, prizeLow, priceHigh);
                    }
                }

            }
        };
        recyclerView.addOnScrollListener(scrollListener);
        edtProductFilterWithName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                productListBeans.clear();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                IsSearch = false;
                searchText = editable.toString().trim();
                if (!searchText.equals(lastText)) {
                    productListBeans.clear();
                    offset = 0;
                    coatAdapter.notifyDataSetChanged();
                    AndroidNetworking.cancel("CoatsActivity");
                    lastText = searchText;
                    latest = "";
                    lowPrice = "";
                    highPrice = "";
                    popularity = "";
                    average_rating = "";
                    productListBeans.clear();
                    apiCalling(searchText, popularity, average_rating, latest, lowPrice, highPrice, sizeIds, colorIds, prizeLow, priceHigh);
                }
            }
        });
    }

    @Override
    public void onSuccessProductList(ProductResponse productResponse) {

        if (!IsSearch) {
            productListBeans.clear();
            productListBeans.addAll(productResponse.getData().getProduct_list());
            LinkedHashSet<ProductResponse.DataBean.ProductListBean> hashSet = new LinkedHashSet<>(productListBeans);
            ArrayList<ProductResponse.DataBean.ProductListBean> listWithoutDuplicates = new ArrayList<>(hashSet);
            coatAdapter.setList(listWithoutDuplicates);
            coatAdapter.notifyDataSetChanged();
        } else {
            productListBeans.addAll(productResponse.getData().getProduct_list());
            coatAdapter.notifyDataSetChanged();
        }
        if (productListBeans.size() > 0) {
            tvNotFound.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            tvNotFound.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSuccessAddRemoveWishList(AddRemoveWishListResponse addRemoveWishListResponse) {
        // toastMessage(addRemoveWishListResponse.getMessage());
        productListBeans.get(index).setIs_wishlist(addRemoveWishListResponse.getIs_wishlist());
        coatAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTokenChangeError(String errorMessage) {
        showDialog(this);
    }

    @Override
    public void onShowBaseLoader() {
        if (searchText.isEmpty()) {
            showLoader();
        } else {
            hideLoader();
        }
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivFilterIcon:
                startActivity(new Intent(CoatsActivity.this, FiltersActivity.class)
                        .putExtra("childCatName", tvWishList.getText().toString())
                        .putExtra("myCatId", myCatId)
                        .putExtra("lowPrice", lowPrice)
                        .putExtra("highPrice", highPrice)
                        .putExtra("latest", latest)
                        .putExtra("popularity", popularity)
                        .putExtra("average_rating", average_rating)
                        .putExtra("minPrice", minPrice)
                        .putExtra("maxPrice", maxPrice)
                );

                break;
            case R.id.ivBackButton:
                session.setFilterPrice(null, null);
                session.setFilterSizeIds(null);
                session.setFilterColorIds(null);
                onBackPressed();
                //startActivity(new Intent(CoatsActivity.this, HomeActivity.class));
                break;
            case R.id.ivSort:
                openDialog();
                break;
            case R.id.bag_icon:
                startActivity(new Intent(CoatsActivity.this, MyCartActivity.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        session.setFilterPrice(null, null);
        session.setFilterSizeIds(null);
        session.setFilterColorIds(null);
    }

    private void openDialog() {
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.open_dialog, null);
        dialog = new BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(view);
        final RadioGroup radioGroup;
        final RadioButton rbDefault, rbPoplarity, rbRating, rbLatest, rbLowHigh, rbHighLow;

        radioGroup = dialog.findViewById(R.id.radioGroup);
        rbDefault = dialog.findViewById(R.id.rbDefault);
        rbPoplarity = dialog.findViewById(R.id.rbPoplarity);
        rbRating = dialog.findViewById(R.id.rbRating);
        rbLatest = dialog.findViewById(R.id.rbLatest);
        rbLowHigh = dialog.findViewById(R.id.rbLowHigh);
        rbHighLow = dialog.findViewById(R.id.rbHighLow);

        final Typeface semi_bold = ResourcesCompat.getFont(CoatsActivity.this, R.font.ibmplexsans_semibold);
        final Typeface regular = ResourcesCompat.getFont(CoatsActivity.this, R.font.ibmplexsans_regular);

        assert rbDefault != null;
        rbDefault.setChecked(true);
        rbDefault.setTypeface(semi_bold);

        /////////POPULARITY///////////
        if (!popularity.isEmpty()) {
            rbDefault.setChecked(false);
            rbDefault.setTypeface(regular);

            assert rbPoplarity != null;
            rbPoplarity.setChecked(true);
            rbPoplarity.setTypeface(semi_bold);

            assert rbRating != null;
            rbRating.setChecked(false);
            assert rbLatest != null;
            rbLatest.setChecked(false);
            assert rbLowHigh != null;
            rbLowHigh.setChecked(false);
            assert rbHighLow != null;
            rbHighLow.setChecked(false);
        }

        /////////AVERAGE RATING///////////
        else if (!average_rating.isEmpty()) {
            rbDefault.setChecked(false);
            rbDefault.setTypeface(regular);
            assert rbPoplarity != null;
            rbPoplarity.setChecked(false);

            assert rbRating != null;
            rbRating.setChecked(true);
            rbRating.setTypeface(semi_bold);

            assert rbLatest != null;
            rbLatest.setChecked(false);
            assert rbLowHigh != null;
            rbLowHigh.setChecked(false);
            assert rbHighLow != null;
            rbHighLow.setChecked(false);
        }

        /////////LATEST///////////
        else if (!latest.isEmpty()) {
            rbDefault.setChecked(false);
            rbDefault.setTypeface(regular);
            assert rbPoplarity != null;
            rbPoplarity.setChecked(false);
            assert rbRating != null;
            rbRating.setChecked(false);

            assert rbLatest != null;
            rbLatest.setChecked(true);
            rbLatest.setTypeface(semi_bold);

            assert rbLowHigh != null;
            rbLowHigh.setChecked(false);
            assert rbHighLow != null;
            rbHighLow.setChecked(false);
        }

        /////////LOW TO HIGH///////////
        else if (!lowPrice.isEmpty()) {
            rbDefault.setChecked(false);
            rbDefault.setTypeface(regular);
            assert rbPoplarity != null;
            rbPoplarity.setChecked(false);
            assert rbRating != null;
            rbRating.setChecked(false);
            assert rbLatest != null;
            rbLatest.setChecked(false);

            assert rbLowHigh != null;
            rbLowHigh.setChecked(true);
            rbLowHigh.setTypeface(semi_bold);

            assert rbHighLow != null;
            rbHighLow.setChecked(false);
        }

        /////////HIGH TO LOW///////////
        else if (!highPrice.isEmpty()) {
            rbDefault.setChecked(false);
            rbDefault.setTypeface(regular);
            assert rbPoplarity != null;
            rbPoplarity.setChecked(false);
            assert rbRating != null;
            rbRating.setChecked(false);
            assert rbLatest != null;
            rbLatest.setChecked(false);
            assert rbLowHigh != null;
            rbLowHigh.setChecked(false);

            assert rbHighLow != null;
            rbHighLow.setChecked(true);
            rbHighLow.setTypeface(semi_bold);
        }

        ///////////DEFAULT/////////////
        else {
            rbDefault.setChecked(true);
            rbDefault.setTypeface(semi_bold);

            assert rbPoplarity != null;
            rbPoplarity.setChecked(false);
            assert rbRating != null;
            rbRating.setChecked(false);
            assert rbLatest != null;
            rbLatest.setChecked(false);
            assert rbLowHigh != null;
            rbLowHigh.setChecked(false);
            assert rbHighLow != null;
            rbHighLow.setChecked(false);
        }

        assert radioGroup != null;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @androidx.annotation.RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbDefault) {
                    rbDefault.setTypeface(semi_bold);
                } else {
                    rbDefault.setTypeface(regular);
                }

                if (checkedId == R.id.rbPoplarity) {
                    rbPoplarity.setTypeface(semi_bold);
                    productListBeans.clear();
                    searchText = "";
                    popularity = "popularity=1";
                    average_rating = "";
                    latest = "";
                    lowPrice = "";
                    highPrice = "";
                 /* sizeIds = "";
                    colorIds = "";
                    prizeLow = "";
                    priceHigh = "";*/
                    offset = 0;
                    recyclerView.scrollToPosition(0);
                    IsSearch = true;
                    apiCalling(searchText, popularity, average_rating, latest, lowPrice, highPrice, sizeIds, colorIds, prizeLow, priceHigh);
                } else {
                    rbPoplarity.setTypeface(regular);
                }

                if (checkedId == R.id.rbRating) {
                    rbRating.setTypeface(semi_bold);
                    productListBeans.clear();
                    searchText = "";
                    popularity = "";
                    average_rating = "average_rating";
                    latest = "";
                    lowPrice = "";
                    highPrice = "";
                 /* sizeIds = "";
                    colorIds = "";
                    prizeLow = "";
                    priceHigh = "";*/
                    offset = 0;
                    recyclerView.scrollToPosition(0);
                    IsSearch = true;
                    apiCalling(searchText, popularity, average_rating, latest, lowPrice, highPrice, sizeIds, colorIds, prizeLow, priceHigh);
                } else {
                    rbRating.setTypeface(regular);
                }

                if (checkedId == R.id.rbLatest) {
                    rbLatest.setTypeface(semi_bold);
                    productListBeans.clear();
                    searchText = "";
                    popularity = "";
                    average_rating = "";
                    latest = "latest";
                    lowPrice = "";
                    highPrice = "";
                 /* sizeIds = "";
                    colorIds = "";
                    prizeLow = "";
                    priceHigh = "";*/
                    offset = 0;
                    recyclerView.scrollToPosition(0);
                    IsSearch = true;
                    apiCalling(searchText, popularity, average_rating, latest, lowPrice, highPrice, sizeIds, colorIds, prizeLow, priceHigh);
                } else {
                    rbLatest.setTypeface(regular);
                }

                if (checkedId == R.id.rbLowHigh) {
                    rbLowHigh.setTypeface(semi_bold);
                    productListBeans.clear();
                    searchText = "";
                    popularity = "";
                    average_rating = "";
                    latest = "";
                    lowPrice = "price_low";
                    highPrice = "";
                 /* sizeIds = "";
                    colorIds = "";
                    prizeLow = "";
                    priceHigh = "";*/
                    offset = 0;
                    recyclerView.scrollToPosition(0);
                    IsSearch = true;
                    apiCalling(searchText, popularity, average_rating, latest, lowPrice, highPrice, sizeIds, colorIds, prizeLow, priceHigh);
                } else {
                    rbLowHigh.setTypeface(regular);
                }

                if (checkedId == R.id.rbHighLow) {
                    rbHighLow.setTypeface(semi_bold);
                    productListBeans.clear();
                    searchText = "";
                    popularity = "";
                    average_rating = "";
                    latest = "";
                    lowPrice = "";
                    highPrice = "price_high";
                /*  sizeIds = "";
                    colorIds = "";
                    prizeLow = "";
                    priceHigh = "";*/
                    offset = 0;
                    recyclerView.scrollToPosition(0);
                    IsSearch = true;
                    apiCalling(searchText, popularity, average_rating, latest, lowPrice, highPrice, sizeIds, colorIds, prizeLow, priceHigh);
                } else {
                    rbHighLow.setTypeface(regular);
                }
                dialog.dismiss();
            }
        });

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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recyclerView.scrollToPosition(0);
        offset = 0;
        productListBeans.clear();
        IsSearch = true;

        if (session.getCartItemCount().equals("0"))
            tvCartItemCountShop.setVisibility(View.GONE);
        else {
            tvCartItemCountShop.setVisibility(View.VISIBLE);
            tvCartItemCountShop.setText(session.getCartItemCount());
        }
        apiCalling(searchText, popularity, average_rating, latest, lowPrice, highPrice, sizeIds, colorIds, prizeLow, priceHigh);
    }
}