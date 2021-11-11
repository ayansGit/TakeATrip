package com.takeatrip.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.takeatrip.R
import com.takeatrip.models.location.LocationData

class LocationAdapter(context: Context, locationList: ArrayList<LocationData>): RecyclerView.Adapter<LocationAdapter.ViewHolder>() {

    val context = context
    val locationList = locationList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_location, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvLocation.text = locationList[position].name
        holder.tvCountry.text = locationList[position].state

        if(position == locationList.size-1){
            holder.margin.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        }else holder.margin.setBackgroundColor(ContextCompat.getColor(context, R.color.grey3))
    }

    override fun getItemCount(): Int {
        return locationList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val margin = itemView.findViewById<View>(R.id.margin)
        val tvLocation = itemView.findViewById<TextView>(R.id.tvLocation)
        val tvCountry = itemView.findViewById<TextView>(R.id.tvCountry)
    }
}