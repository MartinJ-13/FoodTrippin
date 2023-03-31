package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.firestore.*
import com.google.firebase.firestore.core.Query
import com.google.firebase.firestore.ktx.toObject
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivityProfilemyreviewsBinding
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivityRestaurantBinding
import com.squareup.picasso.Picasso
import org.w3c.dom.Document

class RestaurantActivity : AppCompatActivity()  {
    private lateinit var reviewData: ArrayList<Review>
    private lateinit var menuData: ArrayList<RestaurantMenu>

    private lateinit var reviewAdapter: RestaurantReviewAdapter
    private lateinit var menuAdapter: RestaurantMenuAdapter
    private lateinit var database : FirebaseFirestore
    private lateinit var viewBinding: ActivityRestaurantBinding

    private lateinit var reviewRecyclerView: RecyclerView
    private lateinit var menuRecyclerView: RecyclerView

    private val helper : SnapHelper = LinearSnapHelper()
    private var likeFlag : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding: ActivityRestaurantBinding = ActivityRestaurantBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        database = FirebaseFirestore.getInstance()

        val restaurantName = intent.getStringExtra("restaurantName").toString()
        val restaurantAddress = intent.getStringExtra("restaurantAddress").toString()
        val restaurantCuisineType = intent.getStringExtra("restaurantCuisineType").toString()
        val restaurantDiningType = intent.getStringExtra("restaurantDiningType").toString()
        val restaurantRating = intent.getStringExtra("restaurantRating")
        val getResto = database.collection("restaurants").whereEqualTo("name", restaurantName)

        viewBinding.restaurantNameTv.text = restaurantName
        viewBinding.restaurantAddressTv.text = restaurantAddress
        viewBinding.restaurantCuisineTagTv.text = restaurantCuisineType
        viewBinding.restaurantDIneTagTv.text = restaurantDiningType
        viewBinding.restaurantRatingBar.rating = restaurantRating!!.toFloat()

        val user = FirebaseAuth.getInstance().currentUser
        val getUser = database.collection("users").document(user!!.uid)
        var username : String? = ""
        if (user != null){
            getUser.get().addOnSuccessListener { document ->
                if(document != null) {
                    username = document.getString("username")
                    retrieveLikeStatusListener(username, restaurantName, viewBinding)
                }
            }
        }

        //SIDEBAR CODE
        // Get the DrawerLayout and NavigationView using view binding
        val drawerLayout = viewBinding.drawerLayout
        val navView = viewBinding.navView


        getResto.get().addOnSuccessListener { querySnapshot ->
            val documents = querySnapshot.documents
            if(documents.isNotEmpty()) {
                val firstDoc = documents[0]
                val restoPic = firstDoc.getString("imageResId")
                Picasso.get().load(restoPic).into(viewBinding.restaurantImgIv)
            }
        }

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

        this.reviewData = arrayListOf()
        this.menuData = RestaurantMenuDataHelper.loadData()

        this.reviewAdapter = RestaurantReviewAdapter(reviewData)
        this.menuAdapter = RestaurantMenuAdapter(menuData)

        this.reviewRecyclerView = viewBinding.restaurantReviewsRecyclerView
        this.menuRecyclerView = viewBinding.restaurantMenuRecyclerView

        this.menuRecyclerView.adapter = menuAdapter
        this.reviewRecyclerView.adapter = reviewAdapter

        this.reviewRecyclerView.layoutManager = LinearLayoutManager(this)
        this.menuRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        retrieveReviewsListener(restaurantName, viewBinding)

        viewBinding.restaurantLikeBtn.setOnClickListener(View.OnClickListener {
            if(!likeFlag) {
                viewBinding.restaurantLikeBtn.setImageResource(R.drawable.heart_on)
                viewBinding.restaurantLikeBtn.setColorFilter(0xfa2a55, PorterDuff.Mode.SRC_ATOP)
                likeFlag = true
                likeRestaurant(restaurantName, username)
            } else {
                viewBinding.restaurantLikeBtn.setImageResource(R.drawable.heart_off)
                likeFlag = false
                unlikeRestaurant(restaurantName, username)
            }
        })

        viewBinding.restaurantReviewBtn.setOnClickListener(View.OnClickListener{
            val intent1 = Intent(this, ReviewActivity::class.java)
            intent1.putExtra("restaurantName", restaurantName)
            startActivity(intent1)

        })
    }

    private fun retrieveReviewsListener(restaurantName : String, binding: ActivityRestaurantBinding) {
        database = FirebaseFirestore.getInstance()
        database.collection("reviews").whereEqualTo("restaurant", restaurantName).addSnapshotListener(object : EventListener<QuerySnapshot>{
            override fun onEvent(
                value: QuerySnapshot?,
                error: FirebaseFirestoreException?
            ) {
                if (error != null){
                    Log.e("Error in database", error.message.toString())
                    return
                }
                for (dc : DocumentChange in value?.documentChanges!!){
                    if (dc.type == DocumentChange.Type.ADDED){
                        reviewData.add(dc.document.toObject(Review::class.java))
                    }
                }
                reviewAdapter.notifyDataSetChanged()
                if(reviewData.size == 0){
                    binding.noReviewLl.visibility = View.VISIBLE
                }else{
                    binding.noReviewLl.visibility = View.GONE
                }
            }
        })
    }

    private fun retrieveLikeStatusListener(username : String?, restaurantName: String, binding: ActivityRestaurantBinding){
        database = FirebaseFirestore.getInstance()
        val getLikeStatus = database.collection("likes").whereEqualTo("restaurantName", restaurantName).whereEqualTo("username", username)
       getLikeStatus.get().addOnSuccessListener { querySnapshot ->
           val documents = querySnapshot.documents

           if(documents.isNotEmpty()) {
               binding.restaurantLikeBtn.setImageResource(R.drawable.heart_on)
               binding.restaurantLikeBtn.setColorFilter(0xfa2a55, PorterDuff.Mode.SRC_ATOP);
               likeFlag = true
           }
           else {
               binding.restaurantLikeBtn.setImageResource(R.drawable.heart_off)
               likeFlag = false
           }

       }
    }

    private fun likeRestaurant(restaurantName: String, username: String?){
        database = FirebaseFirestore.getInstance()
        val likeData = hashMapOf(
            "restaurantName" to restaurantName,
            "username" to username,
        )
        database.collection("likes")
            .document()
            .set(likeData)
            .addOnSuccessListener {
                Toast.makeText(this, "Liked post!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Like failed", Toast.LENGTH_SHORT).show()
            }
    }

    private fun unlikeRestaurant(restaurantName: String, username: String?){
        database = FirebaseFirestore.getInstance()
        val getResto = database.collection("likes").whereEqualTo("restaurantName", restaurantName).whereEqualTo("username", username)
        getResto.get().addOnSuccessListener { querySnapshot ->
        for (document in querySnapshot.documents) {
            document.reference.delete().addOnSuccessListener {
                Toast.makeText(this, "Unliked post!", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Unlike failed!", Toast.LENGTH_SHORT).show()
            }
        }
        }.addOnFailureListener { e ->
            Log.w(TAG, "Error finding the post to unlike", e)
        }
    }

}