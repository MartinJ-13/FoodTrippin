package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.os.Bundle
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivityHomepageBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.PopupRestaurantfilterBinding
import android.view.Gravity
import android.view.ViewGroup
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivityToolbarBinding
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.FilterAdapter


class HomePageActivity : AppCompatActivity() {

    private lateinit var data: ArrayList<RestaurantPreviewModel>
    private lateinit var myAdapter: RestaurantPreviewAdapter
    private lateinit var viewBinding: ActivityHomepageBinding
    private lateinit var recyclerView: RecyclerView

    private lateinit var toolbarBinding: ActivityToolbarBinding

    private lateinit var popupBinding : PopupRestaurantfilterBinding
    private lateinit var popupWindow : PopupWindow
    private lateinit var filterAdapter : FilterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding: ActivityHomepageBinding =
            ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        this.data = RestaurantPreviewDataHelper.loadData()
        this.recyclerView = viewBinding.recyclerView
        this.myAdapter = RestaurantPreviewAdapter(data)
        this.recyclerView.adapter = myAdapter
        this.recyclerView.layoutManager = LinearLayoutManager(this)

        toolbarBinding.filterBtn.setOnClickListener{
            popupBinding = PopupRestaurantfilterBinding.inflate(layoutInflater)

            filterAdapter = FilterAdapter(this)

        }



    }
}


