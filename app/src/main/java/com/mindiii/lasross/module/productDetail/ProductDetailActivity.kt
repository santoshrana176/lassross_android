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
import android.os.SystemClock
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindiii.lasross.R
import com.mindiii.lasross.app.session.Session
import com.mindiii.lasross.base.ApiCallback
import com.mindiii.lasross.base.ClickListener.SimilarProductClickListener
import com.mindiii.lasross.base.LasrossParentKotlinActivity
import com.mindiii.lasross.module.allreviews.AllReviewsActivity19
import com.mindiii.lasross.module.cart.MyCartActivity
import com.mindiii.lasross.module.cart.model.AddTocartResponse
import com.mindiii.lasross.module.cart.presenter.AddToCartPresenter
import com.mindiii.lasross.module.home.model.AddRemoveWishListResponse
import com.mindiii.lasross.module.productDetail.adapter.SimilarProductKotlinAdapter
import com.mindiii.lasross.module.productDetail.adapter.ViewPagerAdapter
import com.mindiii.lasross.module.productDetail.model.*
import com.mindiii.lasross.module.productDetail.presenter.ProductDetailPresenter
import kotlinx.android.synthetic.main.activity_product_detail.*
import java.util.*
import kotlin.collections.ArrayList


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ProductDetailActivity : LasrossParentKotlinActivity(), ApiCallback.ProductDetailCallback, ApiCallback.AddToCartCallback, View.OnClickListener
{
    lateinit var similarProduct: List<SimilarProduct>
    private lateinit var availableQuantity: String
    private lateinit var varientlist: List<Variant>
    private lateinit var variantSizeValue: ArrayList<VariantValue>
    private lateinit var variantColorValue: ArrayList<VariantValue>
    private lateinit var galleryImage: ArrayList<GalleryImage>
    lateinit var productId: String
    lateinit var productDesc: String
    lateinit var productSku: String
    lateinit var productCategory: String
    private var quantity: Int = 1
    private var index: Int = 0
    private var productQuantity = ""
    private var variants = ""
    private var selectedColorId = ""
    private var selectedSizeId = ""
    private var isCheckWish: Boolean = true
    private var mLastClickTime: Long = 0
    private var similarProductAdapter: SimilarProductKotlinAdapter? = null
    private var viewPagerAdapter: ViewPagerAdapter? = null
    private lateinit var session: Session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        session = Session(this)
        val window = this.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = this.resources.getColor(R.color.white)
        }

        /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
               getWindow().statusBarColor=ContextCompat.getColor(this ,R.color.white)
           }*/

        if (session.cartItemCount.equals("0", ignoreCase = true))
            tvCartItemCountProductDetail.setVisibility(View.GONE)
        else {
            tvCartItemCountProductDetail.setVisibility(View.VISIBLE)
            tvCartItemCountProductDetail.setText(session.cartItemCount)
        }
        //tvCartItemCountProductDetail.text = session.cartItemCount
       if( intent.getStringExtra("productId")!=null)
        productId = intent.getStringExtra("productId")
        similarProduct = ArrayList()
        varientlist = ArrayList()
        variantSizeValue = ArrayList()
        variantColorValue = ArrayList()
        galleryImage = ArrayList()

        tvMoveToBag.setOnClickListener(this)
        tvPlus.setOnClickListener(this)
        tvMinus.setOnClickListener(this)
        ivAddWish.setOnClickListener(this)
        ivAlreadyAddWish.setOnClickListener(this)
        llAddToCart.setOnClickListener(this)
        ibBagIcon.setOnClickListener(this)
        ivProductDetailBack.setOnClickListener(this)
        llProductDetailRating.setOnClickListener(this)
     //   apiCalling()
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

        /*Size Adapter*/
        /*val sizeArray = ArrayList<String>()
        for(n in 0..variantSizeValue.size-1) {
            sizeArray.add(variantSizeValue.get(n).variant_value)
        }*/

        val sizeAdapter = ArrayAdapter(this, R.layout.size_adapter_layout, variantSizeValue.toMutableList())
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinSize.adapter = sizeAdapter

        spinSize.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedSizeId = variantSizeValue[position].variantValueId

            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        /*Color Adapter*/
        /*val colorArray = ArrayList<String>()
        for(n in 0..variantColorValue.size-1) {
            colorArray.add(variantColorValue.get(n).variant_value)
        }*/

        val spinnerAdapter = ArrayAdapter(this, R.layout.color_adapter_layout, variantColorValue.toMutableList())
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinColor.adapter = spinnerAdapter

        spinColor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedColorId = variantColorValue[position].variantValueId
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
        val sizeVal = VariantValue("0", "0", "Select Size")
        variantSizeValue.add(0, sizeVal)
        variantColorValue = productDetailResponse.data.product_detail.variant[1].variant_value as ArrayList<VariantValue>
        val colorVal = VariantValue("0", "0", "Select Color")
        variantColorValue.add(0, colorVal)

        galleryImage = productDetailResponse.data.product_detail.gallery_images as ArrayList<GalleryImage>
        val galleryImages = GalleryImage("0", productDetailResponse.data.product_detail.product_image_original, "0")
        galleryImage.add(0, galleryImages)

        setAdapter()
        similarProductAdapter!!.notifyDataSetChanged()
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
                    val message = "We have only $availableQuantity Qty for this product"
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
                val intent = Intent(this@ProductDetailActivity, AllReviewsActivity19::class.java)
                intent.putExtra("productId", productId)
                startActivity(intent)
            }
        }
    }

    private fun callAddToProductApi() {
        when {
            selectedSizeId == "0" -> toastMessage("Please select size")
            selectedColorId == "0" -> toastMessage("Please select color")
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