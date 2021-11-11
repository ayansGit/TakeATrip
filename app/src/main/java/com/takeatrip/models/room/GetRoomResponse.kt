package com.takeatrip.models.room

import com.google.gson.annotations.SerializedName
import com.takeatrip.models.location.LocationData

data class GetRoomResponse(
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message : Array<String>,
    @SerializedName("data") val data : ArrayList<RoomData>
)
