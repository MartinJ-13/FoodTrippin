package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

class RestaurantPreviewDataHelper{
        companion object {
            fun loadData(): ArrayList<RestaurantPreviewModel> {
                val data = ArrayList<RestaurantPreviewModel>()

                data.add(RestaurantPreviewModel("McDonald's", 5.0,"New York City", "Fast Food", "American", ""))
                data.add(RestaurantPreviewModel("Burger King", 4.5, "Los Angeles", "Fast Food", "American", ""))
                data.add(RestaurantPreviewModel("Pizza Hut", 3.0, "Chicago", "Casual Dining", "Italian", ""))
                data.add(RestaurantPreviewModel("Olive Garden", 3.5, "Miami", "Fine Dining", "Italian", ""))
                data.add(RestaurantPreviewModel("Red Lobster", 2.0, "San Francisco", "Casual Dining", "Seafood", ""))
                data.add(RestaurantPreviewModel("KFC", 4.0, "Washington DC", "Fast Food", "American", ""))
                data.add(RestaurantPreviewModel("Taco Bell", 5.0, "New York City", "Fast Food", "Mexican", ""))
                data.add(RestaurantPreviewModel("Chipotle", 4.0, "Los Angeles", "Fast Casual", "Mexican", ""))
                data.add(RestaurantPreviewModel("Subway", 3.5, "Chicago", "Fast Food", "Sandwiches", ""))
                data.add(RestaurantPreviewModel("Starbucks", 2.5,"Chicago", "Coffee Shop", "Coffee and Snacks", ""))

                return data
            }
        }
    }
