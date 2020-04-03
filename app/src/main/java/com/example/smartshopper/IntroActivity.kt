package com.example.smartshopper

import android.Manifest
import android.os.Bundle
import com.example.smartshopper.custom_intro.HelpList
import com.example.smartshopper.custom_intro.IntroCamera
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.app.NavigationPolicy
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide

class IntroActivity : IntroActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Add slides, edit configuration...
        //hides status bar
        isFullscreen = true
        pageScrollDuration = 500

        var custom_slide = FragmentSlide.Builder()
            .background(R.color.camera_back)
            .backgroundDark(android.R.color.holo_orange_light)
            .fragment(IntroCamera.newInstance())
            .build()
        addSlide(custom_slide)

        custom_slide = FragmentSlide.Builder()
            .background(R.color.list_back)
            .backgroundDark(android.R.color.holo_orange_light)
            .fragment(HelpList.newInstance())
            .build()
        addSlide(custom_slide)

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