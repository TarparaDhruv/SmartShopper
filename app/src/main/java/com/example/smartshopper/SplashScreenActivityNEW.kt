package com.example.smartshopper

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import kotlinx.android.synthetic.main.activity_splash_screen_new.*


@GlideModule
class AppGlideModule : AppGlideModule()

class SplashScreenActivityNEW : AppCompatActivity() {

    private val TAG = "SplashScreen"
    private var permissionsRequestedCount: Int = 0
    private val INTERNET_REQUEST_CODE = 101
    private val ACCESS_NETWORK_STATE_REQUEST_CODE = 102
    private val CAMERA_REQUEST_CODE = 103

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen_new)
        //hide status bar for fullscreen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        Glide.with(baseContext).load(R.drawable.store_2).into(show_gif_here)

        val backgroundTask = object : Thread() {
            override fun run() {

                checkAllPermissions(
                    this@SplashScreenActivityNEW, allPermissions = hashMapOf(
                        Manifest.permission.INTERNET to INTERNET_REQUEST_CODE,
                        Manifest.permission.ACCESS_NETWORK_STATE to ACCESS_NETWORK_STATE_REQUEST_CODE,
                        Manifest.permission.CAMERA to CAMERA_REQUEST_CODE
                    )
                )

            }
        }
        backgroundTask.start()
    }

    fun checkAllPermissions(currentActivity: AppCompatActivity, allPermissions: Map<String, Int>) {
        allPermissions.forEach { permissionName, permissionRequestCode ->
            run {
                val permission = ContextCompat.checkSelfPermission(currentActivity, permissionName)
                if (permission != PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Requesting permission for ${permissionName}.")
                    permissionsRequestedCount++
                    ActivityCompat.requestPermissions(
                        currentActivity,
                        arrayOf(permissionName),
                        permissionRequestCode
                    )
                }
            }
        }
        goToMain()
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
