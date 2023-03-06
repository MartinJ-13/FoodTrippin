package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivitySignup2Binding

class SignUpActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val viewBinding : ActivitySignup2Binding = ActivitySignup2Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.createBtn.setOnClickListener(View.OnClickListener {
            if(viewBinding.inputPassTv.text.toString().isNotEmpty() && viewBinding.inputPassTv2.text.toString().isNotEmpty())
            {
                val intent = Intent(applicationContext, SignUpActivity3::class.java)
                this.startActivity(intent)
            }
        })
        viewBinding.loginTv.setOnClickListener(View.OnClickListener {
            finish()
        })
    }
}