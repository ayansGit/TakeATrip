package com.takeatrip.models.transport

import com.google.gson.annotations.SerializedName

data class AddTransportResponse(
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message : Array<String>,
)
