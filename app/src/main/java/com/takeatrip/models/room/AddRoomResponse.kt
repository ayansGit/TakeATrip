package com.takeatrip.models.room

import com.google.gson.annotations.SerializedName

data class AddRoomResponse(
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message : Array<String>,
)
