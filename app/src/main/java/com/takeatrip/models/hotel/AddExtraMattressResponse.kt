package com.takeatrip.models.hotel

import com.google.gson.annotations.SerializedName

data class AddExtraMattressResponse(
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message : Array<String>,
)
