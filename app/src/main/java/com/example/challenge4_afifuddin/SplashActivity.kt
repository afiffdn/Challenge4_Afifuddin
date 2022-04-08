package com.example.challenge4_afifuddin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.challenge4_afifuddin.database.MainActivity
import com.example.challenge4_afifuddin.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val SPLASH_TIME_OUT:Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler().postDelayed(
            {startActivity(Intent(this,MainActivity::class.java))
            finish()
            },SPLASH_TIME_OUT
        )


    }


}