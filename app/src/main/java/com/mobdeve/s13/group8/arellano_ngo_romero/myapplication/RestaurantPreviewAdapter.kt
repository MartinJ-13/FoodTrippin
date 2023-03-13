package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ItemlayoutRestaurantcardBinding

class RestaurantPreviewAdapter (private val data: ArrayList<RestaurantPreviewModel>) : RecyclerView.Adapter<RestaurantPreviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantPreviewViewHolder {
        val itemViewBinding: ItemlayoutRestaurantcardBinding = ItemlayoutRestaurantcardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RestaurantPreviewViewHolder(itemViewBinding)
    }

    override fun onBindViewHolder(holder: RestaurantPreviewViewHolder, position: Int) {
        holder.bindData(data[position])
    }
    override fun getItemCount(): Int {
        return data.size
    }
}


