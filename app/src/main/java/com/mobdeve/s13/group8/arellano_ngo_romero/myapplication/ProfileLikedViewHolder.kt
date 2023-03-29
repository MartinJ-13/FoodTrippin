package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ItemlayoutRestaurantcardBinding

class ProfileLikedViewHolder (private val viewBinding: ItemlayoutRestaurantcardBinding): RecyclerView.ViewHolder(viewBinding.root) {

    fun bindData(profileLiked: ProfileLiked) {
        this.viewBinding.profileLikedRestaurantImgIv.setImageResource(profileLiked.restaurantImage)
        this.viewBinding.profileLikedRestaurantNameTv.text = profileLiked.restaurantName
        this.viewBinding.profileLikedLocationTv.text = profileLiked.restaurantLocation
        this.viewBinding.profileLikedCuisinetypeTv.text = profileLiked.restaurantCuisineType
        this.viewBinding.profileLikedDiningtypeTv.text = profileLiked.restaurantDiningType
    }
}