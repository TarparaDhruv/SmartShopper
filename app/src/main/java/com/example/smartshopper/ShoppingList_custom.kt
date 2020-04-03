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
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_shopping_list.view.*

interface refreshthipage {
    fun refreshfrag()
}

class ShoppingList_custom : Fragment(), refreshthipage {
    override fun refreshfrag() {
        fragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit()
    }

    private var shoppingList: ArrayList<ShoppingListViewModel> = ArrayList()
    val SECOND_PREF_NAME = "SecondLaunchPref"
    lateinit var sharedPref: SharedPreferences
    lateinit var adapter: CustomAdapter

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
        val context: Context = this.context ?: return root

        if (list != null) {
            val itemType = object : TypeToken<ArrayList<ShoppingListViewModel>>() {}.type
            shoppingList = gson.fromJson(list, itemType)
            if (shoppingList.size > 0) {
                adapter = CustomAdapter(this, shoppingList, sharedPref, intface = this)
                recyclerView.adapter = adapter
                root.empty_shopplin_list_gif.visibility = View.GONE
            } else {
                //show empty list gif if size is zero
                root.empty_shopplin_list_gif.visibility = View.VISIBLE
                Glide.with(requireContext()).load(R.drawable.store_2)
                    .into(root.empty_shopplin_list_gif)
                root.button_delete.visibility = View.GONE
            }
        } else {
            //show empty list gif
            root.empty_shopplin_list_gif.visibility = View.VISIBLE
            Glide.with(requireContext()).load(R.drawable.store_2).into(root.empty_shopplin_list_gif)
            root.button_delete.visibility = View.GONE
        }

        root.fabAdd.setOnClickListener {
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
                    adapter = CustomAdapter(this, shoppingList, sharedPref, intface = this)
                    recyclerView.adapter = adapter
                    //hide gif when item added to list
                    root.empty_shopplin_list_gif.visibility = View.GONE
                    root.button_delete.visibility = View.VISIBLE
                }
            }
            dialog.setNegativeButton("Cancel") { _: DialogInterface, _: Int ->
            }
            dialog.show()
        }
        //clear sharedpref and refresh fragment
        root.button_delete.setOnClickListener {
            sharedPref = activity?.getSharedPreferences(SECOND_PREF_NAME, Context.MODE_PRIVATE)!!
            val gson = Gson()
            shoppingList.removeAll(shoppingList)
            val json = gson.toJson(shoppingList)
            with(sharedPref.edit()) {
                putString("Set", json)
                apply()
            }

            recyclerView.adapter = null
            fragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit()
        }

        return root
    }
}

