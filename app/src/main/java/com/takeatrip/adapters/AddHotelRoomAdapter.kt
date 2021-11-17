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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.item_add_room_adapter, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.rvMeals.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        holder.rvMeals.adapter = AddedMealAdapter(context, mealList){

        }
    }

    override fun getItemCount(): Int {
        return 1
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val rvMeals = itemView.findViewById<RecyclerView>(R.id.rvMeals)
    }

    interface SelectedRoomListener {
        fun onMealSelected(mealMap: HashMap<String, MealData>)
    }
}