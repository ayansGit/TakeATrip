package com.takeatrip.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.takeatrip.R
import com.takeatrip.models.hotel.Hotel

class HotelAdapter(context: Context, hotelList: ArrayList<Hotel>): RecyclerView.Adapter<HotelAdapter.ViewHolder>() {

    val context = context
    val hotelList = hotelList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_hotel, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvLocation.text = hotelList[position].name
        holder.rating.rating = hotelList[position].rating.toFloat()

        if(position == hotelList.size-1){
            holder.margin.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        }
    }

    override fun getItemCount(): Int {
        return hotelList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val margin = itemView.findViewById<View>(R.id.margin)
        val tvLocation = itemView.findViewById<TextView>(R.id.tvLocation)
        val rating = itemView.findViewById<RatingBar>(R.id.rating)
    }
}