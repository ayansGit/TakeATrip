package com.takeatrip.models.hotel.addHotelRequest

import com.google.gson.annotations.SerializedName

data class ExtraMattressRequestData(
    @SerializedName("hotel_id") val hotelId : String,
    @SerializedName("room_type_id") val roomTypeId: String,
    @SerializedName("with_matress_meal") val mealWithMatress: ArrayList<Meal>,
    @SerializedName("without_matress_meal") val mealWithoutMatress: ArrayList<Meal>
)
