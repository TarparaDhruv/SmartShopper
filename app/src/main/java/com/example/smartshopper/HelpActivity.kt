package com.example.smartshopper

import android.os.Bundle
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.app.NavigationPolicy
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide

class HelpActivity : IntroActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Add slides, edit configuration...
        //hides status bar
        isFullscreen = true
        pageScrollDuration = 500
        addSlide(
            SimpleSlide.Builder()
                .title("First Help")
                .description("HELPHELPHELP HELPHELPHELP HELPHELPHELP")
                .image(android.R.drawable.ic_menu_mylocation)
                .background(android.R.color.darker_gray)
                .backgroundDark(android.R.color.holo_orange_light)
                .scrollable(false)
                .build()
        )
        addSlide(
            SimpleSlide.Builder()
                .title("HELPHELPHELP 2")
                .description("HELPHELPHELP HELPHELPHELP HELPHELPHELP")
                .image(android.R.drawable.ic_menu_camera)
                .background(android.R.color.holo_blue_light)
                .backgroundDark(android.R.color.holo_orange_light)
                .scrollable(false)
                .build()
        )
//        var temp = FragmentSlide.Builder()
//        Layou
//        addSlide(
//            FragmentSlide.Builder()
//                .background(R.color.blue_back)
//                .backgroundDark(R.color.colorPrimaryDark)
//                .fragment(R.layout.fragment_custom)
//                .build()
//        )
//        Glide.with(applicationContext).load(R.drawable.store_2).into(findViewById(R.id.helpslide))
        addSlide(
            SimpleSlide.Builder()
                .title("Slide 3")
                .description("Slide description 3")
                .image(android.R.drawable.ic_media_next)
                .background(android.R.color.holo_blue_light)
                .backgroundDark(android.R.color.holo_orange_light)
                .scrollable(false)
                .build()
        )
        val loginSlide = FragmentSlide.Builder()
            .background(R.color.blue_back)
            .backgroundDark(R.color.colorPrimaryDark)
            .fragment(demo.newInstance())
            .build()
        addSlide(loginSlide)


        setNavigationPolicy(object : NavigationPolicy {
            override fun canGoForward(position: Int): Boolean {
                return true
            }

            override fun canGoBackward(position: Int): Boolean {
                return true
            }
        })

        /* Show/hide button */
        isButtonBackVisible = true
        /* Use skip button behavior */
        buttonBackFunction = BUTTON_BACK_FUNCTION_BACK

        /* Show/hide button */
        //setButtonNextVisible(true);
        /* Use next and finish button behavior */
        //setButtonNextFunction(BUTTON_NEXT_FUNCTION_NEXT_FINISH);
    }

}
