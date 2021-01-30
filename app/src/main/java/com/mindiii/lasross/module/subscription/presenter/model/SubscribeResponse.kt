package com.mindiii.lasross.module.subscription.presenter.model

import java.io.Serializable

data class SubscribeResponse(
        val `data`: Data,
        val status: String,
        val message: String = ""
) : Serializable

data class Data(
        val created_on: String,
        val end_date: String,
        val is_cancelled: String,
        val is_lifetime: String,
        val payment_platform_type: String,
        val plan_currency: String,
        val plan_description: String,
        val plan_duration: String,
        val plan_duration_type: String,
        val plan_level: String,
        val plan_price: String,
        val plan_title: String,
        val platform_package_name: String,
        val platform_product_id: String,
        val platform_type: String,
        val purchase_amount: String,
        val purchase_details: String,
        val purchase_duration: String,
        val purchase_duration_type: String,
        val purchase_id: String,
        val purchase_token: String,
        val start_date: String,
        val status: String,
        val stripe_plan_id: String,
        val stripe_product_id: String,
        val subscriptionPlanId: String,
        val subscription_plan_id: String,
        val updated_on: String,
        val userSubscriptionId: String,
        val user_id: String
) : Serializable