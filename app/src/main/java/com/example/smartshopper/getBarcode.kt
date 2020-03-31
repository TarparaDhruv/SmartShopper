package com.example.smartshopper

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator

class getBarcode : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        val scanner = IntentIntegrator(this)
        scanner.setOrientationLocked(false)
        scanner.initiateScan()
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int, data: Intent?
    ) {
        if (resultCode == Activity.RESULT_OK) {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents != null) {
                    val data = Intent()
                    data.data = Uri.parse(result.contents)
                    setResult(Activity.RESULT_OK, data)
                    finish()
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
}