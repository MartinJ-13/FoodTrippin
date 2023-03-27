package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivitySignup2Binding

class SignUpActivity2 : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database:  FirebaseFirestore
   // private lateinit var storage: FirebaseStorage
  //  private lateinit var imageUri: Uri
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val viewBinding : ActivitySignup2Binding = ActivitySignup2Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        firebaseAuth = FirebaseAuth.getInstance()
    //    storage = FirebaseStorage.getInstance()
        database = FirebaseFirestore.getInstance()

        val username =  intent.getStringExtra("username").toString()
        val email = intent.getStringExtra("email").toString()


        viewBinding.createBtn.setOnClickListener(View.OnClickListener {
            val pw1 = viewBinding.inputPassTv.text.toString()
            val pw2 = viewBinding.inputPassTv2.text.toString()

            if(pw1.isNotEmpty() && pw2.isNotEmpty()) {
                if(pw1.length >=6 ) {
                    if(pw1 == pw2) {
                            // Register user with email and password

                        firebaseAuth.createUserWithEmailAndPassword(email, pw1)
                            .addOnCompleteListener(this) { task ->
                                if(task.isSuccessful){
                                    val user = firebaseAuth.currentUser

                                    if (user != null){
                                        val userData = hashMapOf(
                                            "uid" to user.uid,
                                            "username" to username,
                                            "email" to email,
                                            "avatar" to null
                                        )
                                        database.collection("users")
                                            .document(user.uid)
                                            .set(userData)
                                            .addOnSuccessListener {
                                                Log.d(TAG, "User data added to Firestore")
                                            }
                                            .addOnFailureListener{ e ->
                                                Log.w(TAG, "Error adding user data to Firestore", e)
                                            }

                                    }
                                    val intent1 = Intent(this, MainActivity::class.java)
                                    intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent1)


                                    val intent = Intent(applicationContext, SignUpActivity3::class.java)
                                    intent.putExtra("username", username)
                                    intent.putExtra("email", email)
                                    intent.putExtra("password", pw1)
                                    startActivity(intent)

                                    finish()
                                    this.overridePendingTransition(0, 0);
                                }
                                else {
                                    Toast.makeText(this, "Authentication failed: " + task.exception?.message, Toast.LENGTH_SHORT).show()
                                }
                            }
                }
                else {
                    Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(this, "Password must have 6 characters or more!", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
            }

        })
        viewBinding.loginTv.setOnClickListener(View.OnClickListener {
            val intent1 = Intent(this, MainActivity::class.java)
            intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent1)
            finish()
        })
    }
}

//                        firebaseAuth.createUserWithEmailAndPassword(email, password)
//                            .addOnCompleteListener(this) { task ->
//                                if (task.isSuccessful) {
//                                    // User registration successful
//                                    val firebaseUser = firebaseAuth.currentUser
////                        if (firebaseUser != null) {
////                            // Get user ID
////                            val userId = firebaseUser.uid
////
////
////                            //CREATES THE URI OF THE BITMAP
////
////                            val uri: Uri?
////                            val bytes = ByteArrayOutputStream()
////                            imageBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
////                            val storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
////                            val imagePath = username + "_avatar.jpg"
////                            val imageFile = File(storageDir, imagePath)
////                            imageFile.createNewFile()
////                            val fos = FileOutputStream(imageFile)
////                            fos.write(bytes.toByteArray())
////                            fos.flush()
////                            fos.close()
////                            uri = FileProvider.getUriForFile(this,"${applicationContext.packageName}.provider", imageFile)
////
////                            //UPLOAD IMAGE TO FIREBASE STORAGE
////                            val storageRef = FirebaseStorage.getInstance().getReference("images")
////                            val imageRef = storageRef.child(imagePath)
////                            val uploadTask = imageRef.putFile(uri)
////
////
////                            storage.getReference("images").child(System.currentTimeMillis().toString())
////                                .putFile(uri)
////                                .addOnSuccessListener { task ->
////
////                                }
////                            val user = User(username, email, uri.toString())
////
////                            dbCourses.add(user).addOnSuccessListener { documentReference ->
////                                Toast.makeText(this, "Successfully added User Info", Toast.LENGTH_SHORT).show()
////                            }
////                                .addOnFailureListener{ e ->
////                                    Toast.makeText(this, "Failed to add user", Toast.LENGTH_SHORT).show()
////                                }
////                            //END OF ADDING DATA TO FIRESTORE
////                        }
//                                } else {
//                                    // User registration failed
//                                    Toast.makeText(this, "Authentication failed: " + task.exception?.message, Toast.LENGTH_SHORT).show()
//                                }
//                            }
////            finish()
////            Toast.makeText(this, "Account successfully created!", Toast.LENGTH_SHORT).show()
//                    })