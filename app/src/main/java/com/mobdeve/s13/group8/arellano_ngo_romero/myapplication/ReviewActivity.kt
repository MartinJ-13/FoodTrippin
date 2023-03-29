package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.GravityCompat
import androidx.viewbinding.ViewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivityReviewBinding
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class ReviewActivity : AppCompatActivity(){

    private lateinit var viewBinding : ActivityReviewBinding
    private val pickImage = 100

    private var isTherePhoto1 : Boolean = false
    private var isTherePhoto2 : Boolean = false
    private var submitUri1: Uri? = null
    private var submitUri2: Uri? = null
    private var uris = ArrayList<Uri?>()
    private val user = FirebaseAuth.getInstance().currentUser!!.uid

    private lateinit var database: FirebaseFirestore


    //function to convert Bitmap to Uri (If the camera was used to take the photo)
    private fun convertBitMapToUri(username: String, bitmap: Bitmap): Uri? {
        //Save image to phone's storage
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())

        val dir = File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "review_images")
        if (!dir.exists()) {
            dir.mkdir()
        }
        val file = File(dir, username + "_review_" + timestamp + ".jpg") //this is the filename
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
                    submitUri1 = convertBitMapToUri(user, imageBitmap!!)
                    isTherePhoto1 = true
                    uris.add(submitUri1)
                }
                else {
                    viewBinding.reviewImg2Iv.visibility = View.VISIBLE
                    viewBinding.reviewImg2Iv.setImageBitmap(imageBitmap)
                    submitUri2 = convertBitMapToUri(user, imageBitmap!!)
                    isTherePhoto2 = true
                    uris.add(submitUri2)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewBinding = ActivityReviewBinding.inflate(layoutInflater)
    setContentView(viewBinding.root)

        //SIDEBAR CODE
        // Get the DrawerLayout and NavigationView using view binding
        val drawerLayout = viewBinding.drawerLayout
        val navView = viewBinding.navView
        val restaurantName = intent.getStringExtra("restaurantName").toString()

        viewBinding.restoNameTv.text = restaurantName

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
        var imageCount = 0

        viewBinding.ratingBar.rating = 2.5f
        viewBinding.ratingBar.stepSize = .5f

        viewBinding.ratingBar.setOnRatingBarChangeListener{ ratingBar, rating, fromUser ->
            Toast.makeText(this, "Rating: $rating", Toast.LENGTH_SHORT).show()
        }

        viewBinding.galleryImgBtn.setOnClickListener(View.OnClickListener {
           if(isTherePhoto1 && isTherePhoto2) {
               Toast.makeText(this, "Maximum Photos Uploaded", Toast.LENGTH_SHORT).show()
           }
            else {
               val gallery =
                   Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
               val pickImage = 100
               startActivityForResult(gallery, pickImage)
           }
        })

        viewBinding.takeCamBtn.setOnClickListener(View.OnClickListener {
            if(isTherePhoto1 && isTherePhoto2) {
                Toast.makeText(this, "Maximum Photos Uploaded", Toast.LENGTH_SHORT).show()
            }
            else {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (takePictureIntent.resolveActivity(packageManager) != null) {
                    takePicture.launch(takePictureIntent)
                }
            }
        })


        viewBinding.submitBtn.setOnClickListener(View.OnClickListener {
            if((isTherePhoto1 || isTherePhoto2) && viewBinding.reviewTextTv.text.toString().isNotEmpty()) {
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == pickImage) {
            val imageUri= data?.data
            if(!isTherePhoto1) {
                viewBinding.reviewImg1Iv.visibility = View.VISIBLE
                viewBinding.reviewImg1Iv.setImageURI(imageUri)
                isTherePhoto1 = true
                uris.add(imageUri)
            }
            else{
                viewBinding.reviewImg2Iv.visibility = View.VISIBLE
                viewBinding.reviewImg2Iv.setImageURI(imageUri)
                isTherePhoto2 = true
                uris.add(imageUri)
            }
        }
    }
}