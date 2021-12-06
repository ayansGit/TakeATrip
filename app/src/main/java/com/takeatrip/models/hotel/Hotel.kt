package com.takeatrip.models.hotel

import com.google.gson.annotations.SerializedName

data class Hotel(
    @SerializedName("id") val id : Int,
    @SerializedName("hotel_id") val hotelId : String,
    @SerializedName("name") val name : String,
    @SerializedName("rating") val rating : Int,
    @SerializedName("address") val address : String,
    @SerializedName("hotel_extra_matress") val hotelExtraMattress: ArrayList<ExtraMattress>,
){
    override fun toString(): String {
        return name
    }
}
