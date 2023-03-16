package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
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

        //SIDEBAR CODE
        // Get the DrawerLayout and NavigationView using view binding
       val drawerLayout = viewBinding.drawerLayout
       val navView = viewBinding.navView

        // Set a click listener for the hamburger icon to open the sidebar
        viewBinding.sidebarNav.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Set a navigation item selected listener to handle navigation menu item clicks
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    val intent = Intent(this, HomePageActivity::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawer(GravityCompat.START) // close the drawer layout
                    true
                }
                R.id.menu_profile -> {
                    val intent = Intent(this, ProfilemyreviewsActivity::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawer(GravityCompat.START) // close the drawer layout
                    true
                }
                R.id.menu_logout -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    drawerLayout.closeDrawer(GravityCompat.START) // close the drawer layout
                    true
                }
                else -> false
            }
        }

        //SIDEBAR CODE

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
