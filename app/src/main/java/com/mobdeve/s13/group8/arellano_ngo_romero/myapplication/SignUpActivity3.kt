package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivitySignup3Binding

class SignUpActivity3 : AppCompatActivity() {


    @SuppressLint("WrongThread", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val viewBinding : ActivitySignup3Binding = ActivitySignup3Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        
//        val imageBitmap = intent.getParcelableExtra<Bitmap>("imageBitmap")
//        val imageUri = intent.getStringExtra("imageUri")
        val username =  intent.getStringExtra("username").toString()
        val email = intent.getStringExtra("email").toString()
        val password = intent.getStringExtra("password").toString()


        if(username != null) {
            viewBinding.congratsTv1.text =
                "Congratulations, @$username! \n You have successfully \n created your account."
        }

        //take photo
        viewBinding.finishBtn2.setOnClickListener(View.OnClickListener {
            val intent1 = Intent(this, MainActivity::class.java)
            intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent1)

            val intent = Intent(this, TakePhotoActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("email", email)
            intent.putExtra("password", password)
            startActivity(intent)

        })
        //login
        viewBinding.finishBtn1.setOnClickListener(View.OnClickListener {
            finish()
            FirebaseAuth.getInstance().signOut() //forces new users to login
        })
    }

}