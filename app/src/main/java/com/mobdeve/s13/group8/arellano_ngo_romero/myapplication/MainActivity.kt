package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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

        viewBinding.loginBtn.setOnClickListener(View.OnClickListener {
            if(viewBinding.inputUserLoginTv.text.toString().isNotEmpty() && viewBinding.inputLogPasswordTv.text.toString().isNotEmpty())
            {
                val email = viewBinding.inputUserLoginTv.text.toString()
                val password = viewBinding.inputLogPasswordTv.text.toString()
                val checkBox = viewBinding.stayLogInCb
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            if(checkBox.isChecked){
                               //insert way to make keep the user logged in
                            }
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
}