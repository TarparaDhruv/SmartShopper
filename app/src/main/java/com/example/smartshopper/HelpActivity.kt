package com.example.smartshopper

import android.os.Bundle
import com.example.smartshopper.custom_intro.HelpBarcode
import com.example.smartshopper.custom_intro.HelpContact
import com.example.smartshopper.custom_intro.HelpList
import com.example.smartshopper.custom_intro.HelpSearch
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.app.NavigationPolicy
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide

class HelpActivity : IntroActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Add slides, edit configuration...
        //hides status bar
        isFullscreen = true
        //set slide change duration
        pageScrollDuration = 500
        var custom_slide = FragmentSlide.Builder()
            .background(R.color.colorPrimary)
            .backgroundDark(R.color.colorAccent)
            .fragment(HelpBarcode.newInstance())
            .build()
        addSlide(custom_slide)
        custom_slide = FragmentSlide.Builder()
            .background(R.color.search_back)
            .backgroundDark(R.color.colorAccent)
            .fragment(HelpSearch.newInstance())
            .build()
        addSlide(custom_slide)
        custom_slide = FragmentSlide.Builder()
            .background(R.color.list_back)
            .backgroundDark(R.color.colorAccent)
            .fragment(HelpList.newInstance())
            .build()
        addSlide(custom_slide)
        custom_slide = FragmentSlide.Builder()
            .background(R.color.green_back)
            .backgroundDark(R.color.colorAccent)
            .fragment(HelpContact.newInstance())
            .build()
        addSlide(custom_slide)


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
