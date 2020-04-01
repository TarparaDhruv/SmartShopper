package com.example.smartshopper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
//        v.multiSearchView.setSearchViewListener(object : MultiSearchView.MultiSearchViewListener {
//            override fun onItemSelected(index: Int, s: CharSequence) {
//                Toast.makeText(context, s, Toast.LENGTH_LONG).show()
//            }
//
//            override fun onTextChanged(index: Int, s: CharSequence) {
//                Toast.makeText(context, s, Toast.LENGTH_LONG).show()
//            }
//
//            override fun onSearchComplete(index: Int, s: CharSequence) {
//                Toast.makeText(context, s, Toast.LENGTH_LONG).show()
//            }
//
//            override fun onSearchItemRemoved(index: Int) {
//                Toast.makeText(context, index.toString(), Toast.LENGTH_LONG).show()
//            }
//
//        })
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
}