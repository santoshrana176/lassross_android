package com.mindiii.lasross.module.home.model

data class BannerWeeklyOfferResponse(
        val `data`: Data,
        val status: String
)

data class Data(
        val banner_image: String,
        val weeklyOfferData: List<WeeklyOfferData>
)

data class WeeklyOfferData(
        val collection_type_id: String,
        val created_on: String,
        val in_stock: String,
        val productId: String,
        val product_additional_information: String,
        val product_available_quantity: String,
        val product_description: String,
        val product_dimension: String,
        val product_feature_image: String,
        val product_id: String,
        val product_name: String,
        val product_sku: String,
        val product_weight: String,
        val regular_price: String,
        val sale_price: String,
        val status: String,
        val updated_on: String,
        val weeklyOfferItemId: String
)