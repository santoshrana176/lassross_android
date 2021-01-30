package com.mindiii.lasross.module.cart.model

data class CartClearAllResponse(
        val `data`: ClearCartData,
        val message: String,
        val status: String
)

data class ClearCartData(
        val cart_list: List<Any>,
        val pricing_detail: ClearCartPricingDetail
)

data class ClearCartPricingDetail(
        val currency_code: String,
        val currency_symbol: String,
        val total_amount: String,
        val total_item: String
)