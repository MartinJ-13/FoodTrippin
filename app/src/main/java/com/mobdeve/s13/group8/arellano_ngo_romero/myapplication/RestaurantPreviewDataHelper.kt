package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

class RestaurantPreviewDataHelper{
        companion object {
            fun loadData(): ArrayList<RestaurantPreviewModel> {
                val data = ArrayList<RestaurantPreviewModel>()

                data.add(RestaurantPreviewModel("McDonald's", "New York City", "Fast Food", "American", R.drawable.resto1))
                data.add(RestaurantPreviewModel("Burger King", "Los Angeles", "Fast Food", "American", R.drawable.resto2))
                data.add(RestaurantPreviewModel("Pizza Hut", "Chicago", "Casual Dining", "Italian", R.drawable.resto3))
                data.add(RestaurantPreviewModel("Olive Garden", "Miami", "Fine Dining", "Italian", R.drawable.resto4))
                data.add(RestaurantPreviewModel("Red Lobster", "San Francisco", "Casual Dining", "Seafood", R.drawable.resto5))
                data.add(RestaurantPreviewModel("KFC", "Washington DC", "Fast Food", "American", R.drawable.resto6))
                data.add(RestaurantPreviewModel("Taco Bell", "New York City", "Fast Food", "Mexican", R.drawable.resto7))
                data.add(RestaurantPreviewModel("Chipotle", "Los Angeles", "Fast Casual", "Mexican", R.drawable.resto8))
                data.add(RestaurantPreviewModel("Subway", "Chicago", "Fast Food", "Sandwiches", R.drawable.resto9))
                data.add(RestaurantPreviewModel("Starbucks", "Chicago", "Coffee Shop", "Coffee and Snacks", R.drawable.resto10))

                return data
            }
        }
    }
