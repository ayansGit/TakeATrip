package com.takeatrip.repository

import com.graphicalab.api.ApiHelper
import com.graphicalab.api.ApiMethods
import com.takeatrip.models.hotel.*
import com.takeatrip.models.hotel.addHotelRequest.*
import com.takeatrip.models.meal.ExtraMattressResponse
import com.takeatrip.models.meal.GetMealResponse
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

    fun getMeal(token: String, roomId: String, apiResponseListener: ApiHelper.ApiResponseListener) {

        ApiHelper.RestService.getInstance().getMeal(token, roomId).enqueue(object : Callback<GetMealResponse> {

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

    fun getExtraMattress(token: String, hotelId: String, roomId: String, apiResponseListener: ApiHelper.ApiResponseListener) {

        ApiHelper.RestService.getInstance().getExtraMattressMeal(token, hotelId, roomId).enqueue(object : Callback<ExtraMattressResponse> {

            override fun onResponse(call: Call<ExtraMattressResponse>, response: Response<ExtraMattressResponse>) {
                if (response.code() == 200)
                    apiResponseListener.onSuccess(response.body(), ApiMethods.GET_EXTRA_MATTRESS)
                else apiResponseListener.onFailure(response.message())
            }

            override fun onFailure(call: Call<ExtraMattressResponse>, t: Throwable) {
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

    fun getHotelByLocation(token: String, locationId: String, apiResponseListener: ApiHelper.ApiResponseListener) {
        ApiHelper.RestService.getInstance().getHotelByLocation(token, locationId).enqueue(object : Callback<GetHotelByLocationResponse> {

            override fun onResponse(call: Call<GetHotelByLocationResponse>, response: Response<GetHotelByLocationResponse>) {
                if (response.code() == 200)
                    apiResponseListener.onSuccess(response.body(), ApiMethods.HOTEL_LIST)
                else apiResponseListener.onFailure(response.message())
            }

            override fun onFailure(call: Call<GetHotelByLocationResponse>, t: Throwable) {
                apiResponseListener.onFailure(t.message)
            }

        })
    }

    fun deleteHotel(token: String, hotelId: String, apiResponseListener: ApiHelper.ApiResponseListener) {
        ApiHelper.RestService.getInstance().deleteHotel(token, hotelId).enqueue(object : Callback<DeleteHotelResponse> {

            override fun onResponse(call: Call<DeleteHotelResponse>, response: Response<DeleteHotelResponse>) {
                if (response.code() == 200)
                    apiResponseListener.onSuccess(response.body(), ApiMethods.DELETE_HOTEL)
                else apiResponseListener.onFailure(response.message())
            }

            override fun onFailure(call: Call<DeleteHotelResponse>, t: Throwable) {
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

    fun addExtraMattress(token: String, extraMattressRequestData: ExtraMattressRequestData, apiResponseListener: ApiHelper.ApiResponseListener) {
        ApiHelper.RestService.getInstance().addExtraMattress(token, extraMattressRequestData).enqueue(object : Callback<AddExtraMattressResponse> {

            override fun onResponse(call: Call<AddExtraMattressResponse>, response: Response<AddExtraMattressResponse>) {
                if (response.code() == 200)
                    apiResponseListener.onSuccess(response.body(), ApiMethods.ADD_EXTRA_MATTRESS)
                else apiResponseListener.onFailure(response.message())
            }

            override fun onFailure(call: Call<AddExtraMattressResponse>, t: Throwable) {
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