package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivityProfilemyreviewsBinding
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivityRestaurantBinding

class RestaurantActivity : AppCompatActivity()  {
    private lateinit var reviewData: ArrayList<RestaurantReview>
    private lateinit var menuData: ArrayList<RestaurantMenu>

    private lateinit var reviewAdapter: RestaurantReviewAdapter
    private lateinit var menuAdapter: RestaurantMenuAdapter

    private lateinit var viewBinding: ActivityProfilemyreviewsBinding

    private lateinit var reviewRecyclerView: RecyclerView
    private lateinit var menuRecyclerView: RecyclerView

    private val helper : SnapHelper = LinearSnapHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding: ActivityRestaurantBinding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        this.reviewData = RestaurantReviewDataHelper.loadData()
        this.menuData = RestaurantMenuDataHelper.loadData()

        this.reviewAdapter = RestaurantReviewAdapter(reviewData)
        this.menuAdapter = RestaurantMenuAdapter(menuData)

        this.reviewRecyclerView = viewBinding.restaurantReviewsRecyclerView
        this.menuRecyclerView = viewBinding.restaurantMenuRecyclerView

        this.menuRecyclerView.adapter = menuAdapter
        this.reviewRecyclerView.adapter = reviewAdapter

        this.reviewRecyclerView.layoutManager = LinearLayoutManager(this)
        this.menuRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        var likeFlag: Boolean
        likeFlag = false

        viewBinding.restaurantLikeBtn.setOnClickListener(View.OnClickListener {
            likeFlag = if(!likeFlag) {
                viewBinding.restaurantLikeBtn.setImageResource(R.drawable.heart_on)
                viewBinding.restaurantLikeBtn.setColorFilter(0xfa2a55, PorterDuff.Mode.SRC_ATOP);
                true
            } else {
                viewBinding.restaurantLikeBtn.setImageResource(R.drawable.heart_off)
                false
            }
        })

        viewBinding.restaurantReviewBtn.setOnClickListener(View.OnClickListener{
            val intent1 = Intent(this, ReviewActivity::class.java)
            startActivity(intent1)

        })
    }
}