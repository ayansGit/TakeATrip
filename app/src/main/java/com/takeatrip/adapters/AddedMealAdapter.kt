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
import com.takeatrip.utils.hide
import com.takeatrip.utils.show

class AddedMealAdapter(context: Context, mealList: ArrayList<MealData>, onMealSelected:(mealMap: HashMap<String, MealData>) -> Unit): RecyclerView.Adapter<AddedMealAdapter.ViewHolder>() {

    val context = context
    val mealList = mealList
    val onMealSelected = onMealSelected
    val mealMap = HashMap<String, MealData>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.item_added_meal, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val pos = position

        holder.tvMeal.text = mealList[position].sortName

        holder.tvMeal.setOnClickListener {
            holder.tvMeal.isSelected = !holder.tvMeal.isSelected

        }


    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvMeal = itemView.findViewById<TextView>(R.id.tvMeal)
    }



}