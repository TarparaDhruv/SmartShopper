package com.example.smartshopper.ui.shoppingList

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartshopper.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_shopping_list.*


class CustomAdapter(val activity: ShoppingListFragment, val shoppingList: ArrayList<ShoppingListViewModel>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // binding view
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return shoppingList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // binding values
        holder.evName.setVisibility(View.GONE)
        holder.ivDone.setVisibility(View.GONE)
        val shopList: ShoppingListViewModel = shoppingList[position]
        holder?.tvName?.text = shopList.itemName
        holder?.cbIsCompleted?.isChecked = shopList.isCompleted

        holder.btnRemove.setOnClickListener{
            shoppingList.remove(shoppingList[position])
            activity.recylcerView.adapter!!.notifyDataSetChanged()
            val gson = Gson()
            val json = gson.toJson(shoppingList)
            val sharedPreferences = activity.activity
            val sharedPref = sharedPreferences!!.getPreferences(Context.MODE_PRIVATE)
            with (sharedPref!!.edit()) {
                putString("Set", json)
                commit()
            }
        }

        holder.btnedit.setOnClickListener {
            holder.tvName.setVisibility(View.GONE)
            holder.evName.setVisibility(View.VISIBLE)
            holder.ivDone.setVisibility(View.VISIBLE)
            holder.btnRemove.setVisibility(View.GONE)
            holder.btnedit.setVisibility(View.GONE)
        }

        holder.ivDone.setOnClickListener{
            shoppingList.set( position, ShoppingListViewModel(false, holder.evName.text.toString()))
            holder.evName.setVisibility(View.GONE)
            holder.ivDone.setVisibility(View.GONE)
            holder.btnRemove.setVisibility(View.VISIBLE)
            holder.btnedit.setVisibility(View.VISIBLE)
            holder.tvName.setVisibility(View.VISIBLE)
            activity.recylcerView.adapter!!.notifyDataSetChanged()
            val gson = Gson()
            val json = gson.toJson(shoppingList)
            val sharedPreferences = activity.activity
            val sharedPref = sharedPreferences!!.getPreferences(Context.MODE_PRIVATE)
            with (sharedPref.edit()) {
                putString("Set", json)
                commit()
            }
        }

        holder.cbIsCompleted.setOnClickListener(object : View.OnClickListener {
            override fun  onClick(arg0: View) {
                shoppingList.set( position, ShoppingListViewModel(holder.cbIsCompleted.isChecked, holder.tvName.text.toString()))
                shopList.isCompleted = holder.cbIsCompleted.isChecked
                val gson = Gson()
                val json = gson.toJson(shoppingList)
                val sharedPreferences = activity.activity
                val sharedPref = sharedPreferences!!.getPreferences(Context.MODE_PRIVATE)
                //val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                //val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                with (sharedPref.edit()) {
                    putString("Set", json)
                    commit()
                }
                if (shopList.isCompleted) {
                    holder.tvName.setPaintFlags(holder.tvName.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
                    shoppingList.get(position)
                    holder.btnRemove.setVisibility(View.GONE)
                    holder.btnedit.setVisibility(View.GONE)
                } else {
                    holder.tvName.setPaintFlags(holder.tvName.getPaintFlags() and Paint.STRIKE_THRU_TEXT_FLAG.inv())
                    holder.btnRemove.setVisibility(View.VISIBLE)
                    holder.btnedit.setVisibility(View.VISIBLE)
                }
            }
        })
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvName = itemView.findViewById<TextView>(R.id.tvName)
        val cbIsCompleted = itemView.findViewById<CheckBox>(R.id.cbIsCompleted)
        val btnRemove = itemView.findViewById<ImageView>(R.id.iv_delete)
        val btnedit = itemView.findViewById<ImageView>(R.id.iv_edit)
        val evName = itemView.findViewById<EditText>(R.id.evName)
        val ivDone = itemView.findViewById<ImageView>(R.id.iv_done)
    }
}