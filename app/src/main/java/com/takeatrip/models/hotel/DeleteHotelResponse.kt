package com.takeatrip.models.hotel

import com.google.gson.annotations.SerializedName

data class DeleteHotelResponse(
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message : Array<String>,
)
