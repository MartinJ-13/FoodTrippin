package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

class ReviewDataHelper {
    companion object {
        fun generateData(): ArrayList<Review> {
            val data = ArrayList<Review>()
//val username: String, val title: String, val date:
// String, val rating: Int, val imageId: Int, val review: String, val reviewPicID1 : Int, val reviewPicID2: Int
            data.add(
                Review(
                    "Morbius",
                    "Kinda Mid",
                    "March 13, 2023",
                    5,
                    R.drawable.img1,
                    "Some of the restaurants are a bit boring, but most of them are varied enough. Overall a decent itinerary.",
                    R.drawable.borger,
                    R.drawable.borger
                )
            )

            data.add(
                Review(
                    "Morbius",
                    "7/10 Too much cheese",
                    "March 13, 2023",
                    5,
                    R.drawable.img1,
                    "Some of the restaurants are a bit boring, but most of them are varied enough. Overall a decent itinerary.",
                    R.drawable.borger,
                    R.drawable.borger
                )
            )

            return data
        }
    }
}