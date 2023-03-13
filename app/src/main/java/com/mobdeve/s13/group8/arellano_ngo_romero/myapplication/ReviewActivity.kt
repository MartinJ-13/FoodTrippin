package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivityReviewBinding

class ReviewActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val viewBinding : ActivityReviewBinding = ActivityReviewBinding.inflate(layoutInflater)
    setContentView(viewBinding.root)

        viewBinding.ratingBar.rating = 2.5f
        viewBinding.ratingBar.stepSize = .5f

        viewBinding.ratingBar.setOnRatingBarChangeListener{ ratingBar, rating, fromUser ->
            Toast.makeText(this, "Rating: $rating", Toast.LENGTH_SHORT).show()
        }

    }
}