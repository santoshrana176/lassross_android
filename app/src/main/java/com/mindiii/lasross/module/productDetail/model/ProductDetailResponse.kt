package com.mindiii.lasross.module.productDetail.model


data class ProductDetailResponse(
        val `data`: Data,
        val message: String,
        val status: String
)

data class Data(
        val product_detail: ProductDetail
)

data class ProductDetail(
        val AverageRating: String,
        val category_id: String,
        val category_name: String,
        val collection_type_id: String,
        val created_on: String,
        val currency_code: String,
        val currency_symbol: String,
        val description: Any,
        val gallery_images: List<GalleryImage>,
        val in_stock: String,
        val is_wishlist: String,
        val productId: String,
        val product_additional_information: String,
        val product_available_quantity: String,
        val product_description: String,
        val product_dimension: String,
        val product_image: String,
        val product_image_original: String,
        val product_name: String,
        val product_sku: String,
        val product_weight: String,
        val rating: Any,
        val regular_price: String,
        val sale_price: String,
        val similar_products: List<SimilarProduct>,
        val status: String,
        val updated_on: String,
        val variant: List<Variant>
)

data class GalleryImage(
        val productGalleryAttachmentId: String,
        val product_gallery_image: String,
        val product_id: String
)

data class SimilarProduct(
        val category_id: String,
        val category_name: String,
        val collection_type_id: String,
        val created_on: String,
        val currency_code: String,
        val currency_symbol: String,
        val in_stock: String,
        var is_wishlist: String,
        val productId: String,
        val product_image: String,
        val product_name: String,
        val product_sku: String,
        val regular_price: String,
        val sale_price: String,
        val status: String,
        val updated_on: String
)

data class Variant(
        val variantId: String,
        val variant_name: String,
        val variant_value: List<VariantValue>
)

data class VariantValue(
        val mapping_id: String,
        val variantValueId: String,
        val variant_value: String
) {
    override fun toString(): String {
        return variant_value
    }

}
/*
data class ProductDetailResponse(
    val `data`: Data,
    val message: String,
    val status: String
)

data class Data(
    val product_detail: ProductDetail
)

data class ProductDetail(
    val category_id: String,
    val category_name: String,
    val collection_type_id: String,
    val created_on: String,
    val currency_code: String,
    val currency_symbol: String,
    val gallery_images: List<GalleryImage>,
    val in_stock: String,
    val productId: String,
    val product_additional_information: String,
    val product_available_quantity: String,
    val product_description: String,
    val product_image: String,
    val product_name: String,
    val product_sku: String,
    val regular_price: String,
    val sale_price: String,
    val similar_products: List<SimilarProduct>,
    val status: String,
    val updated_on: String,
    val is_wishlist: String,
    val variant: List<Variant>
)

data class SimilarProduct(
        val category_id: String,
        val category_name: String,
        val collection_type_id: String,
        val created_on: String,
        val currency_code: String,
        val currency_symbol: String,
        val in_stock: String,
        var is_wishlist: String,
        val productId: String,
        val product_image: String,
        val product_name: String,
        val product_sku: String,
        val regular_price: String,
        val sale_price: String,
        val status: String,
        val updated_on: String
)

data class Variant(
    val variantId: String,
    val variant_name: String,
    val variant_value: List<VariantValue>
)

data class VariantValue(
    val mapping_id: String,
    val variantValueId: String,
    val variant_value: String

) {
    override fun toString(): String {
        return variant_value
    }
}

data class GalleryImage(
        var productGalleryAttachmentId: String,
        var product_gallery_image: String,
        var product_id: String
)*/
