package com.example.smartshopper

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smartshopper.model.Product
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.ramotion.foldingcell.FoldingCell

class RecyclerAdapter(private val ll: MutableList<Product>, private
val context: Context, private val storage: FirebaseStorage
) :

    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cts_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return ll.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var storageRef = storage.getReferenceFromUrl(ll[position].item?.get("imageURI").toString())
         storageRef.downloadUrl.addOnSuccessListener {
                   Glide.with(context).load(it).into(holder.image)
               }.addOnFailureListener { }

        storageRef = storage.getReferenceFromUrl(ll[position].storeLogo.toString())
        storageRef.downloadUrl.addOnSuccessListener {
            Glide.with(context).load(it).into(holder.storeLogo)
        }.addOnFailureListener { }

        holder.price.text = ll[position].item?.get("price").toString()
        holder.productName.text = ll[position].item?.get("name").toString()
        holder.storeName.text = ll[position].storeName
        holder.productTitle.text = ll[position].item?.get("name").toString()
        holder.currentPrice.text = ll[position].item?.get("price").toString()
        holder.store.text = ll[position].storeName
        holder.description.text = ll[position].item?.get("description").toString()
        holder.fc.setOnClickListener {
            holder.fc.toggle(false)
        }
        holder.fc.initialize(1000, Color.DKGRAY, 2)
        //Toast.makeText(context, imageURI.toString(), Toast.LENGTH_LONG).show()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val price = itemView.findViewById<TextView>(R.id.price)
        val productName = itemView.findViewById<TextView>(R.id.productName)
        val storeName = itemView.findViewById<TextView>(R.id.storeName)
        val productTitle = itemView.findViewById<TextView>(R.id.productTitle)
        val currentPrice = itemView.findViewById<TextView>(R.id.currentPrice)
        val image = itemView.findViewById<ImageView>(R.id.imageView)
        val store = itemView.findViewById<TextView>(R.id.store)
        val storeLogo = itemView.findViewById<ImageView>(R.id.storeLogo)
        val description = itemView.findViewById<TextView>(R.id.description)
        val fc = itemView.findViewById<FoldingCell>(R.id.folding_cell)
    }


}
