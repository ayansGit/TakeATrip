package com.takeatrip.models.meal

import com.google.gson.annotations.SerializedName

data class GetMealResponse(
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message : Array<String>,
    @SerializedName("data") val data: ArrayList<MealData>
)
