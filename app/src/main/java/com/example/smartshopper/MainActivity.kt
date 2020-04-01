package com.example.smartshopper

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    val FIRST_LAUNCH = "FirstLaunch"
    val FIRST_PREF_NAME = "FirstLaunchPref"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // chechk for first install and save it in shared preference
        var sharedPref = getSharedPreferences(FIRST_PREF_NAME, Context.MODE_PRIVATE)
        if (sharedPref.getBoolean(FIRST_LAUNCH, true)) {
            startActivity(Intent(baseContext, Intro::class.java))
        }
        with(sharedPref.edit()) {
            putBoolean(FIRST_LAUNCH, false)
            commit()
        }
    }

}
