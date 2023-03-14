package com.mobdeve.s13.group8.arellano_ngo_romero.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s13.group8.arellano_ngo_romero.myapplication.databinding.ActivityIloveyouBinding

class ILoveYouActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val viewBinding : ActivityIloveyouBinding = ActivityIloveyouBinding.inflate(layoutInflater)
    setContentView(viewBinding.root)

        viewBinding.homeBtn.setOnClickListener(View.OnClickListener {
            val intent1 = Intent(this, MainActivity::class.java)
            intent1.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK

            val intent2 = Intent(this, HomePageActivity::class.java)
            intent2.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK

            val intent3 = Intent(applicationContext, HomePageActivity::class.java)

            startActivity(intent1)
            startActivity(intent2)
            startActivity(intent3)

            finish()
        })


    }
}