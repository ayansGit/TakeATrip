package com.takeatrip.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.takeatrip.R
import com.takeatrip.utils.hide
import com.takeatrip.utils.show

class MealAdapter(context: Context): RecyclerView.Adapter<MealAdapter.ViewHolder>() {

    val context = context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.item_meal, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.cb.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                holder.price.show()
            }else holder.price.hide()
        }

    }

    override fun getItemCount(): Int {
        return 3
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val etPrice = itemView.findViewById<TextView>(R.id.etPrice)
        val price = itemView.findViewById<TextInputLayout>(R.id.price)
        val cb = itemView.findViewById<CheckBox>(R.id.cb)
    }
}