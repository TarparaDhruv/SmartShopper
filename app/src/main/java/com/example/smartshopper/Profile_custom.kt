package com.example.smartshopper

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.view.*


class Profile_custom : Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    val FIRST_LAUNCH = "FirstLaunch"
    val FIRST_PREF_NAME = "FirstLaunchPref"
    lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_profiles, container, false)
        Toast.makeText(context, "call scanner", Toast.LENGTH_SHORT).show()
        startActivityForResult(Intent(context, getBarcode::class.java), 10)
        return v
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            Toast.makeText(context, "null data", Toast.LENGTH_LONG).show()
        } else {
            v.scanned_value.text = data.data.toString()
        }

        Toast.makeText(context, data?.data.toString(), Toast.LENGTH_LONG).show()
        //v.scanned_value = data?.data.toString()
        //got the code here
    }

}