package com.takeatrip.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.graphicalab.api.ApiHelper
import com.graphicalab.api.ApiMethods
import com.takeatrip.models.location.AddLocationResponse
import com.takeatrip.models.location.GetLocationResponse
import com.takeatrip.models.location.LocationData
import com.takeatrip.models.room.AddRoomResponse
import com.takeatrip.models.room.GetRoomResponse
import com.takeatrip.models.room.RoomData
import com.takeatrip.models.transport.AddTransportResponse
import com.takeatrip.models.transport.GetTransportResponse
import com.takeatrip.models.transport.TransportLocation
import com.takeatrip.repository.LocationRepository
import com.takeatrip.repository.RoomRepository
import com.takeatrip.repository.TransportRepository
import com.takeatrip.utils.StoragePreference

class TransportViewModel(application: Application) : BaseViewModel(application),
    ApiHelper.ApiResponseListener {

    private val addTransportLiveData = MutableLiveData<AddTransportResponse>()
    private val getTransportLiveData = MutableLiveData<List<TransportLocation>>()

    fun observeAddTransport() = addTransportLiveData
    fun observeGetTransport() = getTransportLiveData

    override fun onSuccess(response: Any?, method: Enum<ApiMethods>) {
        dataLoading.value = false

        when (method) {

            ApiMethods.ADD_TRANSPORT -> {

                if (response is AddTransportResponse) {
                    if (response.success)
                        addTransportLiveData.value = response
                    var message = ""
                    for (msg in response.message) {
                        message += msg + "\n"
                    }
                    toastMessage.value = message.trim()
                }

            }

            ApiMethods.TRANSPORT_LIST -> {
                if (response is GetTransportResponse) {
                    if (response.success)
                        getTransportLiveData.value = response.data
                    var message = ""
                    for (msg in response.message) {
                        message += msg + "\n"
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

    fun addTransport(locationId: String, day: Int, travelPlan: String, travelPrice: String, otherPrice: String) {
        dataLoading.value = true
        StoragePreference.getToken(getApplication())?.let {
            TransportRepository.getInstance().addTransport("Bearer $it", locationId, day, travelPlan, travelPrice, otherPrice, this)
        }
    }

    fun getTransport(){
        dataLoading.value = true
        StoragePreference.getToken(getApplication())?.let {
            TransportRepository.getInstance().getTransport("Bearer $it", this)
        }
    }



}
