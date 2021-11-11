package com.takeatrip.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.graphicalab.api.ApiHelper
import com.graphicalab.api.ApiMethods
import com.takeatrip.models.location.AddLocationResponse
import com.takeatrip.models.location.GetLocationResponse
import com.takeatrip.models.location.LocationData
import com.takeatrip.repository.LocationRepository
import com.takeatrip.utils.StoragePreference

class LocationViewModel(application: Application) : BaseViewModel(application),
    ApiHelper.ApiResponseListener {

    private val addLocationLiveData = MutableLiveData<AddLocationResponse>()
    private val getLocationLiveData = MutableLiveData<List<LocationData>>()

    fun observeAddLocation() = addLocationLiveData
    fun observeGetLocation() = getLocationLiveData

    override fun onSuccess(response: Any?, method: Enum<ApiMethods>) {
        dataLoading.value = false

        when (method) {

            ApiMethods.ADD_LOCATION -> {
                if (response is AddLocationResponse) {
                    if (response.success)
                        addLocationLiveData.value = response
                    var message = ""
                    for (msg in response.message) {
                        message += msg + "\n"
                    }
                    toastMessage.value = message.trim()
                }

            }

            ApiMethods.LOCATION_LIST -> {
                if(response is GetLocationResponse){
                    if(response is GetLocationResponse){
                        if(response.success){
                            getLocationLiveData.value = response.data
                        }else{
                            var message = ""
                            for (msg in response.message) {
                                message += msg + "\n"
                            }
                            toastMessage.value = message.trim()
                        }

                    }
                }
            }
        }
    }

    override fun onFailure(message: String?) {
        dataLoading.value = false
        toastMessage.value = message
    }

    fun addLocation(name: String, state: String){
        dataLoading.value = true
        val map = HashMap<String, String>()
        map["name"] = name
        map["state"] = state
        StoragePreference.getToken(getApplication())?.let {
            LocationRepository.getInstance().addLocation( "Bearer $it", map, this)
        }
    }

    fun getLocation(){
        dataLoading.value = true
        StoragePreference.getToken(getApplication())?.let {
            LocationRepository.getInstance().locationList("Bearer $it", this)
        }
    }


}
