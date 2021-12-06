package com.takeatrip.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.takeatrip.R
import com.takeatrip.models.meal.MealData
import com.takeatrip.models.room.RoomData
import com.takeatrip.utils.hide
import com.takeatrip.utils.show

class AddHotelRoomAdapter(context: Context, roomList: ArrayList<RoomData>, mealList: ArrayList<MealData>, selectedRoomListener: SelectedRoomListener): RecyclerView.Adapter<AddHotelRoomAdapter.ViewHolder>() {

    private val context = context
    private val mealList = mealList
    private val roomList = roomList
    private val selectedRoomListener = selectedRoomListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.item_add_room_adapter, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.rvMeals.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        holder.rvMeals.adapter = AddedMealAdapter(context, roomList[position].mealList){
            selectedRoomListener.onMealSelected(position, it)
            selectedRoomListener.onMealSelected("$position~${roomList[position].roomTypeId}", it)
        }

        holder.rvWithMattress.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        holder.rvWithMattress.adapter = AddedMealAdapter(context, roomList[position].withExtraMattress){
            selectedRoomListener.onWithExtraMattressSelected("$position~${roomList[position].roomTypeId}", it)
        }

        holder.rvWithoutMattress.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        holder.rvWithoutMattress.adapter = AddedMealAdapter(context, roomList[position].withoutExtraMattress){
            selectedRoomListener.onWithoutExtraMattressSelected("$position~${roomList[position].roomTypeId}", it)
        }

        holder.tvSelectedRoom.text = roomList[position].name

        holder.cb1.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                holder.rvWithMattress.show()
            }else holder.rvWithMattress.hide()
        }

        holder.cb2.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                holder.rvWithoutMattress.show()
            }else holder.rvWithoutMattress.hide()
        }

        if(roomList[position].withExtraMattress.size>0){
            holder.cb1.show()
        }else holder.cb1.hide()

        if(roomList[position].withoutExtraMattress.size>0){
            holder.cb2.show()
        }else holder.cb2.hide()
    }

    override fun getItemCount(): Int {
        return roomList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val rvMeals = itemView.findViewById<RecyclerView>(R.id.rvMeals)
        val tvSelectedRoom = itemView.findViewById<TextView>(R.id.tvSelectedRoom)
        val cb1 = itemView.findViewById<CheckBox>(R.id.cb1)
        val cb2 = itemView.findViewById<CheckBox>(R.id.cb2)
        val rvWithMattress = itemView.findViewById<RecyclerView>(R.id.rvWithMattress)
        val rvWithoutMattress = itemView.findViewById<RecyclerView>(R.id.rvWithoutMattress)
    }

    interface SelectedRoomListener {
        fun onMealSelected(roomCount: Int, mealData: MealData)
        fun onMealSelected(roomId: String, mealData: MealData)
        fun onWithExtraMattressSelected(roomId: String, mealData: MealData)
        fun onWithoutExtraMattressSelected(roomId: String, mealData: MealData)
    }
}