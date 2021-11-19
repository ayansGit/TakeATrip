package com.takeatrip.adapters

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.takeatrip.R
import com.takeatrip.models.meal.MealData
import com.takeatrip.models.transport.Transport
import com.takeatrip.utils.hide
import com.takeatrip.utils.show

class TransportAdapter(context: Context, transportList: ArrayList<Transport>): RecyclerView.Adapter<TransportAdapter.ViewHolder>() {

    val context = context
    val transportList = transportList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.item_transport, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvDay.text = "Day ${transportList[position].day}"
        holder.tvTravelPlan.text = transportList[position].description
        holder.tvTravelCost.text = transportList[position].transportPrice
        holder.tvMiscellaneous.text = transportList[position].ticketPrice
    }

    override fun getItemCount(): Int {
        return transportList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvDay = itemView.findViewById<TextView>(R.id.tvDay)
        val tvTravelPlan = itemView.findViewById<TextView>(R.id.tvTravelPlan)
        val tvTravelCost = itemView.findViewById<TextView>(R.id.tvTravelCost)
        val tvMiscellaneous = itemView.findViewById<TextView>(R.id.tvMiscellaneous)
    }



}