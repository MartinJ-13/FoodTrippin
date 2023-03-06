package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivitySignup1Binding

class SignUpActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val viewBinding : ActivitySignup1Binding = ActivitySignup1Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.nextBtn.setOnClickListener(View.OnClickListener {
            if(viewBinding.inputUserTv.text.toString().isNotEmpty())
            {
                val intent = Intent(applicationContext, SignUpActivity2::class.java)
                this.startActivity(intent)
            }
        })

        viewBinding.loginTv.setOnClickListener(View.OnClickListener {
            finish()
        })
    }
}