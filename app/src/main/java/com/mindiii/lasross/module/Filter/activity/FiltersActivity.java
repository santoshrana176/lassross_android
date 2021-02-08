package com.mindiii.lasross.module.Filter.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

 import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.mindiii.lasross.R;
import com.mindiii.lasross.app.session.Session;
import com.mindiii.lasross.base.ApiCallback;
import com.mindiii.lasross.base.ClickListener;
import com.mindiii.lasross.base.LasrossParentActivity;
import com.mindiii.lasross.module.Filter.adapter.VariantAdapter;
import com.mindiii.lasross.module.Filter.model.VarientListResponse;
import com.mindiii.lasross.module.Filter.presenter.VarientFilterListPresenter;
import com.mindiii.lasross.module.home.CoatsActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class FiltersActivity extends LasrossParentActivity implements ApiCallback.VariantFilterCallback, View.OnClickListener {

    private List<VarientListResponse.DataBean.VariantBean> sizeModelList;
    private RecyclerView recyclerViewColor;
    private TextView tvItemSize, tvItemColor, tvItemPrice, tvMinPriceRange, tvMaxPriceRange, tvClearAll;
    private ImageView ivItemSizeIcon;
    private ImageView ivItemColorIcon;
    private ImageView ivItemPriceIcon;
    private LinearLayout llItemSize, llItemColor, llItemPrice, llPriceTouch, llSearchlayout;
    private Button btnApply,btnClose;
    private VariantAdapter variantAdapter;
    private String sizeName = "", colorName = "";
    private ArrayList<String> sb;
    private ArrayList<String> sb1;
    private HashSet<String> hashSet, hashSet1;
    private StringBuilder stringBuilder, stringBuilder1;
    private String sizeIds = "", colorIds = "", prizeLow = "", priceHigh = "";
    private String childCatName = "", myCatId = "", dealId = "", popularity = "", average_rating = "", lowPrice = "", highPrice = "", latest = "";
    private Session session;
    //private CrystalRangeSeekbar crystalRangeSeekbar;
    private RangeSeekBar seekbar_distance;
    private int minPrice, maxPrice;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filters_activity_layout_12);

        minPrice = getIntent().getIntExtra("minPrice", 0);
        maxPrice = getIntent().getIntExtra("maxPrice", 0);

        if (getIntent().getStringExtra("childCatName") != null) {
            childCatName = getIntent().getStringExtra("childCatName");
            myCatId = getIntent().getStringExtra("myCatId");
            dealId = getIntent().getStringExtra("dealId");
            popularity = getIntent().getStringExtra("popularity");
            average_rating = getIntent().getStringExtra("average_rating");
            lowPrice = getIntent().getStringExtra("lowPrice");
            highPrice = getIntent().getStringExtra("highPrice");
            latest = getIntent().getStringExtra("latest");
        }
        session = new Session(FiltersActivity.this);
        init();
        apiCalling();

        tvMinPriceRange.setText(minPrice + "");
        tvMaxPriceRange.setText(maxPrice + "");

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(R.color.home_header_bg1));
    }

    private void apiCalling() {
        new VarientFilterListPresenter(this, this).callGetVarientListApi();
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        sizeModelList = new ArrayList<>();
        sb = new ArrayList<>();
        sb1 = new ArrayList<>();
        recyclerViewColor = findViewById(R.id.recyclerViewColor);

        btnApply = findViewById(R.id.btnApply);

        tvItemSize = findViewById(R.id.tvItemSize);
        tvItemColor = findViewById(R.id.tvItemColor);
        tvItemPrice = findViewById(R.id.tvItemPrice);
        tvMinPriceRange = findViewById(R.id.tvMinPriceRange);
        tvMaxPriceRange = findViewById(R.id.tvMaxPriceRange);
        llPriceTouch = findViewById(R.id.llPriceTouch);
        tvClearAll = findViewById(R.id.tvClearAll);
        btnClose = findViewById(R.id.btnClose);
        ivItemSizeIcon = findViewById(R.id.ivItemSizeIcon);
        ivItemColorIcon = findViewById(R.id.ivItemColorIcon);
        ivItemPriceIcon = findViewById(R.id.ivItemPriceIcon);
       // crystalRangeSeekbar = findViewById(R.id.rangeSeekbar3);
        seekbar_distance = findViewById(R.id.seekbar_distance);
        llItemSize = findViewById(R.id.llItemSize);
        llItemColor = findViewById(R.id.llItemColor);
        llItemPrice = findViewById(R.id.llItemPrice);
        llSearchlayout = findViewById(R.id.llSearchlayout);

        seekbar_distance.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                String mainChapterNumber = String.valueOf(leftValue).split("\\.", 2)[0];
                String mainChapterNumber1 = String.valueOf(rightValue).split("\\.", 2)[0];
                tvMinPriceRange.setText(mainChapterNumber + "");
                tvMaxPriceRange.setText(mainChapterNumber1 + "");
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });


        if (session.getFilterMaxPrice() != null && !session.getFilterMaxPrice().equals("")) {
            tvMaxPriceRange.setText(session.getFilterMaxPrice());
            seekbar_distance.setValue(Float.parseFloat(session.getFilterMinPrice()), Float.parseFloat(session.getFilterMaxPrice()));
        } else {
            seekbar_distance.setRange(minPrice,maxPrice);
            seekbar_distance.setValue(minPrice, maxPrice);

        }
        if (session.getFilterMinPrice() != null && !session.getFilterMinPrice().equals("")) {
            tvMinPriceRange.setText(session.getFilterMinPrice());
        }

     /*   crystalRangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                //  tvMinPriceRange.setText(minValue + "");
                //  tvMaxPriceRange.setText(maxValue + "");
                Log.e("The value is", "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);
            }
        });*/

        llItemSize.setOnClickListener(this);
        llItemColor.setOnClickListener(this);
        llItemPrice.setOnClickListener(this);
        btnApply.setOnClickListener(this);
        tvClearAll.setOnClickListener(this);
        btnClose.setOnClickListener(this);
    }

 /*   public void finViewById(ArrayList<View> viewList,ArrayList<Integer> viewIdList ){
        if (viewList.size()==viewIdList.size()){
            for (int i=0;i<viewList.size();i++){
                viewList.get(i).findViewById(viewIdList.get(i));
            }
        }
    }*/

    private void setPriceData() {
        final Typeface semi_bold = ResourcesCompat.getFont(FiltersActivity.this, R.font.ibmplexsans_semibold);
        final Typeface regular = ResourcesCompat.getFont(FiltersActivity.this, R.font.ibmplexsans_regular);

        llItemColor.setBackgroundColor(Color.parseColor("#f9f7f7"));
        tvItemColor.setTextColor(Color.BLACK);
        tvItemColor.setTypeface(regular);
        ivItemColorIcon.setColorFilter(Color.BLACK);
        //ivItemColorArrow.setVisibility(View.GONE);

        llItemSize.setBackgroundColor(Color.parseColor("#f9f7f7"));
        tvItemSize.setTextColor(Color.BLACK);
        tvItemSize.setTypeface(regular);
        ivItemSizeIcon.setColorFilter(Color.BLACK);
        //ivItemSizeArrow.setVisibility(View.GONE);

        llItemPrice.setBackgroundColor(Color.parseColor("#fe682e"));
        tvItemPrice.setTextColor(Color.WHITE);
        tvItemPrice.setTypeface(semi_bold);
        ivItemPriceIcon.setColorFilter(Color.WHITE);
        //ivItemPriceArrow.setVisibility(View.VISIBLE);
        llPriceTouch.setVisibility(View.VISIBLE);
        recyclerViewColor.setVisibility(View.GONE);
        // llSearchlayout.setVisibility(View.GONE);
    }

    private void setSizeData(ArrayList<String> sizeb) {
        final Typeface semi_bold = ResourcesCompat.getFont(FiltersActivity.this, R.font.ibmplexsans_semibold);
        final Typeface regular = ResourcesCompat.getFont(FiltersActivity.this, R.font.ibmplexsans_regular);

        variantAdapter = new VariantAdapter(sizeb, new ClickListener.FilterClickListener() {
            @Override
            public void onSizeClick(String size) {
                System.out.println("adapter........size added SIZE" + size);
                sb.add(size);
            }

            @Override
            public void onColorClick(String color) {
                System.out.println("adapter........color added SIZE" + color);
                sb1.add(color);
            }

            @Override
            public void onSizeRemoveClick(String size) {
                if (sb.size() > 0) {

                    sb.remove(size);
                    System.out.println("adapter........size removed SIZE" + size);

                }
            }

            @Override
            public void onColorRemoveClick(String color) {
                if (sb1.size() > 0) {
                    sb1.remove(color);
                    System.out.println("adapter........color removed SIZE" + color);
                }
            }
        }, sizeModelList, FiltersActivity.this, sizeName);
        recyclerViewColor.setLayoutManager(new LinearLayoutManager(FiltersActivity.this));
        recyclerViewColor.setAdapter(variantAdapter);
        variantAdapter.notifyDataSetChanged();

        llItemSize.setBackgroundColor(Color.parseColor("#fe682e"));
        tvItemSize.setTextColor(Color.WHITE);
        tvItemSize.setTypeface(semi_bold);
        ivItemSizeIcon.setColorFilter(Color.WHITE);
        //ivItemSizeArrow.setVisibility(View.VISIBLE);

        llItemColor.setBackgroundColor(Color.parseColor("#f9f7f7"));
        tvItemColor.setTextColor(Color.BLACK);
        tvItemColor.setTypeface(regular);
        ivItemColorIcon.setColorFilter(Color.BLACK);
        //ivItemColorArrow.setVisibility(View.GONE);


        llItemPrice.setBackgroundColor(Color.parseColor("#f9f7f7"));
        tvItemPrice.setTextColor(Color.BLACK);
        tvItemPrice.setTypeface(regular);
        ivItemPriceIcon.setColorFilter(Color.BLACK);
        //ivItemPriceArrow.setVisibility(View.GONE);
        llPriceTouch.setVisibility(View.GONE);

        recyclerViewColor.setVisibility(View.VISIBLE);
        //llSearchlayout.setVisibility(View.VISIBLE);
    }

    private void setColorData(ArrayList<String> colorb) {
        //  llSearchlayout.setVisibility(View.VISIBLE);
        final Typeface semi_bold = ResourcesCompat.getFont(FiltersActivity.this, R.font.ibmplexsans_semibold);
        final Typeface regular = ResourcesCompat.getFont(FiltersActivity.this, R.font.ibmplexsans_regular);

        variantAdapter = new VariantAdapter(colorb, new ClickListener.FilterClickListener() {
            @Override
            public void onSizeClick(String size) {
                System.out.println("adapter........size added COLOR" + size);
                sb.add(size);
            }

            @Override
            public void onColorClick(String color) {
                System.out.println("adapter........color added COLOR" + color);
                sb1.add(color);

            }

            @Override
            public void onSizeRemoveClick(String size) {
                if (sb.size() > 0) {
                    sb.remove(size);
                    System.out.println("adapter........size removed COLOR" + size);
                }
            }

            @Override
            public void onColorRemoveClick(String color) {
                if (sb1.size() > 0) {
                    sb1.remove(color);
                    System.out.println("adapter........color removed COLOR" + color);
                }
            }
        }, sizeModelList, FiltersActivity.this, colorName);
        recyclerViewColor.setLayoutManager(new LinearLayoutManager(FiltersActivity.this));
        recyclerViewColor.setAdapter(variantAdapter);
        variantAdapter.notifyDataSetChanged();

        llItemColor.setBackgroundColor(Color.parseColor("#fe682e"));
        tvItemColor.setTextColor(Color.WHITE);
        tvItemColor.setTypeface(semi_bold);
        ivItemColorIcon.setColorFilter(Color.WHITE);
        //ivItemColorArrow.setVisibility(View.VISIBLE);

        llItemSize.setBackgroundColor(Color.parseColor("#f9f7f7"));
        tvItemSize.setTextColor(Color.BLACK);
        tvItemSize.setTypeface(regular);
        ivItemSizeIcon.setColorFilter(Color.BLACK);
        //ivItemSizeArrow.setVisibility(View.GONE);

        llItemPrice.setBackgroundColor(Color.parseColor("#f9f7f7"));
        tvItemPrice.setTextColor(Color.BLACK);
        tvItemPrice.setTypeface(regular);
        ivItemPriceIcon.setColorFilter(Color.BLACK);
        //ivItemPriceArrow.setVisibility(View.GONE);
        llPriceTouch.setVisibility(View.GONE);

        recyclerViewColor.setVisibility(View.VISIBLE);

    }

    @Override
    public void onSuccessVariantList(final VarientListResponse varientListResponse) {
        sizeModelList.clear();
        tvItemSize.setText(varientListResponse.getData().getVariant().get(0).getVariant_name());
        tvItemColor.setText(varientListResponse.getData().getVariant().get(1).getVariant_name());

        sizeName = varientListResponse.getData().getVariant().get(0).getVariant_name();
        colorName = varientListResponse.getData().getVariant().get(1).getVariant_name();

        sizeModelList.addAll(varientListResponse.getData().getVariant());

        if (session.getFilterSizeIds() != null && !session.getFilterSizeIds().equals("")) {
            String sIds = session.getFilterSizeIds();
            sb = new ArrayList<>(Arrays.asList(sIds.split(",")));
            setSizeData(sb);
        } else {
            setSizeData(sb);
        }
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
        switch (view.getId()) {
            case R.id.llItemPrice:
                //llSearchlayout.setVisibility(View.GONE);
                setPriceData();
                break;
            case R.id.llItemColor:
               // llSearchlayout.setVisibility(View.VISIBLE);
                variantAdapter.setDataVarient(colorName);
                variantAdapter.notifyDataSetChanged();
                if (session.getFilterColorIds() != null && !session.getFilterColorIds().equals("")) {
                    String cIds = session.getFilterColorIds();
                    sb1 = new ArrayList<>(Arrays.asList(cIds.split(",")));
                    setColorData(sb1);
                } else {
                    setColorData(sb1);
                }
                break;
            case R.id.llItemSize:
                //llSearchlayout.setVisibility(View.VISIBLE);
                variantAdapter.setDataVarient(sizeName);
                variantAdapter.notifyDataSetChanged();
                setSizeData(sb);
                break;
            case R.id.btnApply:
                /* For Sizes */
                stringBuilder = new StringBuilder();
                if (sb.size() != 0) {
                    //ArrayList<String> values=new ArrayList<String>();
                    hashSet = new HashSet<>();
                    hashSet.addAll(sb);
                    sb.clear();
                    sb.addAll(hashSet);

                    /////////////STRING BUILDER START/////////////////
                    for (int i = 0; i < sb.size(); i++) {
                        stringBuilder.append(sb.get(i));
                        stringBuilder.append(",");
                    }
                    sizeIds = stringBuilder.toString();
                    /////////////STRING BUILDER END/////////////////
                } else {
                    sizeIds = "";
                }

                if (sizeIds.endsWith(",")) {
                    sizeIds = sizeIds.substring(0, sizeIds.length() - 1);
                    System.out.println("adapter........ye ids hai size ki " + sizeIds);
                }

                /* For Color */
                stringBuilder1 = new StringBuilder();
                if (sb1.size() > 0) {
                    hashSet1 = new HashSet<String>();
                    hashSet1.addAll(sb1);
                    sb1.clear();
                    sb1.addAll(hashSet1);
                    for (int i = 0; i < sb1.size(); i++) {
                        stringBuilder1.append(sb1.get(i));
                        stringBuilder1.append(",");
                    }
                    colorIds = stringBuilder1.toString();

                } else {
                    colorIds = "";
                }

                if (colorIds.endsWith(",")) {
                    colorIds = colorIds.substring(0, colorIds.length() - 1);
                    System.out.println("adapter........ye ids hai color ki " + colorIds);
                }

                /*Set Data Filter on SharedPref*/
                session.setFilterColorIds(colorIds);
                session.setFilterSizeIds(sizeIds);

                prizeLow = tvMinPriceRange.getText().toString().trim();
                priceHigh = tvMaxPriceRange.getText().toString().trim();
                session.setFilterPrice(prizeLow, priceHigh);

                Intent intent = new Intent(FiltersActivity.this, CoatsActivity.class);
                intent.putExtra("sizeIds", sizeIds);
                intent.putExtra("colorIds", colorIds);
                intent.putExtra("prizeLow", prizeLow);
                intent.putExtra("priceHigh", priceHigh);
                intent.putExtra("childCategoryName", childCatName);
                intent.putExtra("myCategoryId", myCatId);
                intent.putExtra("dealId", dealId);
                intent.putExtra("lowPrice", lowPrice);
                intent.putExtra("highPrice", highPrice);
                intent.putExtra("latest", latest);
                intent.putExtra("popularity", popularity);
                intent.putExtra("average_rating", average_rating);
                intent.putExtra("minPrice", Integer.parseInt(prizeLow));
                intent.putExtra("maxPrice", Integer.parseInt(priceHigh));
                startActivity(intent);
                finish();
                break;

            case R.id.tvClearAll:
                String min=session.getProductDetailMinValue();
                String max=session.getProductDetailMaxValue();
               // llSearchlayout.setVisibility(View.VISIBLE);
                tvMinPriceRange.setText(session.getProductDetailMinValue());
                tvMaxPriceRange.setText(session.getProductDetailMaxValue());
                seekbar_distance.setValue(Float.parseFloat(min), Float.parseFloat(max));
                //crystalRangeSeekbar.setMinValue(Float.parseFloat(min));
                //crystalRangeSeekbar.setMaxValue(Float.parseFloat(max));
                session.setFilterPrice(null, null);
                session.setFilterSizeIds(null);
                session.setFilterColorIds(null);
                sb.clear();
                sb1.clear();
                setSizeData(sb);
                variantAdapter.notifyDataSetChanged();

                break;
                case R.id.btnClose:
               onBackPressed();
                break;
        }
    }
}