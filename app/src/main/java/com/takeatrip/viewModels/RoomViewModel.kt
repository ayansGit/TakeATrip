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
import com.takeatrip.repository.LocationRepository
import com.takeatrip.repository.RoomRepository
import com.takeatrip.utils.StoragePreference

class RoomViewModel(application: Application) : BaseViewModel(application),
    ApiHelper.ApiResponseListener {

    private val addRoomLiveData = MutableLiveData<AddRoomResponse>()
    private val getRoomLiveData = MutableLiveData<List<RoomData>>()

    fun observeAddRoom() = addRoomLiveData
    fun observeGetRoom() = getRoomLiveData

    override fun onSuccess(response: Any?, method: Enum<ApiMethods>) {
        dataLoading.value = false

        when (method) {

            ApiMethods.ADD_ROOM -> {
                if (response is AddRoomResponse) {
                    if (response.success)
                        addRoomLiveData.value = response
                    var message = ""
                    for (msg in response.message) {
                        message += msg + "\n"
                    }
                    toastMessage.value = message.trim()
                }

            }

            ApiMethods.ROOM_LIST -> {

                if (response is GetRoomResponse) {
                    if (response.success) {
                        getRoomLiveData.value = response.data
                    } else {
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

    override fun onFailure(message: String?) {
        dataLoading.value = false
        toastMessage.value = message
    }

    fun addRoom(name: String, allowExtraBed: Int) {
        dataLoading.value = true
        StoragePreference.getToken(getApplication())?.let {
            RoomRepository.getInstance().addRoom("Bearer $it", name, allowExtraBed, this)
        }
    }

    fun getRoom() {
        dataLoading.value = true
        StoragePreference.getToken(getApplication())?.let {
            RoomRepository.getInstance().roomList("Bearer $it", this)
        }
    }


}
