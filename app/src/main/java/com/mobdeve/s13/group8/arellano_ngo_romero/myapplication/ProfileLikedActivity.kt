package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivityProfilelikedBinding

class ProfileLikedActivity : AppCompatActivity()  {

    private lateinit var data: ArrayList<RestaurantPreviewModel>
    private lateinit var myAdapter: RestaurantPreviewAdapter
    private lateinit var viewBinding: ActivityProfilelikedBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding: ActivityProfilelikedBinding = ActivityProfilelikedBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

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

        this.data = RestaurantPreviewDataHelper.loadData()
        this.recyclerView = viewBinding.profileLikedRecyclerView
        this.myAdapter = RestaurantPreviewAdapter(data)
        this.recyclerView.adapter = myAdapter
        this.recyclerView.layoutManager = LinearLayoutManager(this)

        viewBinding.profileMyReviewsBtn.setOnClickListener(View.OnClickListener {
            finish()
        })

    }
}