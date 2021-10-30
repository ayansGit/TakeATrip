package com.takeatrip.repository

import com.graphicalab.api.ApiHelper
import com.graphicalab.api.ApiMethods
import com.takeatrip.models.auth.LoginResponse
import com.takeatrip.models.auth.RegistrationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository {

    fun login(map: Map<String, String>, apiResponseListener: ApiHelper.ApiResponseListener) {
        ApiHelper.RestService.getInstance().login(map).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                if (response.code() == 200)
                    apiResponseListener.onSuccess(response.body(), ApiMethods.LOGIN)
                else apiResponseListener.onFailure(response.message())
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                apiResponseListener.onFailure(t.message)
            }

        })
    }

    fun register(
        map: Map<String, String>,
        apiResponseListener: ApiHelper.ApiResponseListener
    ) {
        ApiHelper.RestService.getInstance().register(map)
            .enqueue(object : Callback<RegistrationResponse> {
                override fun onResponse(
                    call: Call<RegistrationResponse>,
                    response: Response<RegistrationResponse>
                ) {
                    if (response.code() == 200)
                        apiResponseListener.onSuccess(response.body(), ApiMethods.REGISTER)
                    else apiResponseListener.onFailure(response.message())
                }

                override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                    apiResponseListener.onFailure(t.message)
                }

            })
    }



    companion object {
        private var INSTANCE: AuthRepository? = null
        fun getInstance() = INSTANCE
            ?: AuthRepository().also {
                INSTANCE = it
            }
    }
}