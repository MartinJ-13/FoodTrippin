package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

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

    private lateinit var data: ArrayList<RestaurantPreviewModel>
    private lateinit var myAdapter: RestaurantPreviewAdapter
    private lateinit var viewBinding: ActivityProfilelikedBinding
    private lateinit var recyclerView: RecyclerView

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding: ActivityProfilelikedBinding = ActivityProfilelikedBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.loadingPb1.visibility = View.VISIBLE

        database = FirebaseFirestore.getInstance()
        //Logged in user
        val username = intent.getStringExtra("username").toString()
        val profilePic = intent.getStringExtra("profilePic").toString()

        viewBinding.profileMyReviewsUsernameTv.text = username
        Picasso.get().load(profilePic).into(viewBinding.reviewUserIconIv)

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

        this.data = arrayListOf()
        this.recyclerView = viewBinding.profileLikedRecyclerView
        this.myAdapter = RestaurantPreviewAdapter(data)
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
            this.startActivity(intent)
            finish()
        })
    }

    private fun retrieveLikesListener(username : String?) {
        database = FirebaseFirestore.getInstance()
        database.collection("reviews").whereEqualTo("username", username).addSnapshotListener(object :
            EventListener<QuerySnapshot> {
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

                        data.add(dc.document.toObject(RestaurantPreviewModel::class.java))
                    }
                }
               myAdapter.notifyDataSetChanged()
            }
            })

    }

}