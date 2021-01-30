package com.mindiii.lasross.module.cart.model

data class DeleteCartItemResponse(
        val `data`: DeleteCartData,
        val message: String,
        val status: String
)

data class DeleteCartData(
        val cart_list: List<DeleteCartItem>,
        val pricing_detail: DeleteCartPricingDetail,
        val total_records: String
)

data class DeleteCartPricingDetail(
        val currency_code: String,
        val currency_symbol: String,
        val total_amount: String,
        val total_item: String
)

data class DeleteCartItem(
        val cartItemId: String,
        val cart_item_quantity: String,
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
        val variant: List<DeleteCartVariant>,
        val variant_id: String,
        val variant_value: String,
        val variant_value_id: String
)

data class DeleteCartVariant(
        val variantId: String,
        val variant_name: String,
        val variant_value: DeleteCartVariantValue
)

data class DeleteCartVariantValue(
        val variantValueId: String,
        val variant_value: String
)