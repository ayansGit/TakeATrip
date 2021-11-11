package com.graphicalab.api


import com.takeatrip.models.auth.LoginResponse
import com.takeatrip.models.auth.RegistrationResponse
import com.takeatrip.models.location.AddLocationResponse
import com.takeatrip.models.location.GetLocationResponse
import com.takeatrip.models.organisation.OrganisationResponse
import com.takeatrip.models.room.AddRoomResponse
import com.takeatrip.models.room.GetRoomResponse
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

    @GET("user/location/list")
    fun getLocation(@Header("Authorization") token: String): Call<GetLocationResponse>

    @FormUrlEncoded
    @POST("user/location/store")
    fun addLocation(@Header("Authorization") token: String, @FieldMap map: Map<String, String>): Call<AddLocationResponse>

    @GET("user/room-type/list")
    fun getRooms(@Header("Authorization") token: String): Call<GetRoomResponse>

    @FormUrlEncoded
    @POST("user/room-type/store")
    fun addRooms(@Header("Authorization") token: String, @Field("name") name: String, @Field("allowed_extra_bed") allowExtraBed: Int): Call<AddRoomResponse>


}