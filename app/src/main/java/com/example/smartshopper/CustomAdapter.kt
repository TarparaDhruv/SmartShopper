package com.example.smartshopper

import android.content.SharedPreferences
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_shopping_list.*


class CustomAdapter(
    val activity: ShoppingList_custom,
    val shoppingList: ArrayList<ShoppingListViewModel>,
    val sharedPref: SharedPreferences,
    val intface: refreshthipage
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // binding view
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return shoppingList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // binding values
        holder.evName.visibility = View.GONE
        holder.ivDone.visibility = View.GONE
        val shopList: ShoppingListViewModel = shoppingList[position]
        holder.tvName?.text = shopList.itemName
        holder.cbIsCompleted?.isChecked = shopList.isCompleted

        holder.btnRemove.setOnClickListener {
            shoppingList.remove(shoppingList[position])
            activity.recylcerView.adapter!!.notifyDataSetChanged()
            val gson = Gson()
            val json = gson.toJson(shoppingList)
            with(sharedPref.edit()) {
                putString("Set", json)
                apply()
            }
            intface.refreshfrag()

        }

        holder.btnedit.setOnClickListener {
            holder.tvName.visibility = View.GONE
            holder.evName.visibility = View.VISIBLE
            holder.ivDone.visibility = View.VISIBLE
            holder.btnRemove.visibility = View.GONE
            holder.btnedit.visibility = View.GONE
        }

        holder.ivDone.setOnClickListener {
            shoppingList.set(position, ShoppingListViewModel(false, holder.evName.text.toString()))
            holder.evName.visibility = View.GONE
            holder.ivDone.visibility = View.GONE
            holder.btnRemove.visibility = View.VISIBLE
            holder.btnedit.visibility = View.VISIBLE
            holder.tvName.visibility = View.VISIBLE
            activity.recylcerView.adapter!!.notifyDataSetChanged()
            val gson = Gson()
            val json = gson.toJson(shoppingList)
            with(sharedPref.edit()) {
                putString("Set", json)
                apply()
            }
        }

        holder.cbIsCompleted.setOnClickListener(object : View.OnClickListener {
            override fun onClick(arg0: View) {
                shoppingList.set(
                    position,
                    ShoppingListViewModel(
                        holder.cbIsCompleted.isChecked,
                        holder.tvName.text.toString()
                    )
                )
                shopList.isCompleted = holder.cbIsCompleted.isChecked
                val gson = Gson()
                val json = gson.toJson(shoppingList)
                with(sharedPref.edit()) {
                    putString("Set", json)
                    apply()
                }
                if (shopList.isCompleted) {
                    holder.tvName.paintFlags =
                        holder.tvName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    shoppingList.get(position)
                    holder.btnRemove.visibility = View.VISIBLE
                    holder.btnedit.visibility = View.VISIBLE
                } else {
                    holder.tvName.paintFlags =
                        holder.tvName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    holder.btnRemove.visibility = View.VISIBLE
                    holder.btnedit.visibility = View.VISIBLE
                }
            }
        })
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName = itemView.findViewById<TextView>(R.id.tvName)
        val cbIsCompleted = itemView.findViewById<CheckBox>(R.id.cbIsCompleted)
        val btnRemove = itemView.findViewById<ImageView>(R.id.iv_delete)
        val btnedit = itemView.findViewById<ImageView>(R.id.iv_edit)
        val evName = itemView.findViewById<EditText>(R.id.evName)
        val ivDone = itemView.findViewById<ImageView>(R.id.iv_done)
    }
}
