//package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication
//
//import android.annotation.SuppressLint
//import android.content.Intent
//import android.graphics.Bitmap
//import android.net.Uri
//import android.os.Bundle
//import android.os.Environment
//import android.view.View
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.content.FileProvider
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.storage.FirebaseStorage
//import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivitySignup1Binding
//import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivitySignup4Binding
//import java.io.File
//import java.io.FileOutputStream
//import java.io.IOException
//
//class `SignUpActivity4(copy)` : AppCompatActivity() {
//
//    private lateinit var firebaseAuth: FirebaseAuth
//    private lateinit var database:  DatabaseReference
//    private lateinit var storage: FirebaseStorage
//    private lateinit var imageUri: Uri
//    @SuppressLint("WrongThread")
//    override fun onCreate(savedInstanceState: Bundle?){
//        super.onCreate(savedInstanceState)
//        val viewBinding : ActivitySignup4Binding = ActivitySignup4Binding.inflate(layoutInflater)
//        setContentView(viewBinding.root)
//
//        firebaseAuth = FirebaseAuth.getInstance()
//        storage = FirebaseStorage.getInstance()
//        database = FirebaseDatabase.getInstance().getReference("Users")
//
//        val imageBitmap = intent.getParcelableExtra<Bitmap>("imageBitmap")
//        val username =  intent.getStringExtra("username").toString()
//        val email = intent.getStringExtra("email").toString()
//        val password = intent.getStringExtra("password").toString()
//
//        if(imageBitmap != null)
//            viewBinding.userImgIv.setImageBitmap(imageBitmap)
//
//        if(username != null)
//            viewBinding.congratsUserTv.text = "@$username!"
//
//        viewBinding.finishBtn.setOnClickListener(View.OnClickListener {
//            // Register user with email and password
//            firebaseAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                        // User registration successful
//                        val firebaseUser = firebaseAuth.currentUser
//                        if (firebaseUser != null) {
//                            // Get user ID
//                            val userId = firebaseUser.uid
//
//                            // Save image to phone's storage
//                            val dir = File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "avatar_images")
//                            if(!dir.exists())
//                                dir.mkdir()
//                            val file = File(dir, username + "_avatar.jpg")
//                            val outputStream = FileOutputStream(file)
//                            imageBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
//                            outputStream.close()
//                            imageUri = FileProvider.getUriForFile(this, "com.example.myapp.fileprovider", file)
//
//                            // Upload image to storage
//                            val storageRef = FirebaseStorage.getInstance().getReference("images/${userId}_avatar.jpg")
//                            val uploadTask = storageRef.putFile(Uri.fromFile(file))
//                            uploadTask.addOnSuccessListener {
//                                // Get the download URL of the uploaded image
//                                storageRef.downloadUrl.addOnSuccessListener { uri ->
//                                    // Save the user info and the image URI to the database
//                                    val user = User(username, email, imageBitmap, uri.toString())
//                                    database.child(username).setValue(user).addOnCompleteListener {
//                                        if (it.isSuccessful) {
//                                            // Navigate to the main activity on success
//                                            val intent = Intent(this, MainActivity::class.java)
//                                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
//                                            startActivity(intent)
//                                        } else {
//                                            Toast.makeText(this, "Failed to save user info", Toast.LENGTH_SHORT).show()
//                                        }
//                                    }
//                                }
//                            }.addOnFailureListener {
//                                Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
//                            }
//                        }
//                    } else {
//                        // User registration failed
//                        Toast.makeText(this, "Authentication failed: " + task.exception?.message, Toast.LENGTH_SHORT).show()
//                    }
//                }
////            finish()
////            Toast.makeText(this, "You can now Log in!", Toast.LENGTH_SHORT).show()
//        })
//
//    }
//}