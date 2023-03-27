package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuAdapter
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivityProfilemyreviewsBinding
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivityRestaurantBinding

class RestaurantActivity : AppCompatActivity()  {
    private lateinit var reviewData: ArrayList<RestaurantReview>
    private lateinit var menuData: ArrayList<RestaurantMenu>

    private lateinit var reviewAdapter: RestaurantReviewAdapter
    private lateinit var menuAdapter: RestaurantMenuAdapter

    private lateinit var viewBinding: ActivityRestaurantBinding

    private lateinit var reviewRecyclerView: RecyclerView
    private lateinit var menuRecyclerView: RecyclerView

    private val helper : SnapHelper = LinearSnapHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding: ActivityRestaurantBinding = ActivityRestaurantBinding.inflate(layoutInflater)
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
                    val intent1 = Intent(this, MainActivity::class.java)
                    intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent1)

                    val intent2 = Intent(this, HomePageActivity::class.java)
                    intent2.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent2)

                    finish()

                    drawerLayout.closeDrawer(GravityCompat.START) // close the drawer layout
                    true
                }
                R.id.menu_profile -> {
                    val intent1 = Intent(this, MainActivity::class.java)
                    intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent1)

                    val intent2 = Intent(this, HomePageActivity::class.java)
                    intent2.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent2)

                    val intent3 = Intent(this, ProfilemyreviewsActivity::class.java)
                    startActivity(intent3)

                    finish()
                    drawerLayout.closeDrawer(GravityCompat.START) // close the drawer layout
                    true
                }
                R.id.menu_logout -> {
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()

                    drawerLayout.closeDrawer(GravityCompat.START) // close the drawer layout
                    true
                }
                else -> false
            }
        }

        //SIDEBAR CODE

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