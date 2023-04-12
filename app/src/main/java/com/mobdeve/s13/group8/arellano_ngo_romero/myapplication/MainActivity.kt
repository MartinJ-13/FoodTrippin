package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding : ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        val checkBox = viewBinding.showPasswordCb

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            val nunitoFont = ResourcesCompat.getFont(applicationContext, R.font.nunito)
            if (isChecked) {
                viewBinding.inputLogPasswordTv.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                viewBinding.inputLogPasswordTv.typeface = nunitoFont
            } else {
                viewBinding.inputLogPasswordTv.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                viewBinding.inputLogPasswordTv.typeface = nunitoFont
            }
            viewBinding.inputLogPasswordTv.setSelection(viewBinding.inputLogPasswordTv.text.length)
        }

        viewBinding.loginBtn.setOnClickListener(View.OnClickListener {
            if(viewBinding.inputUserLoginTv.text.toString().isNotEmpty() && viewBinding.inputLogPasswordTv.text.toString().isNotEmpty())
            {
                val email = viewBinding.inputUserLoginTv.text.toString()
                val password = viewBinding.inputLogPasswordTv.text.toString()

                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(applicationContext, HomePageActivity::class.java)
                            this.startActivity(intent)
                            val user = firebaseAuth.currentUser
                        } else {
                            Toast.makeText(this, "Incorrect email or password.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            else
            {
                Toast.makeText(this, "Please enter your username and password.", Toast.LENGTH_SHORT).show()
            }
        })

        viewBinding.loginTv.setOnClickListener(View.OnClickListener {
                val intent = Intent(applicationContext, SignUpActivity1::class.java)
                this.startActivity(intent)
        })
    }

    override fun onStart() {
        super.onStart()

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            // User is already logged in, so launch the desired activity
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
            finish() // optional - this will prevent the user from being able to go back to the login screen using the back button
        }
    }
}