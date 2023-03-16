package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivityHomepageBinding
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.PopupRestaurantfilterBinding

class HomePageActivity : AppCompatActivity() {

    private lateinit var data: ArrayList<RestaurantPreviewModel>
    private lateinit var myAdapter: RestaurantPreviewAdapter
    private lateinit var viewBinding: ActivityHomepageBinding
    private lateinit var recyclerView: RecyclerView

    private lateinit var popupBinding: PopupRestaurantfilterBinding
    private lateinit var popupWindow: PopupWindow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        data = RestaurantPreviewDataHelper.loadData()
        recyclerView = viewBinding.recyclerView
        myAdapter = RestaurantPreviewAdapter(data)
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Show filter popup on filter button click
        viewBinding.filterBtn.setOnClickListener {
            showFilterPopup()
        }
    }

    private fun showFilterPopup() {
        popupBinding = PopupRestaurantfilterBinding.inflate(layoutInflater)

        val cuisineOptions = arrayOf("--Choose a Cuisine--","American", "Japanese", "Chinese", "Mexican", "Korean")
        val diningOptions = arrayOf("--Choose a Dining Option--", "Fine Dining", "Casual Dining", "Fast Food", "Buffet", "Food Court")
        val options = arrayOf(cuisineOptions, diningOptions)

        val spinnerAdapters = mutableListOf<SpinnerAdapter>()

        for (option in options) {
            spinnerAdapters.add(SpinnerAdapter(this, option))
        }

        popupBinding.btnApply.setOnClickListener{
            // TODO: Implement filtering logic
            popupWindow.dismiss()
        }

        for (i in spinnerAdapters.indices) {
            val adapter = spinnerAdapters[i]
            val spinner = when (i) {
                0 -> popupBinding.spCuisineType
                1 -> popupBinding.spDiningType
                else -> null
            }
            spinner?.adapter = adapter
        }

        popupWindow = PopupWindow(
            popupBinding.root,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        // Dismiss popup on close button click
        popupBinding.closePopupBtn.setOnClickListener {
            popupWindow.dismiss()
        }

        // Show popup window
        val rootView = window.decorView.rootView
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0)
    }
}
