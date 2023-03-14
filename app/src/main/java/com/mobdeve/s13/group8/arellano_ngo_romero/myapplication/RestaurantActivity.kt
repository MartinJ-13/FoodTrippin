package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
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
    }
}