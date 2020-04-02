package com.example.smartshopper

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.*
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
        linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        v.searchbar.searchEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Toast.makeText(context, "search hit", Toast.LENGTH_SHORT).show()

                val inp: InputMethodManager =
                    activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inp.hideSoftInputFromWindow(searchEditText.windowToken, 0)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false

        }
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