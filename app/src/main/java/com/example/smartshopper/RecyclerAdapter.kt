package com.example.smartshopper

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartshopper.model.Product
import com.ramotion.foldingcell.FoldingCell

class RecyclerAdapter(private val ll: MutableList<Product>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cts_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return ll.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.tv.text = ll[position]
//        holder.tvcontent.text = ll[position]
        holder.price.text = ll[position].item?.get("price").toString()
        holder.productName.text = ll[position].item?.get("name").toString()
        holder.storeName.text = ll[position].storeName
        holder.productTitle.text = ll[position].item?.get("name").toString()
        holder.currentPrice.text = ll[position].item?.get("price").toString()
        holder.fc.setOnClickListener {
            holder.fc.toggle(false)
        }
        holder.fc.initialize(1000, Color.DKGRAY, 2)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val price = itemView.findViewById<TextView>(R.id.price)
        val productName = itemView.findViewById<TextView>(R.id.productName)
        val storeName = itemView.findViewById<TextView>(R.id.storeName)
        val productTitle = itemView.findViewById<TextView>(R.id.productTitle)
        val currentPrice = itemView.findViewById<TextView>(R.id.currentPrice)
        val fc = itemView.findViewById<FoldingCell>(R.id.folding_cell)
    }

}
