package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ItemlayoutProfilemyreviewsBinding
import com.squareup.picasso.Picasso

class ReviewViewHolder(private val viewBinding: ItemlayoutProfilemyreviewsBinding ): RecyclerView.ViewHolder(viewBinding.root) {

    fun bindData(review: Review){


        this.viewBinding.reviewReviewTv.text = review.review
        this.viewBinding.reviewDatePostedTv.text = review.date
        this.viewBinding.reviewRestoNameTv.text = review.restaurant
        Picasso.get().load(review.imageId).into(viewBinding.reviewUserIconIv)
        this.viewBinding.reviewStarsRatingTv.text = review.rating.toString()
        if(!review.reviewPicID1.toString().isNullOrEmpty())
            Picasso.get().load(review.reviewPicID1).into(viewBinding.reviewReviewImage1Iv)
        else
            viewBinding.reviewReviewImage1Iv.visibility = View.GONE
    }
}