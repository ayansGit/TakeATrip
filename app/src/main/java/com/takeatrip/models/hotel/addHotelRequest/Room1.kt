package com.takeatrip.models.hotel.addHotelRequest

import com.google.gson.annotations.SerializedName

data class Room1(
    @SerializedName("room_type_id") val roomTypeId : String,
    @SerializedName("general_meal") val meal: ArrayList<Meal>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Room1

        if (roomTypeId != other.roomTypeId) return false
        if (meal != other.meal) return false

        return true
    }

    override fun hashCode(): Int {
        var result = roomTypeId.hashCode()
        result = 31 * result + meal.hashCode()
        return result
    }
}
