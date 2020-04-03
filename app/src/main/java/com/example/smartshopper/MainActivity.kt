package com.example.smartshopper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.google.android.material.bottomnavigation.BottomNavigationView

@GlideModule
class AppGlideModule: AppGlideModule()

class MainActivity : AppCompatActivity() {
    val INTRO_COMPLETED = "IntroCompleted"
    val FIRST_PREF_NAME = "FirstLaunchPref"
    val REQUEST_CODE_INTRO = 1

    lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_shoppinglist, R.id.navigation_home, R.id.navigation_barcode
            )
        )

        //supportActionBar
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // chechk for first install and intro completed or not and save it in shared preference
        sharedPref = getSharedPreferences(FIRST_PREF_NAME, Context.MODE_PRIVATE)
        if (!sharedPref.getBoolean(INTRO_COMPLETED, false)) {
            startActivityForResult(
                Intent(baseContext, IntroActivity::class.java),
                REQUEST_CODE_INTRO
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_INTRO) {
            sharedPref = getSharedPreferences(FIRST_PREF_NAME, Context.MODE_PRIVATE)
            if (resultCode == Activity.RESULT_OK) {
                with(sharedPref.edit()) {
                    putBoolean(INTRO_COMPLETED, true)
                    commit()
                }
            } else {
                with(sharedPref.edit()) {
                    putBoolean(INTRO_COMPLETED, false)
                    commit()
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.navbar_help) {
            startActivity(Intent(baseContext, HelpActivity::class.java))
        }
        return true
    }

}
