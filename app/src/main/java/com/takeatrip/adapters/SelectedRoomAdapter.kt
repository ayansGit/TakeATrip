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

class SelectedRoomAdapter(context: Context, roomList: ArrayList<RoomData>, mealList: ArrayList<MealData>, selectedRoomListener: SelectedRoomListener): RecyclerView.Adapter<SelectedRoomAdapter.ViewHolder>() {

    private val context = context
    private val roomList = roomList
    private val mealList = mealList
    private val selectedRoomListener = selectedRoomListener
    private var generalMealList = HashMap<String, HashMap<String, MealData>>()
    private var withMattressList = HashMap<String, HashMap<String, MealData>>()
    private var withoutMattressList = HashMap<String, HashMap<String, MealData>>()


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
        holder.mealAdapter = MealAdapter(context, mealList){
            generalMealList[roomList[position].roomTypeId] = it
            selectedRoomListener.onGeneralMealUpdated(generalMealList)
        }
        holder.rcMealType1.adapter = holder.mealAdapter

        holder.rcMealType2.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        holder.with_mattress_meal_adapter = MealAdapter(context, mealList){
            withMattressList[roomList[position].roomTypeId] = it
            selectedRoomListener.onMealWithMattressUpdated(withMattressList)
        }
        holder.rcMealType2.adapter = holder.with_mattress_meal_adapter

        holder.rcMealType3.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        holder.without_mattress_meal_adapter = MealAdapter(context, mealList){
            withoutMattressList[roomList[position].roomTypeId] = it
            selectedRoomListener.onMealWithoutMattressUpdated(withoutMattressList)
        }
        holder.rcMealType3.adapter = holder.without_mattress_meal_adapter

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

        lateinit var mealAdapter: MealAdapter
        lateinit var with_mattress_meal_adapter: MealAdapter
        lateinit var without_mattress_meal_adapter: MealAdapter
    }

    interface SelectedRoomListener{
        fun onGeneralMealUpdated(mealMap: HashMap<String, HashMap<String, MealData>>)
        fun onMealWithMattressUpdated(mealMap:HashMap<String, HashMap<String, MealData>>)
        fun onMealWithoutMattressUpdated(mealMap: HashMap<String, HashMap<String, MealData>>)
    }
}