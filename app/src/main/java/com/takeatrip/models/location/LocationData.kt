package com.takeatrip.models.location

import com.google.gson.annotations.SerializedName

data class LocationData(
    @SerializedName("id") val id : Int,
    @SerializedName("location_id") val locationId : String,
    @SerializedName("user_id") val userId : String,
    @SerializedName("name") val name : String,
    @SerializedName("state") val state : String
){
    override fun toString(): String {
        return name
    }
}
