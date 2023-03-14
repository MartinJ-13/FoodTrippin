package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
            else{
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
            }
        })
        viewBinding.loginTv.setOnClickListener(View.OnClickListener {
            val intent1 = Intent(this, MainActivity::class.java)
            intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent1)
            finish()
        })
    }
}