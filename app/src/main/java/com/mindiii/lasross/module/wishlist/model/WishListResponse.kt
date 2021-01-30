package com.mindiii.lasross.module.wishlist.model

data class WishListResponse(
        val `data`: Data,
        val message: String,
        val status: String
)

data class Data(
        val total_records: String,
        val user_wishlist: List<UserWishlist>
)

data class UserWishlist(
        val AverageRating: String?,
        val category_id: String,
        val category_name: String,
        val collection_type_id: String,
        val created_on: String,
        val currency_code: String,
        val currency_symbol: String,
        val description: String,
        val in_stock: String,
        val productId: String,
        val product_image: String,
        val product_name: String,
        val product_sku: String,
        val rating: String,
        val regular_price: String,
        val sale_price: String,
        val status: String,
        val updated_on: String
)