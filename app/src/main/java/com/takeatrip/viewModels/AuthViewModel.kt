package com.takeatrip.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.graphicalab.api.ApiHelper
import com.graphicalab.api.ApiMethods
import com.takeatrip.models.auth.LoginResponse
import com.takeatrip.models.auth.RegistrationResponse
import com.takeatrip.models.auth.UserData
import com.takeatrip.repository.AuthRepository
import com.takeatrip.utils.StoragePreference

class AuthViewModel(application: Application) : BaseViewModel(application),
    ApiHelper.ApiResponseListener {

    private val loginLiveData = MutableLiveData<UserData>()
    private val registerLiveData = MutableLiveData<UserData>()

    fun observeLogin() = loginLiveData
    fun observeRegistration() = registerLiveData

    override fun onSuccess(response: Any?, method: Enum<ApiMethods>) {
        dataLoading.value = false
        when (method) {

            ApiMethods.LOGIN -> {
                if(response is LoginResponse) {
                    if (response.success) {
                        loginLiveData.value = response.data
                        StoragePreference.setToken(getApplication(), response.token)
                        StoragePreference.setUserId(getApplication(), response.data.userId)
                        StoragePreference.setIsOrganisationAdded(getApplication(), response.data.isOrganisationAdded)
                    }
                    var message = ""
                    for(msg in response.message){
                        message += msg +"\n"
                    }
                    toastMessage.value = message.trim()
                }
            }

            ApiMethods.REGISTER -> {
                if(response is RegistrationResponse) {
                    if (response.success) {
                        registerLiveData.value = response.data
                        StoragePreference.setToken(getApplication(), response.token)
                        StoragePreference.setUserId(getApplication(), response.data.userId)
                        StoragePreference.setIsOrganisationAdded(getApplication(), response.data.isOrganisationAdded)
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

    fun login(email: String, password: String){
        dataLoading.value = true
        val map = HashMap<String, String>()
        map["email"] = email
        map["password"] = password
        AuthRepository.getInstance().login(map, this)
    }

    fun register(email: String, fullName: String, mobileNo: String, password: String){
        dataLoading.value = true
        val map = HashMap<String, String>()
        map["email"] = email
        map["full_name"] = fullName
        map["phone"] = mobileNo
        map["password"] = password
        AuthRepository.getInstance().register(map, this)
    }




}