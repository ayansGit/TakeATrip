package com.takeatrip.repository

import com.graphicalab.api.ApiHelper
import com.graphicalab.api.ApiMethods
import com.takeatrip.models.auth.LoginResponse
import com.takeatrip.models.auth.RegistrationResponse
import com.takeatrip.models.hotel.AddHotelResponse
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HotelRepository {

    fun getMeal(token: String, apiResponseListener: ApiHelper.ApiResponseListener) {
        ApiHelper.RestService.getInstance().getMeal(token).enqueue(object : Callback<GetMealResponse> {

            override fun onResponse(call: Call<GetMealResponse>, response: Response<GetMealResponse>) {
                if (response.code() == 200)
                    apiResponseListener.onSuccess(response.body(), ApiMethods.MEAL_LIST)
                else apiResponseListener.onFailure(response.message())
            }

            override fun onFailure(call: Call<GetMealResponse>, t: Throwable) {
                apiResponseListener.onFailure(t.message)
            }

        })
    }

    fun getHotel(token: String, apiResponseListener: ApiHelper.ApiResponseListener) {
        ApiHelper.RestService.getInstance().getHotel(token).enqueue(object : Callback<GetHotelListResponse> {

            override fun onResponse(call: Call<GetHotelListResponse>, response: Response<GetHotelListResponse>) {
                if (response.code() == 200)
                    apiResponseListener.onSuccess(response.body(), ApiMethods.HOTEL_LIST)
                else apiResponseListener.onFailure(response.message())
            }

            override fun onFailure(call: Call<GetHotelListResponse>, t: Throwable) {
                apiResponseListener.onFailure(t.message)
            }

        })
    }

    fun addHotel(token: String, hotelRequestData: HotelRequestData1, apiResponseListener: ApiHelper.ApiResponseListener) {
        ApiHelper.RestService.getInstance().addHotels(token, hotelRequestData).enqueue(object : Callback<AddHotelResponse> {

            override fun onResponse(call: Call<AddHotelResponse>, response: Response<AddHotelResponse>) {
                if (response.code() == 200)
                    apiResponseListener.onSuccess(response.body(), ApiMethods.ADD_HOTEL)
                else apiResponseListener.onFailure(response.message())
            }

            override fun onFailure(call: Call<AddHotelResponse>, t: Throwable) {
                apiResponseListener.onFailure(t.message)
            }

        })
    }

    fun addHotel(token: String, hotelRequestData: HotelRequestData2, apiResponseListener: ApiHelper.ApiResponseListener) {
        ApiHelper.RestService.getInstance().addHotels(token, hotelRequestData).enqueue(object : Callback<AddHotelResponse> {

            override fun onResponse(call: Call<AddHotelResponse>, response: Response<AddHotelResponse>) {
                if (response.code() == 200)
                    apiResponseListener.onSuccess(response.body(), ApiMethods.ADD_HOTEL)
                else apiResponseListener.onFailure(response.message())
            }

            override fun onFailure(call: Call<AddHotelResponse>, t: Throwable) {
                apiResponseListener.onFailure(t.message)
            }

        })
    }

    fun addHotel(token: String, hotelRequestData: HotelRequestData3, apiResponseListener: ApiHelper.ApiResponseListener) {
        ApiHelper.RestService.getInstance().addHotels(token, hotelRequestData).enqueue(object : Callback<AddHotelResponse> {

            override fun onResponse(call: Call<AddHotelResponse>, response: Response<AddHotelResponse>) {
                if (response.code() == 200)
                    apiResponseListener.onSuccess(response.body(), ApiMethods.ADD_HOTEL)
                else apiResponseListener.onFailure(response.message())
            }

            override fun onFailure(call: Call<AddHotelResponse>, t: Throwable) {
                apiResponseListener.onFailure(t.message)
            }

        })
    }

    fun addHotel(token: String, hotelRequestData: HotelRequestData4, apiResponseListener: ApiHelper.ApiResponseListener) {
        ApiHelper.RestService.getInstance().addHotels(token, hotelRequestData).enqueue(object : Callback<AddHotelResponse> {

            override fun onResponse(call: Call<AddHotelResponse>, response: Response<AddHotelResponse>) {
                if (response.code() == 200)
                    apiResponseListener.onSuccess(response.body(), ApiMethods.ADD_HOTEL)
                else apiResponseListener.onFailure(response.message())
            }

            override fun onFailure(call: Call<AddHotelResponse>, t: Throwable) {
                apiResponseListener.onFailure(t.message)
            }

        })
    }




    companion object {
        private var INSTANCE: HotelRepository? = null
        fun getInstance() = INSTANCE
            ?: HotelRepository().also {
                INSTANCE = it
            }
    }
}