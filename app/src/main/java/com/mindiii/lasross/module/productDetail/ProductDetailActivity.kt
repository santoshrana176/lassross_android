package com.mindiii.lasross.module.productDetail

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.text.method.ScrollingMovementMethod
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.ClickListener.SimilarProductClickListener
import com.mindiii.lasross.base.LasrossParentKotlinActivity
import com.mindiii.lasross.module.allreviews.AllReviewsActivity
import com.mindiii.lasross.module.cart.MyCartActivity
import com.mindiii.lasross.module.cart.model.AddTocartResponse
import com.mindiii.lasross.module.cart.presenter.AddToCartPresenter
import com.mindiii.lasross.module.home.model.AddRemoveWishListResponse
import com.mindiii.lasross.module.productDetail.adapter.CustomeSpinnerAdapter
import com.mindiii.lasross.module.productDetail.adapter.SimilarProductKotlinAdapter
import com.mindiii.lasross.module.productDetail.adapter.ViewPagerAdapter
import com.mindiii.lasross.module.productDetail.model.*
import com.mindiii.lasross.module.productDetail.presenter.ProductDetailPresenter
import kotlinx.android.synthetic.main.activity_product_detail.*
import java.util.*
import kotlin.collections.ArrayList


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ProductDetailActivity : LasrossParentKotlinActivity(),
        ApiCallback.ProductDetailCallback,
        ApiCallback.AddToCartCallback,
        View.OnClickListener {
    lateinit var similarProduct: List<SimilarProduct>
    private lateinit var availableQuantity: String
    private lateinit var varientlist: List<Variant>
    private lateinit var variantSizeValue: ArrayList<VariantValue>
    private lateinit var variantColorValue: ArrayList<VariantValue>
    private lateinit var galleryImage: ArrayList<GalleryImage>
    lateinit var productId: String
      var productDesc: String=""
    lateinit var productSku: String
    lateinit var productCategory: String
    private var quantity: Int = 1
    private var index: Int = 0
    private var productQuantity = ""
    private var variants = ""
    private var selectedColorId = "0"
    private var selectedSizeId = "0"
    private var isCheckWish: Boolean = true
    private var mLastClickTime: Long = 0
    private var similarProductAdapter: SimilarProductKotlinAdapter? = null
    private var viewPagerAdapter: ViewPagerAdapter? = null
    private lateinit var session: Session
    var width = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        session = Session(this)
        //showSystemUI()
     //   window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        /*this?.window?.decorView?.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE
        }*/
       /* Handler().postDelayed({
           this.window.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    statusBarColor = Color.TRANSPARENT
                }
            }
        },5000)
*/
        /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
             val window: Window = window
             window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
             window.setStatusBarColor(Color.BLACK)
         }

          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }*/
        //Display display = context.
        // windowManager.defaultDisplay.getMetrics(displaymetrics);
        //width = displaymetrics.widthPixels
        //val params = viewPager.layoutParams
        //params.height = (1.25 * width).toInt()
        // params.width = width
        //viewPager.layoutParams = params
        val displaymetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displaymetrics)
        width = displaymetrics.widthPixels

        val params = viewPager.layoutParams
        params.height = (1.25 * width).toInt()
        params.width = width
        viewPager.layoutParams = params
        if (session.cartItemCount.equals("0", ignoreCase = true))
            tvCartItemCountProductDetail.setVisibility(View.GONE)
        else {
            tvCartItemCountProductDetail.setVisibility(View.VISIBLE)
            tvCartItemCountProductDetail.setText(session.cartItemCount)
        }
        if (intent.getStringExtra("productId") != null)
            productId = intent.getStringExtra("productId")

        similarProduct = ArrayList()
        varientlist = ArrayList()
        variantSizeValue = ArrayList()
        variantColorValue = ArrayList()
        galleryImage = ArrayList()

        tv_size.setOnClickListener(this)
        ivDropdown.setOnClickListener(this)
        tv_color.setOnClickListener(this)
        ll_size1.setOnClickListener(this)
        tvMoveToBag.setOnClickListener(this)
        tvPlus.setOnClickListener(this)
        tvMinus.setOnClickListener(this)
        ivAddWish.setOnClickListener(this)
        ivAlreadyAddWish.setOnClickListener(this)
        llAddToCart.setOnClickListener(this)
        ibBagIcon.setOnClickListener(this)
        ivProductDetailBack.setOnClickListener(this)
        llProductDetailRating.setOnClickListener(this)
        apiCalling()

    }



    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                /*or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION*/
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }




    private fun setRatingBarStart(rating_bar: RatingBar) {
        val stars = rating_bar.progressDrawable as LayerDrawable
        stars.getDrawable(2)
                .setColorFilter(getResources().getColor(R.color.star_fill_color),
                        PorterDuff.Mode.SRC_ATOP) // for filled stars
        stars.getDrawable(1)
                .setColorFilter(getResources().getColor(R.color.star_fill_color),
                        PorterDuff.Mode.SRC_ATOP) // for half filled stars
        stars.getDrawable(0)
                .setColorFilter(getResources().getColor(R.color.star_unfill_color),
                        PorterDuff.Mode.SRC_ATOP) // for empty stars
    }

    private fun apiCalling() {
        ProductDetailPresenter(this, this).callProductDetailApi(productId)
    }

    private fun setAdapter() {
        /*Similar Product Adapter*/
        rvRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvRecycler.setHasFixedSize(true)
        similarProductAdapter = SimilarProductKotlinAdapter(similarProduct, this, object : SimilarProductClickListener {
            override fun onProfileDetail(position: Int) {
                val intent = Intent(this@ProductDetailActivity, ProductDetailActivity::class.java)
                intent.putExtra("productId", similarProduct[position].productId)
                startActivity(intent)
            }

            override fun onClickToWish(position: Int) {
                index = position
                isCheckWish = false
                addRemoveApiCalling(similarProduct[position].productId)
            }

        })
        rvRecycler.adapter = similarProductAdapter
        // spiner initilization for size
        var spinSizeAdapter = CustomeSpinnerAdapter(this, variantSizeValue/*, object : CustomeSpinnerAdapter.SelectedItem {
            override fun selectedItem(variantValueId: String,name:String) {
                selectedSizeId = variantValueId
                tv_size.text=name
            }

        }*/)
        spinSize.adapter = spinSizeAdapter
        spinSize.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                 selectedSizeId = variantSizeValue[position].variantValueId
                tv_size.text = variantSizeValue[position].variant_value
            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        // val sizeAdapter = ArrayAdapter(this, R.layout.size_adapter_layout, variantSizeValue.toMutableList())
        // sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //  spinSize.setPrompt("Select Size");

        /*  spinSize.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
              override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                //  selectedSizeId = variantSizeValue[position].variantValueId
              } // to close the onItemSelected

              override fun onNothingSelected(parent: AdapterView<*>) {

              }
          }*/

        /*Color Adapter*/
        /*val colorArray = ArrayList<String>()
        for(n in 0..variantColorValue.size-1) {
            colorArray.add(variantColorValue.get(n).variant_value)
        }*/

      /*  val spinnerAdapter = ArrayAdapter(this, R.layout.color_adapter_layout, variantColorValue.toMutableList())
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // spinSize.setPrompt("Select Color");
        spinColor.adapter = spinnerAdapter

        spinColor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedColorId = variantColorValue[position].variantValueId
            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }*/
        var spinnerColorAdapter = CustomeSpinnerAdapter(this, variantColorValue/*, object : CustomeSpinnerAdapter.SelectedItem {
            override fun selectedItem(variantValueId: String,name:String) {
                selectedColorId = variantValueId
                tv_color.text=name
                spinColor.clearFocus();
            }
        }*/)
        spinColor.adapter = spinnerColorAdapter
        spinColor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedColorId = variantColorValue[position].variantValueId
               // selectedColorId = variantValueId
                tv_color.text=variantColorValue[position].variant_value
            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        /*pagerAdapter set*/
        viewPagerAdapter = ViewPagerAdapter(galleryImage, this)
        viewPager.adapter = viewPagerAdapter
        dotsIndicator.setViewPager(viewPager)
        viewPager.adapter?.registerDataSetObserver(dotsIndicator.dataSetObserver)
    }

    override fun onShowBaseLoader() {
        showLoader()
    }

    override fun onHideBaseLoader() {
        hideLoader()
    }

    override fun onError(errorMessage: String?) {
        errorMessage?.let { toastMessage(it) }
    }

    override fun onSuccessDetail(productDetailResponse: ProductDetailResponse?) {
        setData(productDetailResponse!!.data.product_detail)
        similarProduct = productDetailResponse.data.product_detail.similar_products
        availableQuantity = productDetailResponse.data.product_detail.product_available_quantity
        varientlist = productDetailResponse.data.product_detail.variant
        variantSizeValue = productDetailResponse.data.product_detail.variant[0].variant_value as ArrayList<VariantValue>
        val sizeVal = VariantValue("0", "0", "")
         variantSizeValue.add(0, sizeVal)
        variantColorValue = productDetailResponse.data.product_detail.variant[1].variant_value as ArrayList<VariantValue>
        val colorVal = VariantValue("0", "0", "")
         variantColorValue.add(0, colorVal)
         galleryImage =ArrayList() //productDetailResponse.data.product_detail.gallery_images as ArrayList<GalleryImage>
        val galleryImages = GalleryImage("0", "0", productDetailResponse.data.product_detail.product_image_original, productDetailResponse.data.product_detail.product_image_medium)
          galleryImage.add(0, galleryImages)
        if (productDetailResponse.data.product_detail.gallery_images.isNotEmpty()){
            galleryImage.addAll(productDetailResponse.data.product_detail.gallery_images)
        }

        setAdapter()
        similarProductAdapter!!.notifyDataSetChanged()
        if(similarProduct.isEmpty()){
            view_similar.visibility=View.GONE
            rvRecycler.visibility=View.GONE
            divider_similer.visibility=View.GONE
        }   else{
            view_similar.visibility=View.VISIBLE
            rvRecycler.visibility=View.VISIBLE
            divider_similer.visibility=View.VISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData(productDetail: ProductDetail?) {
        //productQuantity = productDetail?.product_available_quantity!!
        productDesc = productDetail!!.product_additional_information
        productSku = productDetail.product_sku
        productCategory = productDetail.category_name
        tvItemNameVariety.text = productDetail.category_name
        tvItemName.text = productDetail.product_name
        tvItemCurreny.text = productDetail.currency_symbol + " "
        tvDescription.text = productDetail.product_description

        productDetail.AverageRating?.let {
            ratingBarProductDetail.rating = it.toFloat()
        }

        if (productDetail.in_stock == "0") {
            llAddToCart.isClickable = false
        } else {
            llAddToCart.isClickable = true
        }

        if (productDetail.AverageRating != null) {
            val strRating = productDetail.AverageRating
            val floatRating = java.lang.Float.valueOf(strRating)

            if (floatRating % 1 > 0)
                ratingNumberProductDetail.text = "(" + getOneValueAfterDecimal(productDetail.AverageRating) + ")"
            else
                ratingNumberProductDetail.text = "(" + getOneValueAfterDecimal(productDetail.AverageRating) + ".0)"


        } else
            ratingNumberProductDetail.text = "(0.0)"

        if (productDetail.sale_price == "0.00") {
            tvItemPrice.text = productDetail.regular_price
        } else {
            tvItemPrice.text = productDetail.sale_price
        }

        if (productDetail.in_stock == "0") {
            tvProductDetailTextChange.text = getString(R.string.out_of_stock)
            //llAddToCart.visibility = View.GONE
        } else {
            tvProductDetailTextChange.text = getString(R.string.add_to_cart)
            //llAddToCart.visibility = View.VISIBLE
        }

        if (productDetail.is_wishlist == "0") {
            ivAlreadyAddWish.visibility = View.GONE
            ivAddWish.visibility = View.VISIBLE
        } else {
            ivAlreadyAddWish.visibility = View.VISIBLE
            ivAddWish.visibility = View.GONE
        }
    }

    override fun onClick(p0: View?) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return
        }
        mLastClickTime = SystemClock.elapsedRealtime()
        when (p0!!.id) {
            R.id.tvMoveToBag -> {
                openDialog(productDesc)
            }
            R.id.tvPlus -> {
                quantity = tvQuantity.text.toString().toInt()
                quantity += 1
                if (availableQuantity.toInt() < quantity) {
                    val message = getString(R.string.we_have_only) + " $availableQuantity " + getString(R.string.oty_for_this)
                    //showConfirmDialog(this, message)
                    showOnBackPressed(this, message)
                } else {
                    tvQuantity.text = quantity.toString()
                }
            }
            R.id.tvMinus -> {
                quantity = tvQuantity.text.toString().toInt()
                if (quantity != 1) {
                    quantity -= 1
                    tvQuantity.text = quantity.toString()
                }

            }
            R.id.tv_color -> {
                spinColor.performClick()
            }
            R.id.tv_size -> {
                spinSize.performClick()
            }
            /*R.id.ll_size1 -> {
                spinSize.performClick()
            }*/
            R.id.ivAddWish -> {
                ivAlreadyAddWish.visibility = View.VISIBLE
                ivAddWish.visibility = View.GONE
                addRemoveApiCalling(productId)
                isCheckWish = true
            }
            R.id.ivAlreadyAddWish -> {
                ivAlreadyAddWish.visibility = View.GONE
                ivAddWish.visibility = View.VISIBLE
                addRemoveApiCalling(productId)
                isCheckWish = true
            }
            R.id.llAddToCart -> {
                callAddToProductApi()
            }
            R.id.ibBagIcon -> {
                val intent = Intent(this@ProductDetailActivity, MyCartActivity::class.java)
                startActivity(intent)
            }
            R.id.ivProductDetailBack -> {
                onBackPressed()
            }
            R.id.llProductDetailRating -> {
                val intent = Intent(this@ProductDetailActivity, AllReviewsActivity::class.java)
                intent.putExtra("productId", productId)
                startActivity(intent)
            }
        }
    }

    private fun callAddToProductApi() {
        when {
            selectedSizeId == "0" -> toastMessage(getString(R.string.please_selelct_size))
            selectedColorId == "0" -> toastMessage(getString(R.string.please_select_color))
            else -> {
                variants = "$selectedColorId,$selectedSizeId"
                productQuantity = tvQuantity.text.toString()
                AddToCartPresenter(this, this).callAddToCartApi(productId, productQuantity, variants)
            }
        }
    }

    private fun openDialog(otherDesc: String) {
        val dialog = Dialog(this, R.style.my_dialog)
        dialog.setContentView(R.layout.dialog_artboard_18)
        Objects.requireNonNull(dialog.window)!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val ivCancel: ImageView
        val tvShortDescription: TextView
        val tvSKU: TextView
        val tvCategory: TextView
        ivCancel = dialog.findViewById(R.id.ivCancel)
        tvShortDescription = dialog.findViewById(R.id.tvShortDescription)
        tvSKU = dialog.findViewById(R.id.tvSKU)
        tvCategory = dialog.findViewById(R.id.tvCategory)
        ivCancel.setOnClickListener { dialog.dismiss() }
       tvShortDescription.text = otherDesc
        tvShortDescription.setMovementMethod(ScrollingMovementMethod())

        tvSKU.text = productSku
        tvCategory.text = productCategory
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        lp.windowAnimations = R.style.DialogAnimation
        dialog.window!!.attributes = lp
        dialog.show()
    }

    private fun addRemoveApiCalling(productId: String) {
        ProductDetailPresenter(this, this).callAddRemoveWishListApi(productId)
    }

    override fun onSuccessAddRemoveWishList(addRemoveWishListResponse: AddRemoveWishListResponse?) {
        if (!isCheckWish) {
            similarProduct[index].is_wishlist = addRemoveWishListResponse!!.is_wishlist
            similarProductAdapter!!.notifyDataSetChanged()
        }
    }

    override fun onTokenChangeError(errorMessage: String?) {
        showDialog(this)
    }

    override fun onSuccessAddToCart(addTocartResponse: AddTocartResponse?) {
        //showConfirmDialog(this, addTocartResponse!!.message)
        showOnBackPressed(this, addTocartResponse!!.message)
    }

    override fun onResume() {
        transparentStatusBar(this)
        super.onResume()
    }

    override fun onRestart() {
        super.onRestart()
        if (session.cartItemCount.equals("0", ignoreCase = true))
            tvCartItemCountProductDetail.setVisibility(View.GONE)
        else {
            tvCartItemCountProductDetail.setVisibility(View.VISIBLE)
            tvCartItemCountProductDetail.text = session.cartItemCount
        }
        //tvCartItemCountProductDetail.text = session.cartItemCount
        apiCalling()
    }
}