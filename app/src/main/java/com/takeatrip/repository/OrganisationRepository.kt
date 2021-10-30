package com.takeatrip.repository

import com.graphicalab.api.ApiHelper
import com.graphicalab.api.ApiMethods
import com.takeatrip.models.auth.LoginResponse
import com.takeatrip.models.auth.RegistrationResponse
import com.takeatrip.models.organisation.OrganisationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrganisationRepository {

    fun addOrganisation(token: String, map: Map<String, String>, apiResponseListener: ApiHelper.ApiResponseListener) {
        ApiHelper.RestService.getInstance().addOrganisation(token, map).enqueue(object : Callback<OrganisationResponse> {
            override fun onResponse(call: Call<OrganisationResponse>, response: Response<OrganisationResponse>) {

                if (response.code() == 200)
                    apiResponseListener.onSuccess(response.body(), ApiMethods.ADD_ORGANISATION)
                else apiResponseListener.onFailure(response.message())
            }

            override fun onFailure(call: Call<OrganisationResponse>, t: Throwable) {
                apiResponseListener.onFailure(t.message)
            }

        })
    }




    companion object {
        private var INSTANCE: OrganisationRepository? = null
        fun getInstance() = INSTANCE
            ?: OrganisationRepository().also {
                INSTANCE = it
            }
    }
}