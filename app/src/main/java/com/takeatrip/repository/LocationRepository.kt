package com.takeatrip.repository

import com.graphicalab.api.ApiHelper
import com.graphicalab.api.ApiMethods
import com.takeatrip.models.auth.LoginResponse
import com.takeatrip.models.auth.RegistrationResponse
import com.takeatrip.models.location.AddLocationResponse
import com.takeatrip.models.location.GetLocationResponse
import com.takeatrip.models.organisation.OrganisationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationRepository {

    fun addLocation(token: String, map: Map<String, String>, apiResponseListener: ApiHelper.ApiResponseListener) {
        ApiHelper.RestService.getInstance().addLocation(token, map).enqueue(object : Callback<AddLocationResponse> {
            override fun onResponse(call: Call<AddLocationResponse>, response: Response<AddLocationResponse>) {

                if (response.code() == 200)
                    apiResponseListener.onSuccess(response.body(), ApiMethods.ADD_LOCATION)
                else apiResponseListener.onFailure(response.message())
            }

            override fun onFailure(call: Call<AddLocationResponse>, t: Throwable) {
                apiResponseListener.onFailure(t.message)
            }

        })
    }

    fun locationList(token: String, apiResponseListener: ApiHelper.ApiResponseListener) {
        ApiHelper.RestService.getInstance().getLocation(token).enqueue(object : Callback<GetLocationResponse> {
            override fun onResponse(call: Call<GetLocationResponse>, response: Response<GetLocationResponse>) {

                if (response.code() == 200)
                    apiResponseListener.onSuccess(response.body(), ApiMethods.LOCATION_LIST)
                else apiResponseListener.onFailure(response.message())
            }

            override fun onFailure(call: Call<GetLocationResponse>, t: Throwable) {
                apiResponseListener.onFailure(t.message)
            }

        })
    }




    companion object {
        private var INSTANCE: LocationRepository? = null
        fun getInstance() = INSTANCE
            ?: LocationRepository().also {
                INSTANCE = it
            }
    }
}