package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivitySignup3Binding
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivityTakephotoBinding

@Suppress("DEPRECATION")
class TakePhotoActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityTakephotoBinding
    private val pickImage = 100
    private var imageUri: Uri? = null
    private var imageBitmap : Bitmap? = null


    private val takePicture =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
//                val username = intent.getStringExtra("username").toString()
//                val email = intent.getStringExtra("email").toString()
//                val password = intent.getStringExtra("password").toString()
          //      val viewBinding: ActivityTakephotoBinding = ActivityTakephotoBinding.inflate(layoutInflater)
                val data: Intent? = result.data
                imageBitmap = data?.extras?.get("data") as Bitmap
                viewBinding.userImageIv.setImageBitmap(imageBitmap)
                imageUri = null

                viewBinding.savePhotoBtn.isEnabled = true
                viewBinding.savePhotoBtn.isClickable = true
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityTakephotoBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

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

            val username = intent.getStringExtra("username").toString()
            val email = intent.getStringExtra("email").toString()
            val password = intent.getStringExtra("password").toString()

            val intent = Intent(this, SignUpActivity3::class.java)
            intent.putExtra("username", username)
            intent.putExtra("email", email)
            intent.putExtra("password", password)
            startActivity(intent)
        })


        viewBinding.skipBtn.setOnClickListener(View.OnClickListener {

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
//            val username = intent.getStringExtra("username").toString()
//            val email = intent.getStringExtra("email").toString()
//            val password = intent.getStringExtra("password").toString()
//            //  val imageBitmap = null
//            val intent = Intent(this, SignUpActivity3::class.java)
//            intent.putExtra("username", username)
//            intent.putExtra("email", email)
//            intent.putExtra("password", password)
//            //  intent.putExtra("imageBitmap", null)
//            this.startActivity(intent)