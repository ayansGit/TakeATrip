package com.takeatrip.models.hotel

import com.google.gson.annotations.SerializedName

data class Hotel(
    @SerializedName("id") val id : Int,
    @SerializedName("hotel_id") val hotelId : String,
    @SerializedName("name") val name : String,
    @SerializedName("rating") val rating : Int,
)
