package com.takeatrip.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.takeatrip.R
import com.takeatrip.models.room.RoomData
import com.takeatrip.utils.hide
import com.takeatrip.utils.show

class HotelRoomAdapter(context: Context, isSelectable: Boolean, roomList: ArrayList<RoomData>, onRoomSelected:(rooms: HashMap<String, RoomData>) -> Unit): RecyclerView.Adapter<HotelRoomAdapter.ViewHolder>() {

    val context = context
    val isSelectable = isSelectable
    val roomList = roomList
    val selectedRooms = HashMap<String, RoomData>()
    val onRoomSelected = onRoomSelected


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.item_room, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(isSelectable)
            holder.cb.show()
        else holder.cb.hide()

        holder.tvRoomType.text = roomList[position].name.trim()
        holder.tvCapacity.text = "Extra beds: ${roomList[position].allowExtraBed.toString()}"

        holder.cb.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                selectedRooms[roomList[position].roomTypeId] = roomList[position]
                roomList[position].selected = true
                onRoomSelected(selectedRooms)
            }else{
                selectedRooms.remove(roomList[position].roomTypeId)
                roomList[position].selected = false
                onRoomSelected(selectedRooms)
            }
        }

        holder.cb.isSelected = roomList[position].selected
    }

    override fun getItemCount(): Int {
        return roomList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvRoomType = itemView.findViewById<TextView>(R.id.tvRoomType)
        val tvCapacity = itemView.findViewById<TextView>(R.id.tvCapacity)
        val cb = itemView.findViewById<CheckBox>(R.id.cb)
    }

}