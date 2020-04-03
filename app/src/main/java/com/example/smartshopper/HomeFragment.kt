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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smartshopper.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var v: View
    private val TAG = "HomeFragment"
    var productVersions: MutableList<Product> = ArrayList()
    lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mAuth = FirebaseAuth.getInstance()
        v = inflater.inflate(R.layout.fragment_home, container, false)
        linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        v.searchbar.searchEditText.setOnEditorActionListener { vv, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                Toast.makeText(context, "search hit", Toast.LENGTH_SHORT).show()
                v.loding_gif.visibility = View.VISIBLE
                Glide.with(requireContext()).load(R.drawable.store_2).into(v.loding_gif)
                var handler = Handler()
                val r = Runnable {
                    v.loding_gif.visibility = View.GONE
                    //what ever you do here will be done after 3 seconds delay.
                }
                handler.postDelayed(r, 3000)
                //hide virtual keyboard
                val inp: InputMethodManager =
                    activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inp.hideSoftInputFromWindow(searchEditText.windowToken, 0)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        v.recyclerView.layoutManager = linearLayoutManager
        var upc = "901030756511" //Barcode

        mAuth.signInAnonymously().addOnSuccessListener(requireActivity()) {
            fetchData(upc)
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
        return v
    }

    fun fetchData(upc: String) {
        var rootRef = FirebaseDatabase.getInstance().reference
        var item: Map<String, Any>?
        lateinit var storeName: String
        lateinit var storeLogo: String
        rootRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (postSnapshot in p0.children) {
                    item = postSnapshot.child("items").child(upc).value as Map<String, Any>?
                    if (item != null) {
                        storeName = postSnapshot.child("storeName").value.toString()
                        storeLogo = postSnapshot.child("storeLogo").value.toString()
                        var product = Product(storeName, storeLogo, item)
                        productVersions.add(product)
                    } else {
                        Toast.makeText(context, "Item not found!", Toast.LENGTH_LONG).show()
                    }
                }
                productVersions.sortBy { it -> it.item?.get("price") as Double }
                var storage = FirebaseStorage.getInstance()
                var temp = RecyclerAdapter(productVersions, requireContext(), storage)
                v.recyclerView.adapter = temp
                temp.notifyDataSetChanged()
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.w("HomeFragment", "loadLog:onCancelled", p0.toException())
            }
        })
    }
}

