package com.example.smartshopper

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment_custom : Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_home, container, false)
        v.button.setOnClickListener {
            Toast.makeText(context, "call scanner", Toast.LENGTH_SHORT).show()
            startActivityForResult(Intent(context, getBarcode::class.java), 10)
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