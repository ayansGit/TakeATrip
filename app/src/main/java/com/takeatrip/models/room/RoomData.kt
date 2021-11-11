package com.takeatrip.models.room

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RoomData(
    @SerializedName("id") val id : Int,
    @SerializedName("room_type_id") val roomTypeId : String,
    @SerializedName("name") val name : String,
    @SerializedName("allowed_extra_bed") val allowExtraBed : Int,
    var selected: Boolean
): Serializable
