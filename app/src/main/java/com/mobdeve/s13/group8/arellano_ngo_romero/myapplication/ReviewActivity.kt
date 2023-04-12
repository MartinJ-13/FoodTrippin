package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import androidx.viewbinding.ViewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivityReviewBinding
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.pow
import kotlin.math.roundToInt

@Suppress("DEPRECATION")
class ReviewActivity : AppCompatActivity(){

    private lateinit var viewBinding : ActivityReviewBinding
    private val pickImage = 100

    private var isTherePhoto1 : Boolean = false
    private var submitUri1: Uri? = null
    private var imageURL1: Uri? = null
   // private var uris = ArrayList<Uri?>()
    private val uid = FirebaseAuth.getInstance().currentUser!!.uid
    private var username : String? = null
    private var profilePic: String? = null

    private lateinit var database: FirebaseFirestore

    //function to convert Bitmap to Uri (If the camera was used to take the photo)
    private fun convertBitMapToUri(uid: String, bitmap: Bitmap): Uri? {
        //Save image to phone's storage
        val timestamp = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(Date())

        val dir = File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "review_images")
        if (!dir.exists()) {
            dir.mkdir()
        }
        val file = File(dir, uid + "_review_" + timestamp + ".jpg") //this is the filename
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.close()
        return FileProvider.getUriForFile(this, "com.mobdeve.s13.group8.arellano_ngo_romero.myapplication"+".provider", file)
        //gives permission to open camera and use photo
    }

    //METHOD TO CALL TO OPEN CAMERA INTENT AND ADD IMAGE
       private val takePicture =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val imageBitmap = data?.extras?.get("data") as Bitmap

                if(!isTherePhoto1) {
                    viewBinding.reviewImg1Iv.visibility = View.VISIBLE
                    viewBinding.reviewImg1Iv.setImageBitmap(imageBitmap)
                    submitUri1 = convertBitMapToUri(uid, imageBitmap)
                    isTherePhoto1 = true
                   // uris.add(submitUri1)
                }
            }
        }

    //rounds up averageRating
    fun Double.round(decimalPlaces: Int): Double {
        val factor = 10.0.pow(decimalPlaces.toDouble())
        return (this * factor).roundToInt() / factor
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        database = FirebaseFirestore.getInstance()
        val getUser = database.collection("users").document(uid)

        if (uid != null) {
            getUser.get().addOnSuccessListener { document ->
                if (document != null) {
                    profilePic = document.getString("avatar")
                    username = document.getString("username")
                }
            }

            //SIDEBAR CODE
            // Get the DrawerLayout and NavigationView using view binding
            val drawerLayout = viewBinding.drawerLayout
            val navView = viewBinding.navView
            val restaurantName = intent.getStringExtra("restaurantName").toString()

            viewBinding.restoNameTv.text = restaurantName + "?"

            // Set a click listener for the hamburger icon to open the sidebar
            viewBinding.sidebarNav.setOnClickListener {
                drawerLayout.openDrawer(GravityCompat.START)
            }

            // Set a navigation item selected listener to handle navigation menu item clicks
            navView.setNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_home -> {
                        val intent1 = Intent(this, MainActivity::class.java)
                        intent1.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent1)

                        val intent2 = Intent(this, HomePageActivity::class.java)
                        intent2.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent2)

                        finish()
                        drawerLayout.closeDrawer(GravityCompat.START) // close the drawer layout
                        true
                    }
                    R.id.menu_profile -> {
                        val intent1 = Intent(this, MainActivity::class.java)
                        intent1.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent1)

                        val intent2 = Intent(this, HomePageActivity::class.java)
                        intent2.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
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
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()

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

            viewBinding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
                Toast.makeText(this, "Rating: $rating", Toast.LENGTH_SHORT).show()
            }

            viewBinding.galleryImgBtn.setOnClickListener(View.OnClickListener {
                if (isTherePhoto1) {
                    Toast.makeText(this, "Maximum Photos Uploaded", Toast.LENGTH_SHORT).show()
                } else {
                    val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                    val pickImage = 100
                    startActivityForResult(gallery, pickImage)
                }
            })

            viewBinding.takeCamBtn.setOnClickListener(View.OnClickListener {
                if (isTherePhoto1) {
                    Toast.makeText(this, "Maximum Photos Uploaded", Toast.LENGTH_SHORT).show()
                } else {
                    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    if (takePictureIntent.resolveActivity(packageManager) != null) {
                        takePicture.launch(takePictureIntent)
                    }
                }
            })


            viewBinding.submitBtn.setOnClickListener(View.OnClickListener {
                if (viewBinding.reviewTextTv.text.toString()
                        .isNotEmpty()
                ) {
                    var reviewID: String? = null
                    if(isTherePhoto1) {
                            Log.d(ContentValues.TAG, "Your in uri process " + submitUri1)
                            val timeStamp = SimpleDateFormat(
                                "yyyyMMdd_HHmmss",
                                Locale.getDefault()
                            ).format(Date())
                            val storageRef = FirebaseStorage.getInstance()
                                .getReference("images/reviews/${restaurantName}")
                            val uploadTask = storageRef.child("${username}${timeStamp}_review.jpg").putFile(submitUri1!!)
                            uploadTask.continueWithTask { task ->
                                if (!task.isSuccessful) {
                                    task.exception?.let { throw it }
                                }
                                storageRef.child("${username}${timeStamp}_review.jpg").downloadUrl
                                //retrieves the downloadUrl of the image
                            }.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                        imageURL1 = task.result
                                        Log.d(ContentValues.TAG, "imageURL1 is " + imageURL1)
                                        val reviewData = hashMapOf(
                                            "username" to username,
                                            "restaurant" to restaurantName,
                                            "date" to SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(Date()),
                                            "rating" to viewBinding.ratingBar.rating,
                                            "review" to viewBinding.reviewTextTv.text.toString(),
                                            "userAvatar" to profilePic,
                                            "reviewPicID1" to imageURL1,
                                            "timestamp" to FieldValue.serverTimestamp(),
                                        )
                                        database.collection("reviews")
                                            .document()
                                            .set(reviewData)
                                            .addOnSuccessListener {
                                                Log.d(ContentValues.TAG, "Review added to Firestore")
                                            }.addOnCompleteListener {
                                                //gets the average rating of all reviews.
                                                database.collection("reviews").whereEqualTo("restaurant", restaurantName)
                                                    .get().addOnSuccessListener { documents ->
                                                        var totalRating = 0.0
                                                        var count = 0

                                                        for (document in documents){
                                                           val rating = document.getDouble("rating")
                                                           if (rating != null){
                                                               totalRating += rating
                                                               count++
                                                           }
                                                            val averageRating = (totalRating / count).round(2)
                                                            database.collection("restaurants").whereEqualTo("name", restaurantName)
                                                                .get().addOnSuccessListener { documents ->
                                                                    for (document in documents) {
                                                                        database.collection("restaurants").document(document.id)
                                                                            .update("rating", averageRating)
                                                                            .addOnSuccessListener {
                                                                                Log.d(ContentValues.TAG, "Rating updated for restaurant $restaurantName")
                                                                            }
                                                                            .addOnFailureListener { e ->
                                                                                Log.w(ContentValues.TAG, "Error updating rating for restaurant $restaurantName", e)
                                                                            }
                                                                    }
                                                                }
                                                                .addOnFailureListener { e ->
                                                                    Log.w(ContentValues.TAG, "Error getting restaurant document for $restaurantName", e)
                                                                }
                                                        }
                                                    }
                                            }
                                            .addOnFailureListener { e ->
                                                Log.w(ContentValues.TAG, "Error adding review to Firestore", e)
                                            }
                                }
                            }
                                .addOnFailureListener { e ->
                                    Toast.makeText(
                                        this,
                                        "Error in uploading/converting images $e",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                    }else{
                        val reviewData = hashMapOf(
                            "username" to username,
                            "restaurant" to restaurantName,
                            "date" to SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(Date()),
                            "rating" to viewBinding.ratingBar.rating,
                            "review" to viewBinding.reviewTextTv.text.toString(),
                            "userAvatar" to profilePic,
                            "reviewPicID1" to "",
                        )
                        database.collection("reviews")
                            .document()
                            .set(reviewData)
                            .addOnSuccessListener {
                                Log.d(TAG, "Review added to Firestore")
                            }.addOnCompleteListener {
                                //gets the average rating of all reviews.
                                database.collection("reviews").whereEqualTo("restaurant", restaurantName)
                                    .get().addOnSuccessListener { documents ->
                                        var totalRating = 0.0
                                        var count = 0

                                        for (document in documents){
                                            val rating = document.getDouble("rating")
                                            if (rating != null){
                                                totalRating += rating
                                                count++
                                            }
                                            val averageRating = (totalRating / count).round(2)
                                            database.collection("restaurants").whereEqualTo("name", restaurantName)
                                                .get().addOnSuccessListener { documents ->
                                                    for (document in documents) {
                                                        database.collection("restaurants").document(document.id)
                                                            .update("rating", averageRating)
                                                            .addOnSuccessListener {
                                                                Log.d(ContentValues.TAG, "Rating updated for restaurant $restaurantName")
                                                            }
                                                            .addOnFailureListener { e ->
                                                                Log.w(ContentValues.TAG, "Error updating rating for restaurant $restaurantName", e)
                                                            }
                                                    }
                                                }
                                                .addOnFailureListener { e ->
                                                    Log.w(ContentValues.TAG, "Error getting restaurant document for $restaurantName", e)
                                                }
                                        }
                                    }
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding review to Firestore", e)
                            }
                    }


                    val intent1 = Intent(this, MainActivity::class.java)
                    intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK

                    val intent2 = Intent(this, HomePageActivity::class.java)
                    intent2.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK

                    val intent3 = Intent(this, ILoveYouActivity::class.java)

                    startActivity(intent1)
                    startActivity(intent2)
                    startActivity(intent3)
                    finish()
                } else
                    Toast.makeText(
                        this,
                        "Please leave a review!",
                        Toast.LENGTH_SHORT
                    ).show()
            })
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == pickImage) {
            val imageUri = data?.data
                viewBinding.reviewImg1Iv.visibility = View.VISIBLE
                submitUri1 = imageUri
                viewBinding.reviewImg1Iv.setImageURI(submitUri1)
                isTherePhoto1 = true
            }
        }
    }
