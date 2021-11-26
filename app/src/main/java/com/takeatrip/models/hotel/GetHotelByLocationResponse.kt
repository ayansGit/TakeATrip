package com.takeatrip.models.hotel

import com.google.gson.annotations.SerializedName

data class GetHotelByLocationResponse(
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message : Array<String>,
    @SerializedName("hotels") val data : ArrayList<Hotel>
)