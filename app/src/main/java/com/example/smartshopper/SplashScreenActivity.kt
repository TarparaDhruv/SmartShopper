package com.example.smartshopper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import kotlinx.android.synthetic.main.activity_splash_screen.*

@GlideModule
class AppGlideModule : AppGlideModule()

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        //hide status bar for fullscreen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        Glide.with(baseContext).load(R.drawable.store_2).into(splash_gif)

        object : Thread() {
            override fun run() {
                goToMain()

            }
        }.start()
    }

    fun goToMain() {
        try {
            Thread.sleep(3000)
            val intent = Intent(baseContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            Log.e("SplashScreen", e.message.toString())
            Toast.makeText(
                applicationContext,
                "An error occurred. Please try again after some time",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}