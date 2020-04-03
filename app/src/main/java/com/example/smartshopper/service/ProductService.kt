package com.example.smartshopper.service

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.smartshopper.RecyclerAdapter
import com.example.smartshopper.model.Product
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_home.view.*

class ProductService(
    private val rootRef: DatabaseReference, private val storage: FirebaseStorage,
    private val v: View, private val context: Context
) {

    var productVersions: MutableList<Product> = ArrayList()
    var item: Map<String, Any>? = null
    lateinit var storeName: String
    lateinit var storeLogo: String

    fun fetchDataByUPC(upc: String) {
        rootRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (postSnapshot in p0.children) {
                    item = (postSnapshot.child("items").child(upc).value) as? Map<String, Any>
                    if (item != null) {
                        storeName = postSnapshot.child("storeName").value.toString()
                        storeLogo = postSnapshot.child("storeLogo").value.toString()
                        var product = Product(storeName, storeLogo, item)
                        productVersions.add(product)
                    } else {
                        Toast.makeText(context, "not found", Toast.LENGTH_LONG).show()
                    }
                }
                if (!productVersions.isEmpty()) {
                    productVersions.sortBy { it -> it.item?.get("price") as Double }
                    var temp = RecyclerAdapter(productVersions, context, storage)
                    v.recyclerView.adapter = temp
                    temp.notifyDataSetChanged()
                } else {
                    Toast.makeText(context, "Item not found!", Toast.LENGTH_LONG).show()

                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.w("HomeFragment", "loadLog:onCancelled", p0.toException())
            }
        })
    }

    fun fetchDataByName(name: String) {
        rootRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (postSnapshot in p0.children) {
                    var items = postSnapshot.child("items").children
                    for (itemSnapshot in items) {
                        var tempItem = itemSnapshot.value as Map<String, Any>
                        if (tempItem.get("name").toString().contains(name, true))
                            item = tempItem
                    }
                    if (item != null) {
                        storeName = postSnapshot.child("storeName").value.toString()
                        storeLogo = postSnapshot.child("storeLogo").value.toString()
                        var product = Product(storeName, storeLogo, item)
                        productVersions.add(product)
                    }
                }
                if (!productVersions.isEmpty()) {
                    productVersions.sortBy { it -> it.item?.get("price") as Double }
                    var temp = RecyclerAdapter(productVersions, context, storage)
                    v.recyclerView.adapter = temp
                    temp.notifyDataSetChanged()
                } else {
                    Toast.makeText(context, "Item not found!", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.w("HomeFragment", "loadLog:onCancelled", p0.toException())
            }
        })
    }


}