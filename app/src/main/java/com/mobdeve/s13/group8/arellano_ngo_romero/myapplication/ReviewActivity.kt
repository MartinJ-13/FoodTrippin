package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivityReviewBinding

class ReviewActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val viewBinding : ActivityReviewBinding = ActivityReviewBinding.inflate(layoutInflater)
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

                val intent3 = Intent(this, ILoveYouActivity::class.java)
                //    val intent3 = Intent(this, )
                startActivity(intent1)
                startActivity(intent2)
                startActivity(intent3)
                finish()
            }
            else
                Toast.makeText(this, "Please leave a review and upload at least 1 photo!", Toast.LENGTH_SHORT).show()
        })
    }
}