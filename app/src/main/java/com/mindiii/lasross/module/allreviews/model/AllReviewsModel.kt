package com.mindiii.lasross.module.allreviews.model

data class AllReviewsModel(
        val `data`: Data,
        val status: String
)

data class Data(
        val rating: Rating,
        val review: List<Review>
)

data class Rating(
        val all_ratings: String,
        val average_rating: String,
        val five_star: String,
        val four_star: String,
        val one_star: String,
        val review: String,
        val three_star: String,
        val two_star: String
)

data class Review(
        val AverageRating: String,
        val created_on: String,
        val description: String,
        val full_name: String,
        val productId: String,
        val updated_on: String,
        val userId: String,
        val user_profile_image: String
)

/*
{
    "status": "success",
    "data": {
        "review": [],
        "rating": {
        "average_rating": null,
        "all_ratings": "0",
        "one_star": "0",
        "two_star": "0",
        "three_star": "0",
        "four_star": "0",
        "five_star": "0",
        "review": "0"
    }
    }
}*/
