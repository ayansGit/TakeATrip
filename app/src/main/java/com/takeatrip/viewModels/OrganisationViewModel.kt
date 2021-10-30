package com.takeatrip.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.graphicalab.api.ApiHelper
import com.graphicalab.api.ApiMethods
import com.takeatrip.models.auth.LoginResponse
import com.takeatrip.models.auth.RegistrationResponse
import com.takeatrip.models.auth.UserData
import com.takeatrip.models.organisation.OrganisationResponse
import com.takeatrip.repository.AuthRepository
import com.takeatrip.repository.OrganisationRepository
import com.takeatrip.utils.StoragePreference

class OrganisationViewModel(application: Application) : BaseViewModel(application),
    ApiHelper.ApiResponseListener {

    private val addOrganisationLiveData = MutableLiveData<OrganisationResponse>()

    fun observeAddOrganisation() = addOrganisationLiveData

    override fun onSuccess(response: Any?, method: Enum<ApiMethods>) {
        dataLoading.value = false
        when (method) {

            ApiMethods.ADD_ORGANISATION -> {
                if(response is OrganisationResponse) {
                    if (response.success) {
                        addOrganisationLiveData.value = response
                    }
                    var message = ""
                    for(msg in response.message){
                        message += msg +"\n"
                    }
                    toastMessage.value = message.trim()
                }
            }


        }

    }


    override fun onFailure(message: String?) {
        dataLoading.value = false
        toastMessage.value = message
    }

    fun addOrganisation(name: String, email: String, phone: String, address: String){
        dataLoading.value = true
        val map = HashMap<String, String>()
        map["email"] = email
        map["name"] = name
        map["phone"] = phone
        map["address"] = address
        StoragePreference.getToken(getApplication())?.let {
            OrganisationRepository.getInstance().addOrganisation("Bearer $it", map, this)
        }

    }



}