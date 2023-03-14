package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

class RestaurantMenuDataHelper {

    companion object {
        fun loadData(): ArrayList<RestaurantMenu> {
            val data = ArrayList<RestaurantMenu>()

            data.add(RestaurantMenu(R.drawable.resto1_menu))
            data.add(RestaurantMenu(R.drawable.resto2_menu))
            data.add(RestaurantMenu(R.drawable.resto3_menu))
            data.add(RestaurantMenu(R.drawable.resto4_menu))
            return data
        }
    }
}