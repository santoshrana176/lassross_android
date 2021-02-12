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
        val description: String,
        val gallery_images: List<GalleryImage>,
        val in_stock: String,
        val is_wishlist: String,
        val productId: String,
        val product_additional_information: String,
        val product_available_quantity: String,
        val product_description: String,
        val product_dimension: String,
        val product_image: String,
        val product_image_large: String,
        val product_image_medium: String,
        val product_image_original: String,
        val product_name: String,
        val product_sku: String,
        val product_weight: String,
        val rating: String,
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
        val product_gallery_image_large: String,
        val product_gallery_image_medium: String,
        val product_gallery_image_thumb: String,
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
)
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
data class ProductDetailsResponse(
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
    val description: String,
    val gallery_images: List<GalleryImage>,
    val in_stock: String,
    val is_wishlist: String,
    val productId: String,
    val product_additional_information: String,
    val product_available_quantity: String,
    val product_description: String,
    val product_dimension: String,
    val product_image: String,
    val product_image_large: String,
    val product_image_medium: String,
    val product_image_original: String,
    val product_name: String,
    val product_sku: String,
    val product_weight: String,
    val rating: String,
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
    val product_gallery_image_large: String,
    val product_gallery_image_medium: String,
    val product_gallery_image_thumb: String,
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
    val is_wishlist: String,
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
)
data class VariantValue(
        val mapping_id: String,
        val variantValueId: String,
        val variant_value: String
) {
    override fun toString(): String {
        return variant_value
    }

}*/
/*{
    "status": "success",
    "message": "Found",
    "data": {
        "product_detail": {
            "productId": "49",
            "product_name": "Women Embroidered Casual Spread Shirt ",
            "product_description": "No Cost EMI on Bajaj Finserv EMI Card on cart value above ₹4499T&C\r\n\r\nNo Cost EMI on Flipkart Axis Bank Credit CardT&C\r\n\r\nSpecial PriceGet extra 63% off (price inclusive of discount)T&C\r\n\r\nBank Offer10% Instant Discount on Mastercard on Fashion for First 3 Prepaid PaymentsT&C\r\n\r\nBank Offer5% Unlimited Cashback on Flipkart Axis Bank Credit CardT&C\r\n\r\nBank Offer5% Cashback* on HDFC Bank Debit CardsT&C\r\n\r\nBank OfferExtra 5% off* with Axis Bank Buzz Credit CardT&C",
            "product_additional_information": "No Cost EMI on Bajaj Finserv EMI Card on cart value above ₹4499T&C\r\n\r\nNo Cost EMI on Flipkart Axis Bank Credit CardT&C\r\n\r\nSpecial PriceGet extra 63% off (price inclusive of discount)T&C\r\n\r\nBank Offer10% Instant Discount on Mastercard on Fashion for First 3 Prepaid PaymentsT&C\r\n\r\nBank Offer5% Unlimited Cashback on Flipkart Axis Bank Credit CardT&C\r\n\r\nBank Offer5% Cashback* on HDFC Bank Debit CardsT&C\r\n\r\nBank OfferExtra 5% off* with Axis Bank Buzz Credit CardT&C\r\n\r\nIt's simple yet elegant and sober the color is just wow. If you are planning to buy then just go for it without a second thought.\r\n\r\nIt's simple yet elegant and sober the color is just wow. If you are planning to buy then just go for it without a second thought\r\n\r\nIt's simple yet elegant and sober the color is just wow. If you are planning to buy then just go for it without a second thought\r\n\r\nIt's simple yet elegant and sober the color is just wow. If you are planning to buy then just go for it without a second thought\r\n\r\nIt's simple yet elegant and sober the color is just wow. If you are planning to buy then just go for it without a second thought\r\n\r\nIt's simple yet elegant and sober the color is just wow. If you are planning to buy then just go for it without a second thought\r\n\r\nIt's simple yet elegant and sober the color is just wow. If you are planning to buy then just go for it without a second thought\r\n\r\nIt's simple yet elegant and sober the color is just wow. If you are planning to buy then just go for it without a second thought",
            "product_sku": "LS-42",
            "product_available_quantity": "10000000",
            "currency_code": "Euro",
            "currency_symbol": "€",
            "regular_price": "685.00",
            "sale_price": "0.00",
            "product_weight": "",
            "product_dimension": "",
            "collection_type_id": "2",
            "in_stock": "0",
            "status": "1",
            "created_on": "2019-09-24 10:15:32",
            "updated_on": "2019-11-25 10:19:38",
            "description": "",
            "category_id": "1, 4",
            "category_name": "Men, Shirt",
            "rating": "3.0",
            "AverageRating": "3.66667",
            "is_wishlist": "1",
            "product_image": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/Pm1orJnVWkXyD0AH.jpg",
            "product_image_original": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/Pm1orJnVWkXyD0AH.jpg",
            "product_image_medium": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/medium/Pm1orJnVWkXyD0AH.jpg",
            "product_image_large": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/large/Pm1orJnVWkXyD0AH.jpg",
            "gallery_images": [
                {
                    "productGalleryAttachmentId": "5",
                    "product_id": "49",
                    "product_gallery_image": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product_gallery/MKJtQSCBibPayjul.jpg",
                    "product_gallery_image_thumb": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product_gallery/thumb/MKJtQSCBibPayjul.jpg",
                    "product_gallery_image_medium": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product_gallery/medium/MKJtQSCBibPayjul.jpg",
                    "product_gallery_image_large": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product_gallery/large/MKJtQSCBibPayjul.jpg"
                },
                {
                    "productGalleryAttachmentId": "6",
                    "product_id": "49",
                    "product_gallery_image": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product_gallery/KQ5IumfxWD8AdeX1.jpg",
                    "product_gallery_image_thumb": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product_gallery/thumb/KQ5IumfxWD8AdeX1.jpg",
                    "product_gallery_image_medium": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product_gallery/medium/KQ5IumfxWD8AdeX1.jpg",
                    "product_gallery_image_large": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product_gallery/large/KQ5IumfxWD8AdeX1.jpg"
                },
                {
                    "productGalleryAttachmentId": "7",
                    "product_id": "49",
                    "product_gallery_image": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product_gallery/4hoFuX3JWYfzi9SV.jpg",
                    "product_gallery_image_thumb": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product_gallery/thumb/4hoFuX3JWYfzi9SV.jpg",
                    "product_gallery_image_medium": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product_gallery/medium/4hoFuX3JWYfzi9SV.jpg",
                    "product_gallery_image_large": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product_gallery/large/4hoFuX3JWYfzi9SV.jpg"
                },
                {
                    "productGalleryAttachmentId": "8",
                    "product_id": "49",
                    "product_gallery_image": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product_gallery/vWmrEnTOyxj6HkAF.jpg",
                    "product_gallery_image_thumb": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product_gallery/thumb/vWmrEnTOyxj6HkAF.jpg",
                    "product_gallery_image_medium": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product_gallery/medium/vWmrEnTOyxj6HkAF.jpg",
                    "product_gallery_image_large": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product_gallery/large/vWmrEnTOyxj6HkAF.jpg"
                },
                {
                    "productGalleryAttachmentId": "10",
                    "product_id": "49",
                    "product_gallery_image": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product_gallery/uctx7oCn6QBiEZN5.png",
                    "product_gallery_image_thumb": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product_gallery/thumb/uctx7oCn6QBiEZN5.png",
                    "product_gallery_image_medium": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product_gallery/medium/uctx7oCn6QBiEZN5.png",
                    "product_gallery_image_large": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product_gallery/large/uctx7oCn6QBiEZN5.png"
                },
                {
                    "productGalleryAttachmentId": "11",
                    "product_id": "49",
                    "product_gallery_image": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product_gallery/p0WziRKDBk5L4ZsA.png",
                    "product_gallery_image_thumb": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product_gallery/thumb/p0WziRKDBk5L4ZsA.png",
                    "product_gallery_image_medium": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product_gallery/medium/p0WziRKDBk5L4ZsA.png",
                    "product_gallery_image_large": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product_gallery/large/p0WziRKDBk5L4ZsA.png"
                }
            ],
            "variant": [
                {
                    "variantId": "1",
                    "variant_name": "size",
                    "variant_value": [
                        {
                            "mapping_id": "1",
                            "variantValueId": "1",
                            "variant_value": "Small"
                        },
                        {
                            "mapping_id": "2",
                            "variantValueId": "2",
                            "variant_value": "Medium"
                        },
                        {
                            "mapping_id": "3",
                            "variantValueId": "3",
                            "variant_value": "Large"
                        },
                        {
                            "mapping_id": "4",
                            "variantValueId": "4",
                            "variant_value": "Extra large"
                        },
                        {
                            "mapping_id": "19",
                            "variantValueId": "19",
                            "variant_value": "XXL"
                        },
                        {
                            "mapping_id": "22",
                            "variantValueId": "22",
                            "variant_value": "Extra small"
                        }
                    ]
                },
                {
                    "variantId": "2",
                    "variant_name": "color",
                    "variant_value": [
                        {
                            "mapping_id": "5",
                            "variantValueId": "5",
                            "variant_value": "black"
                        },
                        {
                            "mapping_id": "6",
                            "variantValueId": "6",
                            "variant_value": "white"
                        },
                        {
                            "mapping_id": "7",
                            "variantValueId": "7",
                            "variant_value": "Yellow"
                        },
                        {
                            "mapping_id": "8",
                            "variantValueId": "8",
                            "variant_value": "Light pink"
                        },
                        {
                            "mapping_id": "9",
                            "variantValueId": "9",
                            "variant_value": "Red"
                        },
                        {
                            "mapping_id": "10",
                            "variantValueId": "10",
                            "variant_value": "Blue"
                        }
                    ]
                }
            ],
            "similar_products": [
                {
                    "productId": "48",
                    "product_name": "casual sleeve Solid Women Pink Shirt",
                    "product_sku": "LS-41",
                    "regular_price": "321.00",
                    "currency_code": "Euro",
                    "currency_symbol": "€",
                    "sale_price": "128.40",
                    "collection_type_id": "1",
                    "in_stock": "1",
                    "status": "1",
                    "updated_on": "2019-11-25 10:33:26",
                    "created_on": "2019-09-24 07:37:03",
                    "category_id": "1, 5",
                    "category_name": "Men, T-shirt",
                    "is_wishlist": "1",
                    "product_image": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/i0qfHUgPBMjmoJdn.jpg"
                },
                {
                    "productId": "47",
                    "product_name": "Women regular fitted party wears ",
                    "product_sku": "LS-40",
                    "regular_price": "699.00",
                    "currency_code": "Euro",
                    "currency_symbol": "€",
                    "sale_price": "559.20",
                    "collection_type_id": "2",
                    "in_stock": "0",
                    "status": "1",
                    "updated_on": "2019-11-25 09:31:34",
                    "created_on": "2019-09-24 07:31:48",
                    "category_id": "1, 5",
                    "category_name": "Men, T-shirt",
                    "is_wishlist": "0",
                    "product_image": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/EkUVW4ve8NTPS7w3.jpg"
                },
                {
                    "productId": "32",
                    "product_name": "Men Solid Casual Mandarin Shirt",
                    "product_sku": "LS-28",
                    "regular_price": "343.00",
                    "currency_code": "Euro",
                    "currency_symbol": "€",
                    "sale_price": "171.50",
                    "collection_type_id": "1",
                    "in_stock": "1",
                    "status": "1",
                    "updated_on": "2019-11-18 05:43:15",
                    "created_on": "2019-09-23 13:40:26",
                    "category_id": "1, 4",
                    "category_name": "Men, Shirt",
                    "is_wishlist": "0",
                    "product_image": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/BlWkzcg7xKfnve4a.jpg"
                },
                {
                    "productId": "31",
                    "product_name": "IndoPrimo Men's Cotton Casual Fancy Shirt for Men Full Sleeves",
                    "product_sku": "LS-27",
                    "regular_price": "800.00",
                    "currency_code": "Euro",
                    "currency_symbol": "€",
                    "sale_price": "20.00",
                    "collection_type_id": "1",
                    "in_stock": "1",
                    "status": "1",
                    "updated_on": "2019-10-09 13:57:28",
                    "created_on": "2019-09-20 14:32:34",
                    "category_id": "1, 4",
                    "category_name": "Men, Shirt",
                    "is_wishlist": "0",
                    "product_image": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/LyQBARreY1nTw35U.jpg"
                },
                {
                    "productId": "10",
                    "product_name": "Symbol Men's Regular fit Formal Shirt",
                    "product_sku": "LS-10",
                    "regular_price": "498.00",
                    "currency_code": "Euro",
                    "currency_symbol": "€",
                    "sale_price": "200.00",
                    "collection_type_id": "2",
                    "in_stock": "1",
                    "status": "1",
                    "updated_on": "2019-09-30 13:13:43",
                    "created_on": "2019-09-20 09:33:32",
                    "category_id": "1, 4",
                    "category_name": "Men, Shirt",
                    "is_wishlist": "0",
                    "product_image": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/E7j3b5h2MmJLABfe.jpg"
                },
                {
                    "productId": "9",
                    "product_name": "Symbol Men's Formal Dobby Regular Fit Shirt",
                    "product_sku": "LS-9",
                    "regular_price": "50.00",
                    "currency_code": "Euro",
                    "currency_symbol": "€",
                    "sale_price": "30.00",
                    "collection_type_id": "2",
                    "in_stock": "1",
                    "status": "1",
                    "updated_on": "2019-09-30 13:13:10",
                    "created_on": "2019-09-20 07:29:37",
                    "category_id": "1, 4",
                    "category_name": "Men, Shirt",
                    "is_wishlist": "0",
                    "product_image": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/QE1eGMTaoJlprXWi.jpg"
                },
                {
                    "productId": "6",
                    "product_name": "ETHICS Perfect Ultra Lite Sport Shoes for Men",
                    "product_sku": "LS-6",
                    "regular_price": "434.00",
                    "currency_code": "Euro",
                    "currency_symbol": "€",
                    "sale_price": "343.00",
                    "collection_type_id": "1",
                    "in_stock": "1",
                    "status": "1",
                    "updated_on": "2019-10-03 12:15:38",
                    "created_on": "2019-09-18 13:43:00",
                    "category_id": "1, 22",
                    "category_name": "Men, Shoes",
                    "is_wishlist": "0",
                    "product_image": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/t8kB6hEm1NzcFMA3.jpg"
                },
                {
                    "productId": "5",
                    "product_name": "Diverse Men's Printed Regular Fit Cotton Formal Shirt",
                    "product_sku": "LS-5",
                    "regular_price": "645.00",
                    "currency_code": "Euro",
                    "currency_symbol": "€",
                    "sale_price": "544.00",
                    "collection_type_id": "1",
                    "in_stock": "1",
                    "status": "1",
                    "updated_on": "2019-10-03 11:03:55",
                    "created_on": "2019-09-18 13:38:48",
                    "category_id": "1, 4",
                    "category_name": "Men, Shirt",
                    "is_wishlist": "0",
                    "product_image": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/zCxbv3Jw7VDdog5r.jpg"
                },
                {
                    "productId": "3",
                    "product_name": "Men Checkered Casual Spread Shirt",
                    "product_sku": "LS-3",
                    "regular_price": "500.00",
                    "currency_code": "Euro",
                    "currency_symbol": "€",
                    "sale_price": "250.00",
                    "collection_type_id": "1",
                    "in_stock": "1",
                    "status": "1",
                    "updated_on": "2019-09-30 12:55:16",
                    "created_on": "2019-09-18 08:50:16",
                    "category_id": "1, 4",
                    "category_name": "Men, Shirt",
                    "is_wishlist": "0",
                    "product_image": "https://lasross-development.s3.us-east-2.amazonaws.com/uploads/product/thumb/VMdLukQgBtzpTZF4.jpeg"
                }
            ]
        }
    }
}*/
/*=====================================================================================================*/
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
