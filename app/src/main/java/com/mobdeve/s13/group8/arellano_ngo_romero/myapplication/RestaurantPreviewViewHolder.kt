package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ItemlayoutRestaurantcardBinding
import com.squareup.picasso.Picasso


class RestaurantPreviewViewHolder (private val viewBinding: ItemlayoutRestaurantcardBinding) : RecyclerView.ViewHolder(viewBinding.root) {


    fun bindData(restaurant: RestaurantPreviewModel) {
        this.viewBinding.profileLikedRestaurantNameTv.text = restaurant.name
        this.viewBinding.profileRatingTv.text = restaurant.rating.toString()
        this.viewBinding.profileLikedLocationTv.text = restaurant.location
        this.viewBinding.profileLikedCuisinetypeTv.text = restaurant.cuisineType
        this.viewBinding.profileLikedDiningtypeTv.text = restaurant.diningType
        Picasso.get().load(restaurant.imageResId).into(viewBinding.profileLikedRestaurantImgIv)
    }
}
