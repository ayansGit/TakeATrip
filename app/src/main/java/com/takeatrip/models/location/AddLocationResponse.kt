package com.takeatrip.models.location

import com.google.gson.annotations.SerializedName

data class AddLocationResponse(
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message : Array<String>,
    @SerializedName("data") val data : LocationData
)