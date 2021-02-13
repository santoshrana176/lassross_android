package com.mindiii.lasross.module.settings.model

data class SliderBannerResponse(
    val `data`: Data,
    val status: String
)

data class Data(
    val banner_silder: List<BannerSilder>,
    val banner_silder_path: String,
    val weeklyOfferData: List<Any>
)

data class BannerSilder(
    val banner_image: String,
    val description: String,
    val title: String
)