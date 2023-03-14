package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ItemlayoutRestaurantmenuBinding

class RestaurantMenuViewHolder (private val viewBinding: ItemlayoutRestaurantmenuBinding) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bindData(restaurantMenu: RestaurantMenu) {
        this.viewBinding.restaurantMenuIv.setImageResource(restaurantMenu.restaurantMenu)
    }
}


