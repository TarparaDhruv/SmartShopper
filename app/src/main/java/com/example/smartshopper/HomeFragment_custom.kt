package com.example.smartshopper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartshopper.model.Product
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment_custom : Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var v: View
    var productVersions: MutableList<Product> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_home, container, false)


////////////////////////////////////
        var upc = "1234" //Barcode

        var firebaseDatabase = FirebaseDatabase.getInstance()
        val rootRef = firebaseDatabase.reference
        var item: Map<String, Any>?
        lateinit var storeName: String
        rootRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (postSnapshot in p0.children) {
                    item = postSnapshot.child("items").child(upc).value as Map<String, Any>?
                    if (item != null) {
                        storeName = postSnapshot.child("storeName").value.toString()
                        var product = Product(storeName, item)
                        productVersions.add(product)
                    }
                }
                // textView3.text = productVersions[0].toString()
                // textView3.text = item["price"].toString()
                //  textView.text = productVersions[1].item?.get("price").toString()
                //    print("////////////////////////////" + productVersions[0].item?.get("price") + "/////////////////////////////////////")
            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })
/////////////////////////////////////////////////////////////////////


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
     //   val ll = ArrayList<String>()
//        ll.add("1")
//        ll.add("2")
//        ll.add("3")
//        ll.add("4")
//        ll.add("5")
//        ll.add("6")
//        ll.add("7")
//        ll.add("8")
       var temp = RecyclerAdapter(productVersions)
        v.recyclerView.adapter = temp
        temp.notifyDataSetChanged()

        /*ll.add("23981-2")
        val lll = ArrayList<String>()
        //temp = RecyclerAdapter(lll)
        lll.add("123")
        v.recyclerView.adapter = RecyclerAdapter(lll)*/
       // temp.notifyDataSetChanged()
//        temp.notifyDataSetChanged()

        //  v.recyclerView.adapter = RecyclerAdapter(productVersions)

        return v
    }
}