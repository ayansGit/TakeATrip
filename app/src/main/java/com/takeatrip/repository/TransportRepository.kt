package com.takeatrip.repository

import com.graphicalab.api.ApiHelper
import com.graphicalab.api.ApiMethods
import com.takeatrip.models.auth.LoginResponse
import com.takeatrip.models.auth.RegistrationResponse
import com.takeatrip.models.location.AddLocationResponse
import com.takeatrip.models.location.GetLocationResponse
import com.takeatrip.models.meal.GetMealResponse
import com.takeatrip.models.organisation.OrganisationResponse
import com.takeatrip.models.room.AddRoomResponse
import com.takeatrip.models.room.GetRoomResponse
import com.takeatrip.models.transport.AddTransportResponse
import com.takeatrip.models.transport.GetTransportResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransportRepository {

    fun addTransport(token: String, locationId: String, day: Int, plan: String, transportPrice: String, ticket: String, apiResponseListener: ApiHelper.ApiResponseListener) {

        ApiHelper.RestService.getInstance().addTransport(token, locationId, day, plan, transportPrice, ticket).enqueue(object : Callback<AddTransportResponse> {
            override fun onResponse(call: Call<AddTransportResponse>, response: Response<AddTransportResponse>) {
                if (response.code() == 200)
                    apiResponseListener.onSuccess(response.body(), ApiMethods.ADD_TRANSPORT)
                else apiResponseListener.onFailure(response.message())
            }

            override fun onFailure(call: Call<AddTransportResponse>, t: Throwable) {
                apiResponseListener.onFailure(t.message)
            }

        })
    }

    fun getTransport(token: String, apiResponseListener: ApiHelper.ApiResponseListener) {
        ApiHelper.RestService.getInstance().getTransport(token).enqueue(object : Callback<GetTransportResponse> {

            override fun onResponse(call: Call<GetTransportResponse>, response: Response<GetTransportResponse>) {
                if (response.code() == 200)
                    apiResponseListener.onSuccess(response.body(), ApiMethods.TRANSPORT_LIST)
                else apiResponseListener.onFailure(response.message())
            }

            override fun onFailure(call: Call<GetTransportResponse>, t: Throwable) {
                apiResponseListener.onFailure(t.message)
            }

        })
    }




    companion object {
        private var INSTANCE: TransportRepository? = null
        fun getInstance() = INSTANCE
            ?: TransportRepository().also {
                INSTANCE = it
            }
    }
}