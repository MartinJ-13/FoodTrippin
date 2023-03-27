package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivitySignup1Binding

class SignUpActivity1 : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val viewBinding : ActivitySignup1Binding = ActivitySignup1Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //function check if the email given is not empty and in proper format (address@mail.com)
        //uses the library EmailValidator (commons-validator)

        fun String.isValidEmail() =
            Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}").matches(this)

        viewBinding.nextBtn.setOnClickListener(View.OnClickListener {

            val username = viewBinding.inputUserTv.text.toString()
            val email = viewBinding.inputUserTv2.text.toString()

            if(username.isNotEmpty() && email.isNotEmpty())
            {
                if(email.isValidEmail()) {
                    val intent = Intent(applicationContext, SignUpActivity2::class.java)
                    intent.putExtra("username", username)
                    intent.putExtra("email", email)
                    startActivity(intent)
                    this.startActivity(intent)
                    this.overridePendingTransition(0, 0);
                }
                else
                    Toast.makeText(this, "Email is invalid", Toast.LENGTH_SHORT).show()
            }
            else
                Toast.makeText(this, "Please enter your Username and Email", Toast.LENGTH_SHORT).show()
        })

        viewBinding.loginTv.setOnClickListener(View.OnClickListener {
            val intent1 = Intent(this, MainActivity::class.java)
            intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent1)
            finish()
        })


    }
}