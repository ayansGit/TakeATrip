package com.takeatrip.models.hotel.addHotelRequest

import com.google.gson.annotations.SerializedName

data class Room4(
    @SerializedName("room_type_id") val roomTypeId: String,
    @SerializedName("general_meal") val meal: ArrayList<Meal>,
    @SerializedName("with_matress_meal") val mealWithMatress: ArrayList<Meal>,
    @SerializedName("without_matress_meal") val mealWithoutMatress: ArrayList<Meal>
)

