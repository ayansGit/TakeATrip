package com.graphicalab.api

import com.takeatrip.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiHelper {

    private object RetrofitInstance {

        private val okHttpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        fun getInstance(): Retrofit {
            return retrofit
        }
    }

    object RestService {

        private val retrofitService: RetrofitService = RetrofitInstance
            .getInstance()
            .create(RetrofitService::class.java)

        fun getInstance(): RetrofitService{
            return retrofitService
        }

    }

    interface ApiResponseListener{
        fun onSuccess(response: Any?, method: Enum<ApiMethods>)
        fun onFailure(message: String?)
    }

}