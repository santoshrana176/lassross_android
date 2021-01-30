package com.mindiii.lasross.module.myorder.model


data class MyOrdersModel(
        val orderList: List<Order>,
        val status: String
)

data class Order(
        val created_on: String,
        val orderId: String,
        val order_current_status: String,
        val order_number: String,
        val ordered_by_user_id: String,
        val products: Products
)

data class Products(
        val category_name: String,
        val productFeatureImage: String?,
        val productId: String,
        val AverageRating: String?,
        val description: String,
        val product_feature_image: String,
        val product_name: String,
        val rating: String?


        /*
  "AverageRating": null,
 "description": null,
 "productId": "38",
 "productFeatureImage": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/rkUT2b9iLDPsgqKO.jpg"
 */
)