package com.example.challenge4_afifuddin

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import com.example.challenge4_afifuddin.database.MainActivity
import com.example.challenge4_afifuddin.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val sharePredfile = "kotlinsharepreference"
    private lateinit var binding: ActivitySplashBinding
    private val SPLASH_TIME_OUT:Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragmentsupport :FragmentManager = supportFragmentManager

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences =
            this.getSharedPreferences(sharePredfile, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor =sharedPreferences.edit()
        editor.putString("login","ceklogin")
        editor.apply()

        Handler().postDelayed(
            {
                startActivity(Intent(this,MainActivity::class.java))
            }

            ,SPLASH_TIME_OUT
        )


    }


}