package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivitySignup1Binding
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivitySignup4Binding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class SignUpActivity4 : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database:  FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private lateinit var imageUri: Uri

    @SuppressLint("WrongThread")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val viewBinding : ActivitySignup4Binding = ActivitySignup4Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        database = FirebaseFirestore.getInstance()
        
        val imageBitmap = intent.getParcelableExtra<Bitmap>("imageBitmap")
        val username =  intent.getStringExtra("username").toString()
        val email = intent.getStringExtra("email").toString()
        val password = intent.getStringExtra("password").toString()

        if(imageBitmap != null)
            viewBinding.userImgIv.setImageBitmap(imageBitmap)

        if(username != null)
            viewBinding.congratsUserTv.text = "@$username!"

        viewBinding.finishBtn.setOnClickListener(View.OnClickListener {
            // Register user with email and password
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // User registration successful
                        val firebaseUser = firebaseAuth.currentUser
//                        if (firebaseUser != null) {
//                            // Get user ID
//                            val userId = firebaseUser.uid
//
//
//                            //CREATES THE URI OF THE BITMAP
//
//                            val uri: Uri?
//                            val bytes = ByteArrayOutputStream()
//                            imageBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
//                            val storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//                            val imagePath = username + "_avatar.jpg"
//                            val imageFile = File(storageDir, imagePath)
//                            imageFile.createNewFile()
//                            val fos = FileOutputStream(imageFile)
//                            fos.write(bytes.toByteArray())
//                            fos.flush()
//                            fos.close()
//                            uri = FileProvider.getUriForFile(this,"${applicationContext.packageName}.provider", imageFile)
//
//                            //UPLOAD IMAGE TO FIREBASE STORAGE
//                            val storageRef = FirebaseStorage.getInstance().getReference("images")
//                            val imageRef = storageRef.child(imagePath)
//                            val uploadTask = imageRef.putFile(uri)
//
//
//                            storage.getReference("images").child(System.currentTimeMillis().toString())
//                                .putFile(uri)
//                                .addOnSuccessListener { task ->
//
//                                }
//                            val user = User(username, email, uri.toString())
//
//                            dbCourses.add(user).addOnSuccessListener { documentReference ->
//                                Toast.makeText(this, "Successfully added User Info", Toast.LENGTH_SHORT).show()
//                            }
//                                .addOnFailureListener{ e ->
//                                    Toast.makeText(this, "Failed to add user", Toast.LENGTH_SHORT).show()
//                                }
//                            //END OF ADDING DATA TO FIRESTORE
//                        }
                    } else {
                        // User registration failed
                        Toast.makeText(this, "Authentication failed: " + task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
//            finish()
//            Toast.makeText(this, "You can now Log in!", Toast.LENGTH_SHORT).show()
        })

    }

}