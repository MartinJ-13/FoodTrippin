package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

class RestaurantReviewDataHelper {
    companion object {
        fun loadData(): ArrayList<RestaurantReview> {
            val data = ArrayList<RestaurantReview>()

            data.add(RestaurantReview(
                R.drawable.sample_femaleavatar,
                "Review Title 1",
                "Denji",
                "August 19, 2023",
                "Nice! Yummy!",
                R.drawable.resto_placeholder,
                3.0,
                R.drawable.resto_placeholder
            ))

            data.add(RestaurantReview(
                R.drawable.sample_femaleavatar,
                "Review Title 2",
                "Power",
                "March 14, 2023",
                "Best girl ako",
                R.drawable.resto_placeholder,
                5.0,
                R.drawable.resto_placeholder
            ))

            data.add(RestaurantReview(
                R.drawable.sample_femaleavatar,
                "Review Title 3",
                "Aki",
                "January 1, 2023",
                "I need to find the gun devil",
                R.drawable.resto_placeholder,
                4.5,
                R.drawable.resto_placeholder
            ))

            return data
        }
    }
}