package com.takeatrip.models.hotel

import com.google.gson.annotations.SerializedName

data class GetHotelListResponse(
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message : Array<String>,
    @SerializedName("data") val data : ArrayList<Hotel>
)