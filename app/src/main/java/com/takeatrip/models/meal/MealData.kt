package com.takeatrip.models.meal

import com.google.gson.annotations.SerializedName

data class MealData(
    @SerializedName("meal_type_id") val mealTypeId : String,
    @SerializedName("id") val id : Int,
    @SerializedName("sort_name") val sortName : String,
    @SerializedName("full_name") val fullName : String,
    var isSelected: Boolean = false,
    var price: String = "0",
)
