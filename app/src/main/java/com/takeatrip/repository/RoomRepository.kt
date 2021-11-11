package com.takeatrip.repository

import com.graphicalab.api.ApiHelper
import com.graphicalab.api.ApiMethods
import com.takeatrip.models.auth.LoginResponse
import com.takeatrip.models.auth.RegistrationResponse
import com.takeatrip.models.location.AddLocationResponse
import com.takeatrip.models.location.GetLocationResponse
import com.takeatrip.models.organisation.OrganisationResponse
import com.takeatrip.models.room.AddRoomResponse
import com.takeatrip.models.room.GetRoomResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoomRepository {

    fun addRoom(token: String, name: String, allowExtraBed: Int, apiResponseListener: ApiHelper.ApiResponseListener) {
        ApiHelper.RestService.getInstance().addRooms(token, name, allowExtraBed).enqueue(object : Callback<AddRoomResponse> {
            override fun onResponse(call: Call<AddRoomResponse>, response: Response<AddRoomResponse>) {

                if (response.code() == 200)
                    apiResponseListener.onSuccess(response.body(), ApiMethods.ADD_ROOM)
                else apiResponseListener.onFailure(response.message())
            }

            override fun onFailure(call: Call<AddRoomResponse>, t: Throwable) {
                apiResponseListener.onFailure(t.message)
            }

        })
    }

    fun roomList(token: String, apiResponseListener: ApiHelper.ApiResponseListener) {
        ApiHelper.RestService.getInstance().getRooms(token).enqueue(object : Callback<GetRoomResponse> {
            override fun onResponse(call: Call<GetRoomResponse>, response: Response<GetRoomResponse>) {

                if (response.code() == 200)
                    apiResponseListener.onSuccess(response.body(), ApiMethods.ROOM_LIST)
                else apiResponseListener.onFailure(response.message())
            }

            override fun onFailure(call: Call<GetRoomResponse>, t: Throwable) {
                apiResponseListener.onFailure(t.message)
            }

        })
    }




    companion object {
        private var INSTANCE: RoomRepository? = null
        fun getInstance() = INSTANCE
            ?: RoomRepository().also {
                INSTANCE = it
            }
    }
}