package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ItemlayoutRestaurantreviewsBinding

class RestaurantReviewViewHolder (private val viewBinding: ItemlayoutRestaurantreviewsBinding) : RecyclerView.ViewHolder(viewBinding.root) {


    fun bindData(restaurantReview: RestaurantReview) {

        this.viewBinding.reviewUserIconIv.setImageResource(restaurantReview.reviewerIcon)
        this.viewBinding.reviewByTv.text = "By: " + restaurantReview.reviewerName
        this.viewBinding.reviewDatePostedTv.text = restaurantReview.reviewDate
        this.viewBinding.reviewReviewTv.text = restaurantReview.reviewReview
        this.viewBinding.reviewStarsTv.text = restaurantReview.reviewStars.toString()
        this.viewBinding.reviewReviewImage1Iv.setImageResource(restaurantReview.reviewPhoto1)
        this.viewBinding.reviewReviewImage2Iv.setImageResource(restaurantReview.reviewPhoto2)

    }
}