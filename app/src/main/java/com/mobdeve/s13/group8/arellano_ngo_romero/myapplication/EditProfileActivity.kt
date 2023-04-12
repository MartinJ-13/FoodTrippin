package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.content.ContentValues
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivityEditprofileBinding
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream

@Suppress("DEPRECATION")
class EditProfileActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityEditprofileBinding
    private val pickImage = 100
    private var imageUri: Uri? = null
    private var imageBitmap : Bitmap? = null
    private lateinit var database: FirebaseFirestore
    private var isTherePhoto : Boolean = false


    private val takePicture =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                imageBitmap = data?.extras?.get("data") as Bitmap
                viewBinding.editProfAvatarIv.setImageBitmap(imageBitmap)
                imageUri = null
                isTherePhoto = true
            }
        }

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

    private fun dbHelper(username : String, downloadUri : String?) {
        val query = database.collection("users").whereEqualTo("username", username)

        query.get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    val docRef = document.reference
                    val updateData = hashMapOf(
                        "avatar" to downloadUri,
                        "bio" to viewBinding.editProfBioPt.text.toString()
                    )
                    docRef.update(updateData as Map<String, Any>)
                        .addOnSuccessListener {
                            Log.d(
                                ContentValues.TAG,
                                "Document updated successfully"
                            )
                        }
                        .addOnFailureListener { exception ->
                            Log.d(
                                ContentValues.TAG,
                                "Error updating document ",
                                exception
                            )
                        }
                }
                Toast.makeText(
                    this,
                    "Photo successfully updated/added!",
                    Toast.LENGTH_SHORT
                ).show()
                
                val intent1 = Intent(this, MainActivity::class.java)
                intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent1)

                val intent2 = Intent(this, HomePageActivity::class.java)
                intent2.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent2)

                val intent3 = Intent(this, ProfilemyreviewsActivity::class.java)
                startActivity(intent3)
                finish()
            }.addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Cannot find user ", exception)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityEditprofileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val username = intent.getStringExtra("username").toString()
        val profilePic = intent.getStringExtra("profilePic").toString()
        val bio = intent.getStringExtra("bio").toString()

        if(bio.isNotBlank()){
            viewBinding.editProfBioPt.setText(bio)
        }

        database = FirebaseFirestore.getInstance()

        Picasso.get().load(profilePic).into(viewBinding.editProfAvatarIv)


        viewBinding.editProfOpenCamBtn.setOnClickListener(View.OnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (takePictureIntent.resolveActivity(packageManager) != null) {
                takePicture.launch(takePictureIntent)
            }
        })
        viewBinding.editProfOpenGalBtn.setOnClickListener(View.OnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            val pickImage = 100
            startActivityForResult(gallery, pickImage)
        })

        viewBinding.editProfSaveBtn.setOnClickListener(View.OnClickListener {
            //Upload photo to Firebase Storage
            if(viewBinding.editProfBioPt.text.toString().isNotEmpty() || isTherePhoto) {
                if (isTherePhoto) {
                    if (imageBitmap != null) { //if photo is taken from camera
                        //converts Bitmap to Uri
                        imageUri = convertBitMapToUri(username, imageBitmap!!)
                    }
                    // uploads photo (uri) to firebase storage
                    // if photo is taken from gallery or converted to uri
                    val storageRef =
                        FirebaseStorage.getInstance().getReference("images/avatars/${username}")
                    val uploadTask = storageRef.child("${username}_avatar.jpg")
                        .putFile(imageUri!!) //uploads image to directory in fire storage

                    uploadTask.continueWithTask { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let { throw it }
                        }
                        storageRef.child("${username}_avatar.jpg").downloadUrl
                        //retrieves the downloadUrl of the image
                    }.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val downloadUri = task.result
                            dbHelper(username, downloadUri.toString())
                        }
                    }
                } else {
                    dbHelper(username, profilePic)
                }
            }
            else {
                Toast.makeText(this, "No changes detected!", Toast.LENGTH_SHORT
                ).show()
            }
        })

        viewBinding.editProfCancelBtn.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            viewBinding.editProfAvatarIv.setImageURI(imageUri)
            imageBitmap = null
            isTherePhoto = true
        }
    }
}