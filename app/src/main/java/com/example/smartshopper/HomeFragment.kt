package com.example.smartshopper

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smartshopper.service.ProductService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var v: View
    private val TAG = "HomeFragment"
    lateinit var mAuth: FirebaseAuth
    lateinit var rootRef: DatabaseReference
    lateinit var storage: FirebaseStorage

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mAuth = FirebaseAuth.getInstance()
        rootRef = FirebaseDatabase.getInstance().reference
        storage = FirebaseStorage.getInstance()
        v = inflater.inflate(R.layout.fragment_home, container, false)

        val handler = Handler()
        val r = Runnable {
            v.loding_gif.visibility = View.GONE
            //what ever you do here will be done after 3 seconds delay.
        }

        linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        v.recyclerView.layoutManager = linearLayoutManager

        if (!GLOBAL_BARCODE.isNullOrEmpty()) {
            v.loding_gif.visibility = View.VISIBLE
            Glide.with(requireContext()).load(R.drawable.searchnew).into(v.loding_gif)
            getData(GLOBAL_BARCODE, Constants.BY_UPC.ordinal)
            handler.postDelayed(r, 3000)
        } else {
            v.loding_gif.visibility = View.VISIBLE
            Glide.with(requireContext()).load(R.drawable.nothing).into(v.loding_gif)
        }

        v.searchbar.searchEditText.setOnEditorActionListener { vv, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                //Toast.makeText(context, v.searchbar.searchEditText.text, Toast.LENGTH_SHORT).show()
                v.loding_gif.visibility = View.VISIBLE
                Glide.with(requireContext()).load(R.drawable.searchnew).into(v.loding_gif)
                handler.postDelayed(r, 3000)
                //hide virtual keyboard
                if (!v.searchbar.searchEditText.text.toString().isNullOrEmpty()) {
                    getData(v.searchbar.searchEditText.text.toString(), Constants.BY_NAME.ordinal)
                } else {
                    v.loding_gif.visibility = View.VISIBLE
                    Glide.with(requireContext()).load(R.drawable.nothing).into(v.loding_gif)
                }
                val inp: InputMethodManager =
                    activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inp.hideSoftInputFromWindow(searchEditText.windowToken, 0)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        return v
    }

    fun getData(queryString: String, queryType: Int) {
        mAuth.signInAnonymously().addOnSuccessListener(requireActivity()) {
            val productService = ProductService(rootRef, storage, v, requireContext())
            if (queryType == Constants.BY_NAME.ordinal)
                productService.fetchDataByName(queryString)
            else if (queryType == Constants.BY_UPC.ordinal)
                productService.fetchDataByUPC(queryString)
        }
            .addOnFailureListener(
                requireActivity()
            ) { exception ->
                Log.d(
                    TAG,
                    "signInAnonymously:FAILURE",
                    exception
                )
            }
    }

}