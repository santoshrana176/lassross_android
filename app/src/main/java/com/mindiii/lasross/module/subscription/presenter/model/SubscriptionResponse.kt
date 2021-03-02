package com.mindiii.lasross.module.subscription

data class SubscriptionResponse(
        var `data`: List<Data>,
        val status: String
)

data class Data(
        val created_on: String,
        val is_lifetime: String,
        val plan_currency: String,
        val plan_description: String,
        val plan_duration: String,
        val plan_duration_type: String,
        val plan_level: String,
        val plan_price: String,
        val plan_title: String,
        val status: String,
        val stripe_plan_id: String,
        val stripe_product_id: String,
        val subscriptionPlanId: String,
        val updated_on: String,
        var imageIdActive: Int,
        var imageIdDeActive: Int
)