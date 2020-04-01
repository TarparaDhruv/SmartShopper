package com.example.smartshopper.ui.shoppingList

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartshopper.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_shopping_list.*


class ShoppingListFragment : Fragment() {

    private var shoppingList1: ArrayList<ShoppingListViewModel> = ArrayList()
    val SECOND_PREF_NAME = "SecondLaunchPref"
    //val preference_file_key: String =  "MY_PREFERENCES"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_shopping_list, container, false)
        val recyclerView = root.findViewById(R.id.recylcerView) as RecyclerView
        recyclerView!!.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        val gson = Gson()
        var sharedPref = activity?.getSharedPreferences(SECOND_PREF_NAME, Context.MODE_PRIVATE)
        var list = sharedPref?.getString("Set", null)
         val itemType = object : TypeToken<ArrayList<ShoppingListViewModel>>() {}.type
        shoppingList1 = gson.fromJson(list, itemType)
        val adapter = CustomAdapter(this, shoppingList1)
        recyclerView.adapter = adapter
        val context: Context = this.context ?: return root

        fabAdd.setOnClickListener {
            val dialog = AlertDialog.Builder(context)
            val view = layoutInflater.inflate(R.layout.dialog_shoppinglist, null)
            val itemName = view.findViewById(R.id.ev_itemName) as EditText
            dialog.setView(view)
            dialog.setPositiveButton("Save") {_: DialogInterface, _:Int->
                if(itemName.text.isNotEmpty()){
                    shoppingList1.add(ShoppingListViewModel(false,itemName?.text.toString()))
                    val gson = Gson()
                    val json = gson.toJson(shoppingList1)
                    //val sharedPref = this?.getPreferences(Context.MODE_PRIVATE)
                    val sharedPref = this.activity?.getPreferences(Context.MODE_PRIVATE)
                    //sharedPref?.getSharedPreferences("pref", Context.MODE_PRIVATE)

                    with (sharedPref!!.edit()) {
                        putString("Set", json)
                        commit()
                    }
                    val adapter = CustomAdapter(this, shoppingList1)
                    recyclerView.adapter = adapter
                }
            }
            dialog.setNegativeButton("Cancel") {_: DialogInterface, _:Int->
            }
            dialog.show()

        }
        return root
    }
}


