package com.takeatrip.models.hotel.addHotelRequest

import com.google.gson.annotations.SerializedName

data class Meal(
    @SerializedName("meal_type_id") val mealTypeId : String,
    @SerializedName("price") val price : String,
)