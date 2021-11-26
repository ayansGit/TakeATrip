package com.graphicalab.api


import com.takeatrip.models.auth.LoginResponse
import com.takeatrip.models.auth.RegistrationResponse
import com.takeatrip.models.hotel.AddHotelResponse
import com.takeatrip.models.hotel.DeleteHotelResponse
import com.takeatrip.models.hotel.GetHotelByLocationResponse
import com.takeatrip.models.hotel.GetHotelListResponse
import com.takeatrip.models.hotel.addHotelRequest.HotelRequestData1
import com.takeatrip.models.hotel.addHotelRequest.HotelRequestData2
import com.takeatrip.models.hotel.addHotelRequest.HotelRequestData3
import com.takeatrip.models.hotel.addHotelRequest.HotelRequestData4
import com.takeatrip.models.location.AddLocationResponse
import com.takeatrip.models.location.GetLocationResponse
import com.takeatrip.models.meal.GetMealResponse
import com.takeatrip.models.organisation.OrganisationResponse
import com.takeatrip.models.room.AddRoomResponse
import com.takeatrip.models.room.GetRoomResponse
import com.takeatrip.models.transport.AddTransportResponse
import com.takeatrip.models.transport.GetTransportResponse
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

    @GET("user/meal-type/list")
    fun getMeal(@Header("Authorization") token: String): Call<GetMealResponse>


    @POST("user/hotel/store")
    fun addHotels(@Header("Authorization") token: String, @Body hotelRequestData1: HotelRequestData1): Call<AddHotelResponse>

    @POST("user/hotel/store")
    fun addHotels(@Header("Authorization") token: String, @Body hotelRequestData2: HotelRequestData2): Call<AddHotelResponse>


    @POST("user/hotel/store")
    fun addHotels(@Header("Authorization") token: String, @Body hotelRequestData3: HotelRequestData3): Call<AddHotelResponse>


    @POST("user/hotel/store")
    fun addHotels(@Header("Authorization") token: String, @Body hotelRequestData4: HotelRequestData4): Call<AddHotelResponse>

    @GET("user/hotel/list")
    fun getHotel(@Header("Authorization") token: String): Call<GetHotelListResponse>

    @FormUrlEncoded
    @POST("user/transport/store")
    fun addTransport(@Header("Authorization") token: String, @Field("location_id") locationId: String, @Field("day") day: Int,
                     @Field("description") description: String, @Field("transport_price") transportPrice: String,
                     @Field("ticket_price") ticketPrice: String): Call<AddTransportResponse>

    @GET("user/transport/list")
    fun getTransport(@Header("Authorization") token: String): Call<GetTransportResponse>

    @GET("user/location/{locationId}/hotel")
    fun getHotelByLocation(@Header("Authorization") token: String, @Path("locationId") locationId: String): Call<GetHotelByLocationResponse>

    @GET("user/hotel/{hotelId}/delete")
    fun deleteHotel(@Header("Authorization") token: String, @Path("hotelId") hotelId: String): Call<DeleteHotelResponse>
}