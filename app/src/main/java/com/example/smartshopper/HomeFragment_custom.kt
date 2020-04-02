package com.example.smartshopper

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smartshopper.model.Product
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.cell_title.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.lang.Exception

interface xx{
    fun getdata()
}
class HomeFragment_custom : Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var v: View
    private val TAG = "HomeFragment"
    var productVersions: MutableList<Product> = ArrayList()
    lateinit var mAuth :FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mAuth = FirebaseAuth.getInstance()
        v = inflater.inflate(R.layout.fragment_home, container, false)
        linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        v.recyclerView.layoutManager = linearLayoutManager
        var upc = "1234" //Barcode
////////////////////////////////////

        /* var firebaseDatabase = FirebaseDatabase.getInstance()
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
                     var temp = RecyclerAdapter(productVersions)
                     v.recyclerView.adapter = temp
                     temp.notifyDataSetChanged()
                 }
                 // textView3.text = productVersions[0].toString()
                 // textView3.text = item["price"].toString()
                 //  textView.text = productVersions[1].item?.get("price").toString()
                 //    print("////////////////////////////" + productVersions[0].item?.get("price") + "/////////////////////////////////////")
             }

             override fun onCancelled(p0: DatabaseError) {

             }

         })*/
/////////////////////////////////////////////////////////////////////

//************************************************************
      // val mAuth = FirebaseAuth.getInstance()


        /*mAuth.signInAnonymously().addOnCompleteListener(requireActivity(), object: OnCompleteListener<AuthResult>{

            override fun onComplete(p0: Task<AuthResult>) {
                var message: String
                if (p0.isSuccessful()) {
                    message = "success signInWithEmailAndPassword";
                } else {
                    message = "fail signInWithEmailAndPassword";
                }
                productName.setText(message);
            }
        }).addOnFailureListener(object: OnFailureListener {

            override fun onFailure(p0: Exception) {
                productName.setText(p0.toString());
                p0.printStackTrace();
            }

        })*/

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

//************************************************************


        //    linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        //   v.recyclerView.layoutManager = linearLayoutManager
        //   val ll = ArrayList<String>()
//        ll.add("1")
//        ll.add("2")
//        ll.add("3")
//        ll.add("4")
//        ll.add("5")
//        ll.add("6")
//        ll.add("7")
//        ll.add("8")
        /* var temp = RecyclerAdapter(productVersions)
          v.recyclerView.adapter = temp
          temp.notifyDataSetChanged()*/

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

    fun fetchData(upc: String) {
        var rootRef = FirebaseDatabase.getInstance().reference
        var storageRef = FirebaseStorage.getInstance()
            .getReferenceFromUrl("gs://smartshopper-e4b3c.appspot.com/products/id_$upc.png")
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
                productVersions.sortBy { it -> it.item?.get("price") as Double }
//                textView3.text = productVersions[0].item?.get("price").toString()
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.w("HomeFragment", "loadLog:onCancelled", p0.toException())
            }

        })

        storageRef.downloadUrl.addOnSuccessListener {
            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
            //Glide.with(requireContext()).load(it).into(v.imageView3)
        }.addOnFailureListener { }
    }


}

/*storageRef.downloadUrl
.addOnSuccessListener(
this@MainActivity
) { uri -> Glide.with(this@MainActivity).load(uri).into(imageView) }
.addOnFailureListener { }*/