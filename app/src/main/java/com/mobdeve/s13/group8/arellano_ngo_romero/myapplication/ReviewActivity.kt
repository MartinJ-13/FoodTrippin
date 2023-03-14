package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivityReviewBinding

class ReviewActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val viewBinding : ActivityReviewBinding = ActivityReviewBinding.inflate(layoutInflater)
    setContentView(viewBinding.root)
        var imageCount = 0

        viewBinding.ratingBar.rating = 2.5f
        viewBinding.ratingBar.stepSize = .5f

        viewBinding.ratingBar.setOnRatingBarChangeListener{ ratingBar, rating, fromUser ->
            Toast.makeText(this, "Rating: $rating", Toast.LENGTH_SHORT).show()
        }

        viewBinding.addImgBtn.setOnClickListener(View.OnClickListener {

            when (imageCount) {
                0 -> {
                    viewBinding.reviewImg1Iv.visibility = View.VISIBLE
                    ++imageCount
                }
                1 -> {
                    viewBinding.reviewImg2Iv.visibility = View.VISIBLE
                    ++imageCount
                }
                else -> Toast.makeText(this, "Maximum Photos Uploaded", Toast.LENGTH_SHORT).show()
            }
        })

        viewBinding.submitBtn.setOnClickListener(View.OnClickListener {
            if(imageCount > 0 && viewBinding.reviewTextTv.text.toString().isNotEmpty()) {
                val intent1 = Intent(this, MainActivity::class.java)
                intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK

                val intent2 = Intent(this, HomePageActivity::class.java)
                intent2.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK

                //    val intent3 = Intent(this, )
                startActivity(intent1)
                finish()
            }
            else
                Toast.makeText(this, "Please leave a review and upload at least 1 photo!", Toast.LENGTH_SHORT).show()
        })
    }
}