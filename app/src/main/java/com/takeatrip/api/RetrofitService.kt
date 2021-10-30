package com.graphicalab.api


import com.takeatrip.models.auth.LoginResponse
import com.takeatrip.models.auth.RegistrationResponse
import com.takeatrip.models.organisation.OrganisationResponse
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {


    @FormUrlEncoded
    @POST("user/login")
    fun login(@FieldMap map: Map<String, String>): Call<LoginResponse>

    @FormUrlEncoded
    @POST("user/store")
    fun register(@FieldMap map: Map<String, String>): Call<RegistrationResponse>

    @FormUrlEncoded
    @POST("user/organisation/store")
    fun addOrganisation(@Header("Authorization") token: String, @FieldMap map: Map<String, String>): Call<OrganisationResponse>


}