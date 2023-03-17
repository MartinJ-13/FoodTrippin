package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

class RestaurantPreviewDataHelper{
        companion object {
            fun loadData(): ArrayList<RestaurantPreviewModel> {
                val data = ArrayList<RestaurantPreviewModel>()

                data.add(RestaurantPreviewModel("McDonald's", 5.0,"New York City", "Fast Food", "American", R.drawable.resto1))
                data.add(RestaurantPreviewModel("Burger King", 4.5, "Los Angeles", "Fast Food", "American", R.drawable.resto2))
                data.add(RestaurantPreviewModel("Pizza Hut", 3.0, "Chicago", "Casual Dining", "Italian", R.drawable.resto3))
                data.add(RestaurantPreviewModel("Olive Garden", 3.5, "Miami", "Fine Dining", "Italian", R.drawable.resto4))
                data.add(RestaurantPreviewModel("Red Lobster", 2.0, "San Francisco", "Casual Dining", "Seafood", R.drawable.resto5))
                data.add(RestaurantPreviewModel("KFC", 4.0, "Washington DC", "Fast Food", "American", R.drawable.resto6))
                data.add(RestaurantPreviewModel("Taco Bell", 5.0, "New York City", "Fast Food", "Mexican", R.drawable.resto7))
                data.add(RestaurantPreviewModel("Chipotle", 4.0, "Los Angeles", "Fast Casual", "Mexican", R.drawable.resto8))
                data.add(RestaurantPreviewModel("Subway", 3.5, "Chicago", "Fast Food", "Sandwiches", R.drawable.resto9))
                data.add(RestaurantPreviewModel("Starbucks", 2.5,"Chicago", "Coffee Shop", "Coffee and Snacks", R.drawable.resto10))

                return data
            }
        }
    }
