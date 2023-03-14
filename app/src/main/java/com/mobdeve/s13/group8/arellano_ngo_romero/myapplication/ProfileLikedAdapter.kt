package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ItemlayoutRestaurantcardBinding

class ProfileLikedAdapter (private val data: ArrayList<ProfileLiked>): RecyclerView.Adapter<ProfileLikedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileLikedViewHolder {
        val itemViewBinding: ItemlayoutRestaurantcardBinding = ItemlayoutRestaurantcardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProfileLikedViewHolder(itemViewBinding)
    }

    override fun onBindViewHolder(holder: ProfileLikedViewHolder, position: Int) {
        holder.bindData(data[position])
    }
    override fun getItemCount(): Int {
        return data.size
    }
}