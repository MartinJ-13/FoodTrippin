package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ItemlayoutRestaurantreviewsBinding

class RestaurantReviewAdapter (private val data: ArrayList<RestaurantReview>): RecyclerView.Adapter<RestaurantReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantReviewViewHolder {
        val itemViewBinding: ItemlayoutRestaurantreviewsBinding = ItemlayoutRestaurantreviewsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RestaurantReviewViewHolder(itemViewBinding)
    }

    override fun onBindViewHolder(holder: RestaurantReviewViewHolder, position: Int) {
        holder.bindData(data[position])
    }
    override fun getItemCount(): Int {
        return data.size
    }
}