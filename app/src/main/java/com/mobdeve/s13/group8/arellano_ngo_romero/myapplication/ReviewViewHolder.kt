package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ProfilemyreviewsLayoutBinding

class ReviewViewHolder(private val viewBinding: ProfilemyreviewsLayoutBinding ): RecyclerView.ViewHolder(viewBinding.root) {

    fun bindData(review: Review){
        this.viewBinding.reviewReviewTv.text = review.review
        this.viewBinding.reviewTitleTv.text = review.title
        this.viewBinding.reviewDatePostedTv.text = review.date
        this.viewBinding.reviewUserIconIv.setImageResource(review.imageId)
        this.viewBinding.reviewReviewImage1Iv.setImageResource(review.reviewPicID1)
        this.viewBinding.reviewReviewImage2Iv.setImageResource(review.reviewPicID2)
    }
}