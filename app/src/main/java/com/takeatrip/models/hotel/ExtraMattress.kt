package com.takeatrip.models.hotel

import com.google.gson.annotations.SerializedName

data class ExtraMattress(
    @SerializedName("id") val id : Int,
    @SerializedName("hotel_id") val hotelId : String,
    @SerializedName("price") val price : String,
    @SerializedName("extra_mattress") val extraMattress : String,
    @SerializedName("meal_type_id") val mealTypeId : String,
)
