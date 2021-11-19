package com.takeatrip.models.transport

import com.google.gson.annotations.SerializedName

data class TransportLocation(
    @SerializedName("id") val id : Int,
    @SerializedName("location_id") val locationId : String,
    @SerializedName("name") val name : String,
    @SerializedName("state") val state : String,
    @SerializedName("transports") val transports : ArrayList<Transport>
){
    override fun toString(): String {
        return name
    }
}
