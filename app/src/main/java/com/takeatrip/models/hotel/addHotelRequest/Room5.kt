package com.takeatrip.models.hotel.addHotelRequest

import com.google.gson.annotations.SerializedName

data class Room5(
    @SerializedName("with_matress_meal") val mealWithMatress: ArrayList<Meal>,
    @SerializedName("without_matress_meal") val mealWithoutMatress: ArrayList<Meal>
)

