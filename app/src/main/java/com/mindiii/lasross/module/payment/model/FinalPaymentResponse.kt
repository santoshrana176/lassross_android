package com.mindiii.lasross.module.payment.model

data class FinalPaymentResponse(
        val `data`: Data,
        val message: String,
        val status: String
)

data class Data(
        val order_id: Int,
        val order_number: String
)