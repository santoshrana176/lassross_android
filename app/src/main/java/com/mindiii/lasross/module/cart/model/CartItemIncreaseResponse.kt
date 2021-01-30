package com.mindiii.lasross.module.cart.model

data class CartItemIncreaseResponse(
        val `data`: Datas,
        val message: String,
        val status: String
)

data class Datas(
        val cart_list: CartList,
        val pricing_detail: PricingDetails
)

data class PricingDetails(
        val currency_code: String,
        val currency_symbol: String,
        val total_amount: String,
        val total_item: String
)

data class CartList(
        val cartItemId: String,
        val cart_item_quantity: String,
        val created_on: String,
        val product_id: String,
        val updated_on: String,
        val user_id: String,
        val variant_value_id: String
)