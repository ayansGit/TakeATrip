package com.takeatrip.models.auth

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message : Array<String>,
    @SerializedName("token") val token : String,
    @SerializedName("data") val data : UserData
)
