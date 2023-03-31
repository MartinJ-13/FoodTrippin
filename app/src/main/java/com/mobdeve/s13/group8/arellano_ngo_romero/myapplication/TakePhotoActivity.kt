package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivityTakephotoBinding
import java.io.File
import java.io.FileOutputStream

@Suppress("DEPRECATION")
class TakePhotoActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityTakephotoBinding
    private val pickImage = 100
    private var imageUri: Uri? = null
    private var imageBitmap : Bitmap? = null
    private lateinit var database: FirebaseFirestore


    private val takePicture =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                imageBitmap = data?.extras?.get("data") as Bitmap
                viewBinding.userImageIv.setImageBitmap(imageBitmap)
                imageUri = null

                viewBinding.savePhotoBtn.isEnabled = true
                viewBinding.savePhotoBtn.isClickable = true
            }
        }
    //function to convert Bitmap to Uri (If the camera was used to take the photo)
    private fun convertBitMapToUri(username: String, bitmap: Bitmap): Uri? {
        //Save image to phone's storage
        val dir = File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "avatar_images")
        if (!dir.exists()) {
            dir.mkdir()
        }
        val file = File(dir, username + "_avatar.jpg") //this is the filename
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.close()
        return FileProvider.getUriForFile(this, "com.mobdeve.s13.group8.arellano_ngo_romero.myapplication"+".provider", file)
        //gives permission to open camera and use photo
    }


    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityTakephotoBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        //get uid from prev activity
        val username = intent.getStringExtra("username").toString()

        //query uid from database
        database = FirebaseFirestore.getInstance()
        val query = database.collection("users").whereEqualTo("username", username)

        //disable savePhotoBtn
        viewBinding.savePhotoBtn.isEnabled = false
        viewBinding.savePhotoBtn.isClickable = false

        viewBinding.photoBtn.setOnClickListener(View.OnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (takePictureIntent.resolveActivity(packageManager) != null) {
                takePicture.launch(takePictureIntent)
            }
        })
        viewBinding.galleryBtn.setOnClickListener(View.OnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            val pickImage = 100
            startActivityForResult(gallery, pickImage)

        })

        viewBinding.savePhotoBtn.setOnClickListener(View.OnClickListener {
            //Upload photo to Firebase Storage
            if (imageBitmap != null) { //if photo is taken from camera
                //converts Bitmap to Uri
                imageUri = convertBitMapToUri(username, imageBitmap!!)
            }
            // uploads photo (uri) to firebase storage
            // if photo is taken from gallery or converted to uri
            val storageRef =
                FirebaseStorage.getInstance().getReference("images/avatars/${username}")
            val uploadTask = storageRef.child("${username}_avatar.jpg").putFile(imageUri!!) //uploads image to directory in fire storage

            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                storageRef.child("${username}_avatar.jpg").downloadUrl
                //retrieves the downloadUrl of the image
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    query.get()
                        .addOnSuccessListener { querySnapshot ->
                            for (document in querySnapshot.documents) {
                                val docRef = document.reference
                                val updateData = hashMapOf(
                                    "avatar" to downloadUri
                                )
                                docRef.update(updateData as Map<String, Any>)
                                    .addOnSuccessListener {
                                        Log.d(TAG, "Document updated successfully")
                                    }
                                    .addOnFailureListener{exception  ->
                                        Log.d(TAG, "Error updating document ", exception)
                                    }
                            }



                        }.addOnFailureListener{exception ->
                            Log.d(TAG, "Cannot find user ", exception)
                        }
                }
                Toast.makeText(this, "Profile Picture successfully updated/added! You may now login.", Toast.LENGTH_SHORT).show()
                val intent1 = Intent(this, MainActivity::class.java)
                intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent1)
            }

        })


        viewBinding.skipBtn.setOnClickListener(View.OnClickListener {
            val intent1 = Intent(this, MainActivity::class.java)
            intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent1)
            finish()
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            viewBinding.userImageIv.setImageURI(imageUri)
            imageBitmap = null
            viewBinding.savePhotoBtn.isEnabled = true
            viewBinding.savePhotoBtn.isClickable = true
        }
    }
}

//finish all activities and headback to log in
