package com.mindiii.lasross.module.activeplan.model

data class CancelSubscriptionResponse(
        val `data`: Data,
        val status: String,
        val message: String = ""
)

data class Data(
        val application_fee_percent: Any,
        val billing: String,
        val billing_cycle_anchor: Int,
        val billing_thresholds: Any,
        val cancel_at: Any,
        val cancel_at_period_end: Boolean,
        val canceled_at: Int,
        val collection_method: String,
        val created: Int,
        val current_period_end: Int,
        val current_period_start: Int,
        val customer: String,
        val days_until_due: Any,
        val default_payment_method: Any,
        val default_source: Any,
        val default_tax_rates: List<Any>,
        val discount: Any,
        val ended_at: Int,
        val id: String,
        val invoice_customer_balance_settings: InvoiceCustomerBalanceSettings,
        val items: Items,
        val latest_invoice: String,
        val livemode: Boolean,
        val metadata: List<Any>,
        val next_pending_invoice_item_invoice: Any,
        val `object`: String,
        val pending_invoice_item_interval: Any,
        val pending_setup_intent: Any,
        val plan: PlanX,
        val quantity: Int,
        val schedule: Any,
        val start: Int,
        val start_date: Int,
        val status: String,
        val tax_percent: Any,
        val trial_end: Any,
        val trial_start: Any
)

data class InvoiceCustomerBalanceSettings(
        val consume_applied_balance_on_void: Boolean
)

data class Items(
        val `data`: List<DataX>,
        val has_more: Boolean,
        val `object`: String,
        val total_count: Int,
        val url: String
)

data class DataX(
        val billing_thresholds: Any,
        val created: Int,
        val id: String,
        val metadata: List<Any>,
        val `object`: String,
        val plan: Plan,
        val quantity: Int,
        val subscription: String,
        val tax_rates: List<Any>
)

data class Plan(
        val active: Boolean,
        val aggregate_usage: Any,
        val amount: Int,
        val amount_decimal: String,
        val billing_scheme: String,
        val created: Int,
        val currency: String,
        val id: String,
        val interval: String,
        val interval_count: Int,
        val livemode: Boolean,
        val metadata: List<Any>,
        val nickname: String,
        val `object`: String,
        val product: String,
        val tiers: Any,
        val tiers_mode: Any,
        val transform_usage: Any,
        val trial_period_days: Any,
        val usage_type: String
)

data class PlanX(
        val active: Boolean,
        val aggregate_usage: Any,
        val amount: Int,
        val amount_decimal: String,
        val billing_scheme: String,
        val created: Int,
        val currency: String,
        val id: String,
        val interval: String,
        val interval_count: Int,
        val livemode: Boolean,
        val metadata: List<Any>,
        val nickname: String,
        val `object`: String,
        val product: String,
        val tiers: Any,
        val tiers_mode: Any,
        val transform_usage: Any,
        val trial_period_days: Any,
        val usage_type: String
)