package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat.startActivity
import androidx.core.graphics.drawable.DrawableCompat
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ItemlayoutRestaurantcardBinding

class RestaurantPreviewAdapter (private val data: ArrayList<RestaurantPreviewModel>) : RecyclerView.Adapter<RestaurantPreviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantPreviewViewHolder {

        val itemViewBinding: ItemlayoutRestaurantcardBinding = ItemlayoutRestaurantcardBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val myViewHolder = RestaurantPreviewViewHolder(itemViewBinding)

        myViewHolder.itemView.setOnClickListener{
            val intent = Intent(myViewHolder.itemView.context, RestaurantActivity::class.java)
            //val imageRes = itemViewBinding.profileLikedRestaurantImgIv.drawable
            intent.putExtra("restaurantName", itemViewBinding.profileLikedRestaurantNameTv.text.toString())
            intent.putExtra("restaurantAddress", itemViewBinding.profileLikedLocationTv.text.toString())
            intent.putExtra("restaurantCuisineType", itemViewBinding.profileLikedCuisinetypeTv.text.toString())
            intent.putExtra("restaurantDiningType", itemViewBinding.profileLikedDiningtypeTv.text.toString())
            intent.putExtra("restaurantRating", itemViewBinding.profileRatingTv.text.toString())
            myViewHolder.itemView.context.startActivity(intent)
        }
        return RestaurantPreviewViewHolder(itemViewBinding)
    }

    override fun onBindViewHolder(holder: RestaurantPreviewViewHolder, position: Int) {
        holder.bindData(data[position])
    }
    override fun getItemCount(): Int {
        return data.size
    }
}

