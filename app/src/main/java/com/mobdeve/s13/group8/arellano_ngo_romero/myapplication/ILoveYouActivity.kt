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
            finish()
        })


    }
}