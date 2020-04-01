package com.example.smartshopper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.zxing.integration.android.IntentIntegrator

class delete_for_reference_only : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    val FIRST_LAUNCH = "FirstLaunch"
    val FIRST_PREF_NAME = "FirstLaunchPref"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var sharedPref = getSharedPreferences(FIRST_PREF_NAME, Context.MODE_PRIVATE)
        if (sharedPref.getBoolean(FIRST_LAUNCH, true)) {
            startActivity(Intent(baseContext, Intro::class.java))
        }
        with(sharedPref.edit()) {
            putBoolean(FIRST_LAUNCH, false)
            commit()
        }

//        button.setOnClickListener {
//            val scanner = IntentIntegrator(this)
//            scanner.setOrientationLocked(false)
//            scanner.initiateScan()
//        }

        linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
//        recyclerView.layoutManager = linearLayoutManager

        val ll = ArrayList<String>()
        ll.add("1")
        ll.add("2")
        ll.add("3")
        ll.add("4")
        ll.add("5")
        ll.add("6")
        ll.add("7")
        ll.add("8")

//        recyclerView.adapter = RecyclerAdapter(ll)

    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int, data: Intent?
    ) {
        if (resultCode == Activity.RESULT_OK) {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                } else {
//                    textView.text = result.contents.toString()
                    Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
}
