package com.takeatrip.models.transport

import com.google.gson.annotations.SerializedName

data class GetTransportResponse(
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message : Array<String>,
    @SerializedName("data") val data : ArrayList<TransportLocation>,
)
