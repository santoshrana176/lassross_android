package com.mindiii.lasross.module.home.model

data class DealListResponse(
        val count: String,
        val deal_list: List<Deal>,
        val message: String,
        val status: String
)

data class Deal(
        val currency_code: String,
        val currency_symbol: String,
        val dealId: String,
        val deal_image: String,
        val deal_sub_title: String,
        val deal_title: String
)