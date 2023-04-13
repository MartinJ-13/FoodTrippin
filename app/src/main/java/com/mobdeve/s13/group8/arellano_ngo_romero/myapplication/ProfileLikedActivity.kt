package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivityProfilelikedBinding
import com.squareup.picasso.Picasso

class ProfileLikedActivity : AppCompatActivity()  {

    private lateinit var restoData: ArrayList<RestaurantPreviewModel>
    private lateinit var myAdapter: RestaurantPreviewAdapter
    private lateinit var viewBinding: ActivityProfilelikedBinding
    private lateinit var recyclerView: RecyclerView

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding: ActivityProfilelikedBinding = ActivityProfilelikedBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)



        database = FirebaseFirestore.getInstance()
        //Logged in user
        val username = intent.getStringExtra("username").toString()
        val profilePic = intent.getStringExtra("profilePic").toString()
        val bio = intent.getStringExtra("bio").toString()

        if(bio.isNullOrEmpty())
            viewBinding.profileMyReviewsBioEt.visibility = View.GONE
        else
            viewBinding.profileMyReviewsBioEt.text = bio

        viewBinding.profileMyReviewsUsernameTv.text = username
        Picasso.get().load(profilePic).into(viewBinding.reviewUserIconIv)
        viewBinding.profileMyReviewsBioEt.text = bio

        retrieveLikesListener(username, viewBinding)

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

        this.restoData = arrayListOf()
        this.recyclerView = viewBinding.profileLikedRecyclerView
        this.myAdapter = RestaurantPreviewAdapter(restoData)
        this.recyclerView.adapter = myAdapter
        this.recyclerView.layoutManager = LinearLayoutManager(this)

        viewBinding.profileMyReviewsBtn.setOnClickListener(View.OnClickListener {
            finish()
            this.overridePendingTransition(0, 0);
        })
        viewBinding.editProfBtn2.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, EditProfileActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("profilePic", profilePic)
            intent.putExtra("bio", bio)
            this.startActivity(intent)
            finish()
        })

//        database = FirebaseFirestore.getInstance()
//        val likesQuery = database.collection("likes").whereEqualTo("username", username)
//        val likedRestos = ArrayList<String>()
//        likesQuery.addSnapshotListener{ querySnapshot, e ->
//            if (e != null){
//                Log.w(TAG, "Listen failed", e)
//                return@addSnapshotListener
//            }
//            val snapshot = value ?: return@addSnapshotListener
//            for (change in Snapshot!!.documentChanges){
//
//            }
//        }
    }

    override fun onPause(){
        super.onPause()
        finish()
    }

    private fun retrieveLikesListener(username : String?, binding : ActivityProfilelikedBinding) {
        database = FirebaseFirestore.getInstance()
        val likesQuery = database.collection("likes").whereEqualTo("username", username)

        likesQuery.get().addOnCompleteListener { likesTask ->
            if (likesTask.isSuccessful) {
                val restaurantNames = likesTask.result?.documents?.map {
                    it.getString("restaurantName")
                }.orEmpty()
                if(restaurantNames.isNotEmpty()) {
                    val restoQuery =
                        database.collectionGroup("restaurants").whereIn("name", restaurantNames)
                    restoQuery.get().addOnSuccessListener { querySnapshot ->
                        for (document in querySnapshot) {
                            val data = document.data
                            val name = data["name"] as String
                            val cuisineType = data["cuisineType"] as String
                            val diningType = data["diningType"] as String
                            val imageResId = data["imageResId"] as String
                            val location = data["location"] as String
                            val rating = data["rating"] as Double
                            val restaurant = RestaurantPreviewModel(
                                name,
                                rating,
                                location,
                                diningType,
                                cuisineType,
                                imageResId
                            )
                            restoData.add(restaurant)
                        }
                        Log.e(TAG, "Retrieved Restos")
                        myAdapter.notifyDataSetChanged()
                    }
                        .addOnFailureListener { exception ->
                            Log.e(TAG, "Error getting restaurants", exception)
                        }
                    }
                else {
                    Log.e(TAG, "No likes", likesTask.exception)
                    binding.noLikesLl.visibility = View.VISIBLE
                }
                }
            else {
                Log.e(TAG, "Error getting likes", likesTask.exception)
            }
        }
    }
}