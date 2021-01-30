package com.mindiii.lasross.module.delivery.model


data class DeliveryResponse(
        val `data`: Data,
        val status: String
)

data class Data(
        val discount_percent: String,
        val free_shipping: String,
        val grand_total: String,
        val itemCount: Int,
        val subtotal: String,
        val subtotal_before_discount: String,
        val tax_amount: String,
        val tax_percent: String,
        val totalMile: String,
        val total_discount: String,
        val total_shipping_price: String
)