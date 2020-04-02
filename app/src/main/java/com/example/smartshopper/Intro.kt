package com.example.smartshopper

import android.Manifest
import android.os.Bundle
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.app.NavigationPolicy
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide


class Intro : IntroActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Add slides, edit configuration...
        //hides status bar
        isFullscreen = true
        pageScrollDuration = 500
        addSlide(
            SimpleSlide.Builder()
                .title("First Slide")
                .description("Need location access to provide directions")
                .image(android.R.drawable.ic_menu_mylocation)
                .background(android.R.color.darker_gray)
                .backgroundDark(android.R.color.holo_orange_light)
                .scrollable(false)
                .build()
        )
        addSlide(
            SimpleSlide.Builder()
                .title("Camera")
                .description("Need camera permission to scan barcode to ease up searching")
                .image(android.R.drawable.ic_menu_camera)
                .background(android.R.color.holo_blue_light)
                .backgroundDark(android.R.color.holo_orange_light)
                .scrollable(false)
                .permission(Manifest.permission.CAMERA)
                .build()
        )
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
        setNavigationPolicy(object : NavigationPolicy {
            override fun canGoForward(position: Int): Boolean {
                return true
            }

            override fun canGoBackward(position: Int): Boolean {
                return true
            }
        })

        /* Show/hide button */
        setButtonBackVisible(true)
        /* Use skip button behavior */
        setButtonBackFunction(BUTTON_BACK_FUNCTION_BACK)

        /* Show/hide button */
        //setButtonNextVisible(true);
        /* Use next and finish button behavior */
        //setButtonNextFunction(BUTTON_NEXT_FUNCTION_NEXT_FINISH);
    }

}