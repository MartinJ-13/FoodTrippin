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
                    "Jollibee",
                    "March 13, 2023",
                    3.0,
                    "",
                    "Some of the items are a bit boring, but most of them are varied enough. Overall a decent restaurant.",
                    "",
                )
            )

            data.add(
                Review(
                    "Morbius",
                    "McDonalds",
                    "March 13, 2023",
                    3.5,
                    "R.drawable.img",
                    "Cheese is way too much. Shame since the patty is good.",
                    "",

                )
            )

            return data
        }
    }
}