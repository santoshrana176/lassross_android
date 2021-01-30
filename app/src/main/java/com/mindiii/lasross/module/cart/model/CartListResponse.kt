package com.mindiii.lasross.module.cart.model

data class CartListResponse(
        val `data`: Data,
        val message: String,
        val status: String
)

data class Data(
        val cart_list: List<Cart>,
        val pricing_detail: PricingDetail,
        val shipping_detail: ShippingDetail,
        val total_records: String
)

data class Cart(
        val cartItemId: String,
        var cart_item_quantity: String,
        val category_id: String,
        val category_name: String,
        val collection_type_id: String,
        val created_on: String,
        val currency_code: String,
        val currency_symbol: String,
        val in_stock: String,
        val productId: String,
        val product_available_quantity: String,
        val product_image: String,
        val product_name: String,
        val product_sku: String,
        val regular_price: String,
        val sale_price: String,
        val status: String,
        val updated_on: String,
        val variant: List<Variant>,
        val variant_id: String,
        val variant_value: String,
        val variant_value_id: String
)

data class Variant(
        val variantId: String,
        val variant_name: String,
        val variant_value: VariantValue
)

data class VariantValue(
        val variantValueId: String,
        val variant_value: String
)

data class PricingDetail(
        val currency_code: String,
        val currency_symbol: String,
        val total_amount: String,
        val total_item: String
)

data class ShippingDetail(
        val home_banner_image: String,
        val plan_level_benifits: String,
        val shipping_price: String,
        val shipping_price_status: String,
        val tax: String,
        val warehouse_address: String,
        val warehouse_latitude: String,
        val warehouse_longitude: String
)

/*
data class CartListResponse(
    val `data`: Data,
    val message: String,
    val status: String
)

data class Data(
    val cart_list: List<Cart>,
    val pricing_detail: PricingDetail,
    val shipping_detail: ShippingDetail,
    val total_records: String
)

data class PricingDetail(
    val currency_code: String,
    val currency_symbol: String,
    val total_amount: String,
    val total_item: String
)

data class ShippingDetail(
    val shipping_price: String,
    val shipping_price_status: String,
    val tax: String,
    val warehouse_address: String,
    val warehouse_latitude: String,
    val warehouse_longitude: String
)

data class Cart(
        val cartItemId: String,
        var cart_item_quantity: String,
        val category_id: String,
        val category_name: String,
        val collection_type_id: String,
        val created_on: String,
        val currency_code: String,
        val currency_symbol: String,
        val in_stock: String,
        val productId: String,
        val product_available_quantity: String,
        val product_image: String,
        val product_name: String,
        val product_sku: String,
        val regular_price: String,
        val sale_price: String,
        val status: String,
        val updated_on: String,
        val variant: List<Variant>,
        val variant_id: String,
        val variant_value: String,
        val variant_value_id: String,
        val out_of_stock: String
)

data class Variant(
    val variantId: String,
    val variant_name: String,
    val variant_value: VariantValue
)

data class VariantValue(
    val variantValueId: String,
    val variant_value: String
)*/
