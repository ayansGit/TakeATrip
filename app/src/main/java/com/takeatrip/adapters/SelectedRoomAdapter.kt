package com.takeatrip.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.takeatrip.R
import com.takeatrip.models.room.RoomData
import com.takeatrip.utils.hide
import com.takeatrip.utils.show
import kotlinx.android.synthetic.main.activity_add_hotel.*

class SelectedRoomAdapter(context: Context, roomList: ArrayList<RoomData>): RecyclerView.Adapter<SelectedRoomAdapter.ViewHolder>() {

    val context = context
    val roomList = roomList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.item_room_types, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvRoom.text = roomList[position].name

        holder.cb1.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                holder.rcMealType2.show()
            }else holder.rcMealType2.hide()
        }

        holder.cb2.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                holder.rcMealType3.show()
            }else holder.rcMealType3.hide()
        }

        holder.rcMealType1.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        holder.rcMealType1.adapter = MealAdapter(context)

        holder.rcMealType2.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        holder.rcMealType2.adapter = MealAdapter(context)

        holder.rcMealType3.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        holder.rcMealType3.adapter = MealAdapter(context)

    }

    override fun getItemCount(): Int {
        return roomList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvRoom = itemView.findViewById<TextView>(R.id.tvRoom)
        val rcMealType1 = itemView.findViewById<RecyclerView>(R.id.rcMealType1)
        val rcMealType2 = itemView.findViewById<RecyclerView>(R.id.rcMealType2)
        val rcMealType3 = itemView.findViewById<RecyclerView>(R.id.rcMealType3)
        val cb1 = itemView.findViewById<CheckBox>(R.id.cb1)
        val cb2 = itemView.findViewById<CheckBox>(R.id.cb2)
    }
}