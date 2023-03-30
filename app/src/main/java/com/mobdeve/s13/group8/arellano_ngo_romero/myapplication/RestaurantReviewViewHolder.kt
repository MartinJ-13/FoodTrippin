package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ItemlayoutRestaurantreviewsBinding
import com.squareup.picasso.Picasso

class RestaurantReviewViewHolder (private val viewBinding: ItemlayoutRestaurantreviewsBinding) : RecyclerView.ViewHolder(viewBinding.root) {


    fun bindData(restaurantReview: Review) {
        Picasso.get().load(restaurantReview.userAvatar).into(viewBinding.reviewUserIconIv)
        this.viewBinding.reviewByTv.text = "By: " + restaurantReview.username
        this.viewBinding.reviewDatePostedTv.text = restaurantReview.date
        this.viewBinding.reviewReviewTv.text = restaurantReview.review
        this.viewBinding.reviewStarsTv.text = restaurantReview.rating.toString()
        if(!restaurantReview.reviewPicID1.toString().isNullOrEmpty())
            Picasso.get().load(restaurantReview.reviewPicID1).into(viewBinding.reviewReviewImage1Iv)
        else {
            this.viewBinding.reviewReviewImage1Iv.visibility = View.GONE
        }
    }
}