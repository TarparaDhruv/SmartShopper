package com.example.smartshopper.ui.profiles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.smartshopper.R

class ProfilesFragment : Fragment() {

    private lateinit var profilesViewModel: ProfilesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profilesViewModel =
            ViewModelProviders.of(this).get(ProfilesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profiles, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        profilesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
