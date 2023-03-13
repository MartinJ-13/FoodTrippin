package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ProfilemyreviewsLayoutBinding

class ReviewViewHolder(private val viewBinding: ProfilemyreviewsLayoutBinding ): RecyclerView.ViewHolder(viewBinding.root) {

    fun bindData(review: Review){
        this.viewBinding.myReviewsReviewTv.text = review.review
        this.viewBinding.myReviewsTitleTv.text = review.title
        this.viewBinding.myReviewsDatePostedTv.text = review.date
        this.viewBinding.likedUserIconIv.setImageResource(review.imageId)
        this.viewBinding.myReviewsReviewImage1Iv.setImageResource(review.reviewPicID1)
        this.viewBinding.myReviewsReviewImage2Iv.setImageResource(review.reviewPicID2)
    }
}