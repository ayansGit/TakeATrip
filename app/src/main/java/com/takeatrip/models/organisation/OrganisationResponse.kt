package com.takeatrip.models.organisation

import com.google.gson.annotations.SerializedName

data class OrganisationResponse(
    @SerializedName("success") val success : Boolean,
    @SerializedName("message") val message : Array<String>,
    @SerializedName("data") val data : OrganisationData,
)
