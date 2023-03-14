package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivitySignup3Binding

class SignUpActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val viewBinding : ActivitySignup3Binding = ActivitySignup3Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.photoBtn.setOnClickListener(View.OnClickListener {
                val intent = Intent(applicationContext, SignUpActivity4::class.java)
                this.startActivity(intent)
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