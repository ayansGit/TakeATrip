package com.takeatrip.models.auth

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("id") val id : Int,
    @SerializedName("user_id") val userId : String,
    @SerializedName("full_name") val fullName : String,
    @SerializedName("email") val email : String,
    @SerializedName("phone") val phone : String,
    @SerializedName("type") val type : String,
    @SerializedName("is_organisation_added") val isOrganisationAdded : String
)
