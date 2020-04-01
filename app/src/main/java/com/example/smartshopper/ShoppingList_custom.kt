package com.example.smartshopper

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ShoppingList_custom : Fragment() {

    private var shoppingList: ArrayList<ShoppingListViewModel> = ArrayList()
    val SECOND_PREF_NAME = "SecondLaunchPref"
    lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_shopping_list, container, false)
        val recyclerView = root.findViewById(R.id.recylcerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        val gson = Gson()
        sharedPref = activity?.getSharedPreferences(SECOND_PREF_NAME, Context.MODE_PRIVATE)!!
        val list = sharedPref.getString("Set", null)

        if (list != null) {
            val itemType = object : TypeToken<ArrayList<ShoppingListViewModel>>() {}.type
            shoppingList = gson.fromJson(list, itemType)
            val adapter = CustomAdapter(this, shoppingList, sharedPref)
            recyclerView.adapter = adapter
        }

        val context: Context = this.context ?: return root

        root.findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            val dialog = AlertDialog.Builder(context)
            val view = inflater.inflate(R.layout.dialog_dashboard, container, false)
            //val view = layoutInflater.inflate(R.layout.dialog_dashboard, null)
            val itemName = view.findViewById(R.id.ev_itemName) as EditText
            dialog.setView(view)
            dialog.setPositiveButton("Save") { _: DialogInterface, _: Int ->
                if (itemName.text.isNotEmpty()) {
                    shoppingList.add(ShoppingListViewModel(false, itemName.text.toString()))
                    val gson = Gson()
                    val json = gson.toJson(shoppingList)
                    with(sharedPref.edit()) {
                        putString("Set", json)
                        apply()
                    }
                    val adapter = CustomAdapter(this, shoppingList, sharedPref)
                    recyclerView.adapter = adapter
                }
            }
            dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->
            }
            dialog.show()
        }
        return root
    }
}
