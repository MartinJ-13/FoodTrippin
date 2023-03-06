package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivitySignup1Binding
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivitySignup4Binding

class SignUpActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val viewBinding : ActivitySignup4Binding = ActivitySignup4Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.finishBtn.setOnClickListener(View.OnClickListener {
            finish()
        })

    }
}