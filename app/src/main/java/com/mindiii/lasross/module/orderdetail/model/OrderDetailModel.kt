package com.mindiii.lasross.module.orderdetail.model

import java.io.Serializable

data class OrderDetailModel(
        val orderDetail: OrderDetail,
        val status: String
) : Serializable

data class OrderDetail(
        val order_status: String,
        val created_on: String,
        val discount_amount: String,
        val discount_percent: String,
        val discounted_subtotal: String,
        val free_shipping: String,
        val grand_total: String,
        val item_total: String,
        val orderId: String,
        val order_address: OrderAddress,
        val order_current_status: String,
        val order_number: String,
        val order_type: String,
        val ordered_by_user_id: String,
        val payment_mode: String,
        val payment_status: String,
        val products: List<Product>,
        val shipping_price: String,
        val tax_amount: String,
        val tax_percentage: String,
        val tracking_status: List<TrackingStatu>,
        val updated_on: String
        //val free_shipping: String
) : Serializable

data class OrderAddress(
        val created_on: String,
        val orderAddressId: String,
        val order_bill_address_city: String,
        val order_bill_address_company_name: String,
        val order_bill_address_country: String,
        val order_bill_address_email: String,
        val order_bill_address_first_name: String,
        val order_bill_address_house_number: String,
        val order_bill_address_last_name: String,
        val order_bill_address_latitude: String,
        val order_bill_address_locality: String,
        val order_bill_address_location: String,
        val order_bill_address_longitude: String,
        val order_bill_address_phone: String,
        val order_bill_address_zip_code: String,
        val order_id: String,
        val order_ship_address_city: String,
        val order_ship_address_company_name: String,
        val order_ship_address_country: String,
        val order_ship_address_first_name: String,
        val order_ship_address_house_number: String,
        val order_ship_address_last_name: String,
        val order_ship_address_latitude: String,
        val order_ship_address_locality: String,
        val order_ship_address_location: String,
        val order_ship_address_longitude: String,
        val order_ship_address_zip_code: String,
        val updated_on: String
) : Serializable

data class Product(
        val order_status: String,
        val category_name: String,
        val collection_type_id: String,
        val description: String,
        val in_stock: String,
        val item_quantity: String,
        val orderItemId: String,
        val productFeatureImage: String,
        val productId: String,
        val product_available_quantity: String,
        val product_description: String,
        val product_feature_image: String,
        val product_name: String,
        val product_sku: String,
        val rating: String,
        val regular_price: String,
        val sale_price: String,
        val status: String,
        val variant_value: String,
        val variant_value_id: String,
        val variants: List<Variant>
) : Serializable

data class Variant(
        val value: String,
        val variantId: String,
        val variant_name: String
) : Serializable

data class TrackingStatu(
        val created_on: String,
        val orderTrackingId: String,
        val order_id: String,
        val order_status: String,
        val order_status_text: String,
        val order_status_title: String,
        val updated_on: String,
        val order_tracking_number: String

) : Serializable
