package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivityProfilemyreviewsBinding

class ProfilemyreviewsActivity : AppCompatActivity()  {
    private lateinit var data: ArrayList<Review>
    private lateinit var myAdapter: ReviewAdapter
    private lateinit var viewBinding: ActivityProfilemyreviewsBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding: ActivityProfilemyreviewsBinding = ActivityProfilemyreviewsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        this.data = ReviewDataHelper.generateData()
        this.recyclerView = viewBinding.profileMyReviewsRecyclerView
        this.myAdapter = ReviewAdapter(data)
        this.recyclerView.adapter = myAdapter
        this.recyclerView.layoutManager = LinearLayoutManager(this)


    }
}