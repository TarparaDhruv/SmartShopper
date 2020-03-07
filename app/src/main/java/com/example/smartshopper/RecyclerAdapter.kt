package com.example.smartshopper

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ramotion.foldingcell.FoldingCell

class RecyclerAdapter(private val ll: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cts_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return ll.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv.text = ll[position]
        holder.tvcontent.text = ll[position]
        holder.fc.setOnClickListener {
            holder.fc.toggle(false)
        }
        holder.fc.initialize(1000, Color.DKGRAY, 2)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv = itemView.findViewById<TextView>(R.id.ctstv)
        val tvcontent = itemView.findViewById<TextView>(R.id.textcontent)
        val fc = itemView.findViewById<FoldingCell>(R.id.folding_cell)
    }

}
