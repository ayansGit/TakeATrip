package com.takeatrip.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.takeatrip.R

class HotelAdapter(context: Context): RecyclerView.Adapter<HotelAdapter.ViewHolder>() {

    val context = context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_hotel, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(position == 14){
            holder.margin.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        }
    }

    override fun getItemCount(): Int {
        return 15
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val margin = itemView.findViewById<View>(R.id.margin)
    }
}