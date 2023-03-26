package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivitySignup3Binding

class SignUpActivity3 : AppCompatActivity() {
    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }
    private val takePicture = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val username =  intent.getStringExtra("username").toString()
            val email = intent.getStringExtra("email").toString()
            val password = intent.getStringExtra("password").toString()
            val data: Intent? = result.data
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val intent = Intent(this, SignUpActivity4::class.java)
            intent.putExtra("imageBitmap", imageBitmap)
            intent.putExtra("username", username)
            intent.putExtra("email", email)
            intent.putExtra("password", password)
            startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val viewBinding : ActivitySignup3Binding = ActivitySignup3Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)


        viewBinding.photoBtn.setOnClickListener(View.OnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (takePictureIntent.resolveActivity(packageManager) != null) {
                takePicture.launch(takePictureIntent)
            }
        })

        viewBinding.skipBtn.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, SignUpActivity4::class.java)
            this.startActivity(intent)
        })
        viewBinding.loginTv.setOnClickListener(View.OnClickListener {
            val intent1 = Intent(this, MainActivity::class.java)
            intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent1)
            finish()
        })
    }
}