package com.takeatrip.models.hotel.addHotelRequest

import com.google.gson.annotations.SerializedName

data class HotelRequestData2(
    @SerializedName("name") val name : String,
    @SerializedName("location_id") val locationId : String,
    @SerializedName("rating") val rating : Int,
    @SerializedName("address") val address : String,
    @SerializedName("rooms") val room : ArrayList<Room2>,

)
