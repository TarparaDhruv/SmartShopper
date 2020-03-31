package com.example.smartshopper

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment_custom : Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    val FIRST_LAUNCH = "FirstLaunch"
    val FIRST_PREF_NAME = "FirstLaunchPref"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v = inflater.inflate(R.layout.fragment_home, container, false)
        v.button.setOnClickListener {
            Toast.makeText(requireContext(), "call scanner", Toast.LENGTH_SHORT).show()
//            val scanner = IntentIntegrator.forFragment(activity?.fragmentManager?.findFragmentById(R.id.navigation_home)); // `this` is the current Fragment
//            scanner.setOrientationLocked(false)
//            scanner.initiateScan()
        }

        linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        v.recyclerView.layoutManager = linearLayoutManager
        val ll = ArrayList<String>()
        ll.add("1")
        ll.add("2")
        ll.add("3")
        ll.add("4")
        ll.add("5")
        ll.add("6")
        ll.add("7")
        ll.add("8")
        v.recyclerView.adapter = RecyclerAdapter(ll)


        return v
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int, data: Intent?
    ) {
        if (resultCode == Activity.RESULT_OK) {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents == null) {
                    Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()
                } else {
                    textView.text = result.contents.toString()
                    Toast.makeText(context, "Scanned: " + result.contents, Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
}