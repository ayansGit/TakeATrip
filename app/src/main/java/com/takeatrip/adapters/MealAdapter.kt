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

class MealAdapter(context: Context, mealList: ArrayList<MealData>, onMealSelected:(mealMap: HashMap<String, MealData>) -> Unit): RecyclerView.Adapter<MealAdapter.ViewHolder>() {

    val context = context
    val mealList = mealList
    val onMealSelected = onMealSelected
    val mealMap = HashMap<String, MealData>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.item_meal, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val pos = position

        holder.cb.text = mealList[position].sortName
        holder.cb.isChecked = mealList[position].isSelected

        holder.cb.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                holder.price.show()
                mealList[position].isSelected = true
                mealMap[mealList[position].mealTypeId] = mealList[position]
                onMealSelected(mealMap)
            }else {
                holder.price.hide()
                mealList[position].isSelected = false
                mealList[pos].price = "0"
                mealMap.remove(mealList[position].mealTypeId)
                onMealSelected(mealMap)
            }
        }

        holder.etPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mealList[pos].price = p0.toString()
                mealMap[mealList[position].mealTypeId] = mealList[position]
                onMealSelected(mealMap)
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val etPrice = itemView.findViewById<EditText>(R.id.etPrice)
        val price = itemView.findViewById<TextInputLayout>(R.id.price)
        val cb = itemView.findViewById<CheckBox>(R.id.cb)
    }

    fun getMeals(): ArrayList<MealData>{
        return mealList.filter { it.isSelected } as ArrayList<MealData>
    }


}