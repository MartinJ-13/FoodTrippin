package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding : ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.loginBtn.setOnClickListener(View.OnClickListener {
            if(viewBinding.inputUserLoginTv.text.toString().isNotEmpty() && viewBinding.inputLogPasswordTv.text.toString().isNotEmpty())
            {
                val intent = Intent(applicationContext, HomePageActivity::class.java)
                this.startActivity(intent)
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

        viewBinding.tempBtn.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, RestaurantActivity::class.java)
            this.startActivity(intent)
        })

        viewBinding.tempBtn2.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, ReviewActivity::class.java)
            this.startActivity(intent)
        })
    }
}