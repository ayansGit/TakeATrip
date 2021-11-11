package com.takeatrip.models.location

import com.google.gson.annotations.SerializedName
import com.takeatrip.models.auth.UserData

data class GetLocationResponse(
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message : Array<String>,
    @SerializedName("data") val data : ArrayList<LocationData>
)
