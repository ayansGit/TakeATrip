package com.takeatrip.models.transport

import com.google.gson.annotations.SerializedName

data class Transport(
    @SerializedName("id") val id : Int,
    @SerializedName("transport_id") val transportId : String,
    @SerializedName("day") val day : Int,
    @SerializedName("description") val description : String,
    @SerializedName("transport_price") val transportPrice : String,
    @SerializedName("ticket_price") val ticketPrice : String
)
