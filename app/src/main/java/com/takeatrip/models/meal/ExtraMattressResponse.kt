package com.takeatrip.models.meal

import com.google.gson.annotations.SerializedName

data class ExtraMattressResponse(
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message : Array<String>,
    @SerializedName("withExtraMatress") val withExtraMattress: ArrayList<MealData>,
    @SerializedName("withoutExtraMatress") val withoutExtraMattress: ArrayList<MealData>
)
