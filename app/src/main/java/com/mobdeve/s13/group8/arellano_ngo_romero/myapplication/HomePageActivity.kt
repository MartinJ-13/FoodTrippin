package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.text.TextWatcher
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivityHomepageBinding
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.PopupRestaurantfilterBinding


class HomePageActivity : AppCompatActivity() {

    private lateinit var restoData: ArrayList<RestaurantPreviewModel>
    private lateinit var restoAdapter: RestaurantPreviewAdapter
    private lateinit var viewBinding: ActivityHomepageBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var popupBinding: PopupRestaurantfilterBinding
    private lateinit var popupWindow: PopupWindow
    private lateinit var database : FirebaseFirestore
    private lateinit var filteredRestaurants: ArrayList<RestaurantPreviewModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        RetrieveReviewsListener()

        restoData = ArrayList()
        filteredRestaurants = ArrayList()
        recyclerView = viewBinding.recyclerView
        restoAdapter = RestaurantPreviewAdapter(restoData)
        recyclerView.adapter = restoAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //logged in user
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null){
            val uid = user.uid
        }

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
        navView.itemIconTintList = null
        //SIDEBAR CODE

        // Show filter popup on filter button click
        viewBinding.filterBtn.setOnClickListener {
            showFilterPopup()
        }
//
//        viewBinding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
//                override fun onQueryTextSubmit(query: String?): Boolean {
//                    tempRestoData.clear()
//                    tempRestoData.addAll(originalRestoData)
//                    restoAdapter.setData(tempRestoData)
//                    if (!query.isNullOrEmpty()) {
//                        val lowerCaseQuery = query.lowercase()
//                        val filteredRestaurants = ArrayList<RestaurantPreviewModel>()
//                        for (restaurant in tempRestoData) {
//                            if (restaurant.name?.lowercase()?.contains(lowerCaseQuery) == true ||
//                                restaurant.location?.lowercase()?.contains(lowerCaseQuery) == true ||
//                                restaurant.cuisineType?.lowercase()?.contains(lowerCaseQuery) == true ||
//                                restaurant.diningType?.lowercase()?.contains(lowerCaseQuery) == true) {
//                                filteredRestaurants.add(restaurant)
//                            }
//                        }
//                        restoAdapter.setData(filteredRestaurants)
//                    } else {
//                        restoAdapter.setData(tempRestoData)
//                    }
//                    return true
//                }
//
//                override fun onQueryTextChange(newText: String?): Boolean {
//                    return false
//                }
//            }
// Listen for changes to the search query input in real time

        viewBinding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    val filteredRestaurants = ArrayList<RestaurantPreviewModel>()
                    for (restaurant in restoData) {
                        if (restaurant.name?.lowercase()?.contains(newText.lowercase()) == true ||
                            restaurant.location?.lowercase()?.contains(newText.lowercase()) == true ||
                            restaurant.cuisineType?.lowercase()?.contains(newText.lowercase()) == true ||
                            restaurant.diningType?.lowercase()?.contains(newText.lowercase()) == true) {
                            filteredRestaurants.add(restaurant)
                        }
                    }
                    restoAdapter.setData(filteredRestaurants)
                } else {
                    restoAdapter.setData(restoData)
                }
                return true
            }
        })



    }


    private fun showFilterPopup() {
        popupBinding = PopupRestaurantfilterBinding.inflate(layoutInflater)

        val cuisineOptions = arrayOf("--Choose a Cuisine--","American", "Japanese", "Chinese", "Mexican", "Korean", "Filipino")
        val diningOptions = arrayOf("--Choose a Dining Option--", "Fine Dining", "Casual", "Fast Food", "Buffet", "Food Court", "Cafe")
        val options = arrayOf(cuisineOptions, diningOptions)
        val spinnerAdapters = mutableListOf<SpinnerAdapter>()

        for (option in options) {
            spinnerAdapters.add(SpinnerAdapter(this, option))
        }

        popupBinding.btnApply.setOnClickListener{
            val cuisineType = cuisineOptions[popupBinding.spCuisineType.selectedItemPosition]
            val diningType = diningOptions[popupBinding.spDiningType.selectedItemPosition]
//            val minRating = popupBinding.rbFilter.rating.toDouble() ?: 0.0 // Set default value to 0.0 if input is invalid
//            getFilteredRestaurants(cuisineType, diningType, minRating) { filteredRestaurants ->
//                restoData.clear()
//                restoData.addAll(filteredRestaurants)
//                restoAdapter.notifyDataSetChanged()
//            }
            getFilteredRestaurants(cuisineType, diningType) { filteredRestaurants ->
                restoData.clear()
                restoData.addAll(filteredRestaurants)
                restoAdapter.notifyDataSetChanged()
            }
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

    private fun getFilteredRestaurants(
        cuisineType: String,
        diningType: String,
        callback: (List<RestaurantPreviewModel>) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("restaurants")
        var query: Query = collectionRef

        if (cuisineType != "--Choose a Cuisine--") {
            query = query.whereEqualTo("cuisineType", cuisineType)
        }

        if (diningType != "--Choose a Dining Option--") {
            query = query.whereEqualTo("diningType", diningType)
        }

        query.get()
            .addOnSuccessListener { documents ->
                val restaurants = mutableListOf<RestaurantPreviewModel>()
                for (document in documents) {
                    val restaurant = document.toObject(RestaurantPreviewModel::class.java)
                    restaurants.add(restaurant)
                }
                callback(restaurants)
            }
            .addOnFailureListener { exception ->
                // Handle error
            }
    }

    private fun RetrieveReviewsListener() {
        viewBinding.loadingRestoPb.visibility = View.VISIBLE
        database = FirebaseFirestore.getInstance()

        database.collection("restaurants").addSnapshotListener(object :
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
                        restoData.add(dc.document.toObject(RestaurantPreviewModel::class.java))
                    }
                }
                restoAdapter.notifyDataSetChanged()
                viewBinding.loadingRestoPb.visibility = View.INVISIBLE
            }
        })
    }
}
