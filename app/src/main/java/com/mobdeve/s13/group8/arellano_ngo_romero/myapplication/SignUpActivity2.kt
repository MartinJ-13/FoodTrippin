package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivitySignup2Binding

class SignUpActivity2 : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database:  FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val viewBinding : ActivitySignup2Binding = ActivitySignup2Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        val username =  intent.getStringExtra("username").toString()
        val email = intent.getStringExtra("email").toString()

        val checkBox = viewBinding.showPasswordCb2

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            val nunitoFont = ResourcesCompat.getFont(applicationContext, R.font.nunito)
            if (isChecked) {
                viewBinding.inputPassTv.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                viewBinding.inputPassTv2.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                //to retain font when inputType is changed
                viewBinding.inputPassTv.typeface = nunitoFont
                viewBinding.inputPassTv2.typeface = nunitoFont
            } else {
                viewBinding.inputPassTv.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                viewBinding.inputPassTv2.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                viewBinding.inputPassTv.typeface = nunitoFont
                viewBinding.inputPassTv2.typeface = nunitoFont
            }
            viewBinding.inputPassTv.setSelection(viewBinding.inputPassTv.text.length)
            viewBinding.inputPassTv2.setSelection(viewBinding.inputPassTv2.text.length)
        }

        viewBinding.createBtn.setOnClickListener(View.OnClickListener {
            val pw1 = viewBinding.inputPassTv.text.toString()
            val pw2 = viewBinding.inputPassTv2.text.toString()
            var uid = ""
            if(pw1.isNotEmpty() && pw2.isNotEmpty()) {
                if(pw1.length >=6 ) {
                    if(pw1 == pw2) {
                            // Register user with email and password

                        firebaseAuth.createUserWithEmailAndPassword(email, pw1)
                            .addOnCompleteListener(this) { task ->
                                if(task.isSuccessful){
                                    val user = firebaseAuth.currentUser

                                    if (user != null){
                                        uid = user.uid
                                        val userData = hashMapOf(
                                            "uid" to user.uid,
                                            "username" to username,
                                            "email" to email,
                                            "avatar" to "https://firebasestorage.googleapis.com/v0/b/food-trippin-prototype.appspot.com/o/images%2Favatars%2Favatar.png?alt=media&token=3c0f2323-6881-4ab2-8764-9a7b9eefb92f",
                                            "bio" to " ",
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
                                    intent.putExtra("uid", uid)
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