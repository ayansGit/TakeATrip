package com.takeatrip.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.takeatrip.R
import com.takeatrip.models.hotel.Hotel
import com.takeatrip.utils.hide
import com.takeatrip.utils.show

class HotelAdapter(context: Context, hotelList: ArrayList<Hotel>, hotelAdapterListener: HotelAdapterListener): RecyclerView.Adapter<HotelAdapter.ViewHolder>() {

    val context = context
    val hotelList = hotelList
    val hotelAdapterListener = hotelAdapterListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_hotel, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvLocation.text = hotelList[position].name
        holder.rating.rating = hotelList[position].rating.toFloat()

        if(hotelList[position].hotelExtraMattress.size>0){
            holder.btExtraMattress.hide()
        }else {
            holder.btExtraMattress.show()
        }

        holder.ivDelete.setOnClickListener {
            hotelAdapterListener.deleteHotel(hotelList[position].hotelId)
        }

        holder.btExtraMattress.setOnClickListener {
            hotelAdapterListener.addExtraMattress(hotelList[position].hotelId)
        }

        /*if(position == hotelList.size-1){
            holder.margin.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        }*/
    }

    override fun getItemCount(): Int {
        return hotelList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val margin = itemView.findViewById<View>(R.id.margin)
        val tvLocation = itemView.findViewById<TextView>(R.id.tvLocation)
        val rating = itemView.findViewById<RatingBar>(R.id.rating)
        val btExtraMattress = itemView.findViewById<Button>(R.id.btExtraMattress)
        val ivDelete = itemView.findViewById<ImageView>(R.id.tvDelete)
    }

    interface HotelAdapterListener{
        fun deleteHotel(hotelId: String)
        fun addExtraMattress(hotelId: String)
    }
}