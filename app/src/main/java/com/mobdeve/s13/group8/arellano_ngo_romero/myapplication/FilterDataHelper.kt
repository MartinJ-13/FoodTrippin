package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

class FilterDataHelper {
    companion object {
        fun loadFilters(): ArrayList<FilterModel> {
            val data = ArrayList<FilterModel>()

            // Spinner 1 options
            val locationOptions = arrayOf("Quezon City", "San Juan", "Pasig City", "Makati", "Taguig City")
            data.add(FilterModel(locationOptions))

            // Spinner 2 options
            val cuisineOptions = arrayOf("American", "Japanese", "Chinese", "Mexican", "Korean")
            data.add(FilterModel(cuisineOptions))

            // Spinner 3 options
            val diningOptions = arrayOf("Fine Dining", "Casual Dining", "Fast Food", "Buffet", "Food Court")
            data.add(FilterModel(diningOptions))

            return data
        }
    }
}
