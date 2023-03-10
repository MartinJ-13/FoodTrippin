package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivitySignup1Binding
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivitySignup4Binding

class SignUpActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val viewBinding : ActivitySignup4Binding = ActivitySignup4Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.finishBtn.setOnClickListener(View.OnClickListener {
            val intent1 = Intent(this, MainActivity::class.java)
            intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent1)
            finish()
            Toast.makeText(this, "You can now Log in!", Toast.LENGTH_SHORT).show()
        })
    }
}