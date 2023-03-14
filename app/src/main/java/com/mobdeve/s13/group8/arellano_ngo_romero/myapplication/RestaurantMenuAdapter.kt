package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ItemlayoutRestaurantmenuBinding

class RestaurantMenuAdapter (private val data: ArrayList<RestaurantMenu>): RecyclerView.Adapter<RestaurantMenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantMenuViewHolder {
        val itemViewBinding: ItemlayoutRestaurantmenuBinding = ItemlayoutRestaurantmenuBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RestaurantMenuViewHolder(itemViewBinding)
    }

    override fun onBindViewHolder(holder: RestaurantMenuViewHolder, position: Int) {
        holder.bindData(data[position])
    }
    override fun getItemCount(): Int {
        return data.size
    }
}