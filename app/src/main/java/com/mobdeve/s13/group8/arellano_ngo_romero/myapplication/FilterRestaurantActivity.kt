package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivityRestaurantfilterBinding

class FilterRestaurantActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding: ActivityRestaurantfilterBinding = ActivityRestaurantfilterBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}






