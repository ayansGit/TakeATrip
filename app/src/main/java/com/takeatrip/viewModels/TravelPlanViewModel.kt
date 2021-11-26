package com.takeatrip.viewModels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.graphicalab.api.ApiHelper
import com.graphicalab.api.ApiMethods
import com.takeatrip.models.hotel.GetHotelByLocationResponse
import com.takeatrip.models.hotel.GetHotelListResponse
import com.takeatrip.models.hotel.Hotel
import com.takeatrip.models.location.AddLocationResponse
import com.takeatrip.models.location.GetLocationResponse
import com.takeatrip.models.location.LocationData
import com.takeatrip.models.meal.GetMealResponse
import com.takeatrip.models.meal.MealData
import com.takeatrip.models.room.RoomData
import com.takeatrip.repository.HotelRepository
import com.takeatrip.repository.LocationRepository
import com.takeatrip.utils.StoragePreference

class TravelPlanViewModel(application: Application) : BaseViewModel(application),
    ApiHelper.ApiResponseListener {

    private val addLocationLiveData = MutableLiveData<AddLocationResponse>()
    private val getLocationLiveData = MutableLiveData<List<LocationData>>()
    private val getRoomLiveData = MutableLiveData<List<RoomData>>()
    private val getMealLiveData = MutableLiveData<List<MealData>>()
    private val getHotelLiveData = MutableLiveData<List<Hotel>>()

    fun observeGetLocation() = getLocationLiveData
    fun observeHotel() = getHotelLiveData
    fun observeGetMeal() = getMealLiveData

    override fun onSuccess(response: Any?, method: Enum<ApiMethods>) {
        dataLoading.value = false

        when (method) {

            ApiMethods.LOCATION_LIST -> {
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

            ApiMethods.MEAL_LIST -> {
                if(response is GetMealResponse){
                    if(response.success){
                      getMealLiveData.value = response.data
                    }else{
                        var message = ""
                        for (msg in response.message) {
                            message += msg + "\n"
                        }
                        toastMessage.value = message.trim()
                    }

                }
            }

            ApiMethods.HOTEL_LIST -> {
                if(response is GetHotelByLocationResponse){
                    if(response.success){
                        getHotelLiveData.value = response.data
                    }
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

    fun getLocation(){
        dataLoading.value = true
        StoragePreference.getToken(getApplication())?.let {
            LocationRepository.getInstance().locationList("Bearer $it", this)
        }
    }

    fun getMeal(){
        if(dataLoading.value == false)
        dataLoading.value = true

        StoragePreference.getToken(getApplication())?.let {
            HotelRepository.getInstance().getMeal("Bearer $it", this)
        }
    }

    fun getHotelByLocation(locationId: String){
        if(dataLoading.value == false)
            dataLoading.value = true

        StoragePreference.getToken(getApplication())?.let {
            HotelRepository.getInstance().getHotelByLocation("Bearer $it", locationId,this)
        }
    }


}
