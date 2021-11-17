package com.takeatrip.views.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.graphicalab.utils.BaseActivity
import com.takeatrip.R
import kotlinx.android.synthetic.main.activity_add_hotel.*
import kotlinx.android.synthetic.main.header.*
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.takeatrip.adapters.SelectedRoomAdapter
import com.takeatrip.models.hotel.addHotelRequest.*
import com.takeatrip.models.location.LocationData
import com.takeatrip.models.meal.MealData
import com.takeatrip.models.room.RoomData
import com.takeatrip.viewModels.HotelViewModel
import com.google.gson.Gson





class AddHotelActivity : BaseActivity(), SelectedRoomAdapter.SelectedRoomListener {

    private val locationList = ArrayList<LocationData>()
    private var location_id =""
    private var name =""
    private var rating = 0
    private var address = ""
    private lateinit var hotelViewModel: HotelViewModel
    private lateinit var ad: ArrayAdapter<*>
    private val roomList = ArrayList<RoomData>()
    private val mealList = ArrayList<MealData>()
    private var generalMealMap = HashMap<String, HashMap<String, MealData>>()
    private var withMattressMealMap = HashMap<String, HashMap<String, MealData>>()
    private var withoutMattressMealMap = HashMap<String, HashMap<String, MealData>>()
    private lateinit var selectedRoomAdapter: SelectedRoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_hotel)
        hotelViewModel = ViewModelProvider(this).get(HotelViewModel::class.java)
        tvTitle.text = "Add Hotels"
        ivBack.setOnClickListener {
            onBackPressed()
        }
        selectedRoomAdapter = SelectedRoomAdapter(this, roomList, mealList, this)
        rcRoomType.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rcRoomType.adapter = selectedRoomAdapter
        ad = ArrayAdapter<LocationData>(
            this,
            android.R.layout.simple_spinner_item,
            locationList
        )

        ad.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        locationSpinner.adapter = ad

//        rcMealType.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
//        rcMealType.adapter = MealAdapter(this)

        ivAdd.setOnClickListener {
            startActivityForResult(Intent(this, SelectRoomActivity::class.java), 1)
        }

        btAddMore.setOnClickListener {
            startActivityForResult(Intent(this, SelectRoomActivity::class.java), 1)
        }

        btSubmit.setOnClickListener {
            submit()
        }

        observeLocationList()
        observeMealList()
        observeAddHotel()
        observeLoader()
        observeToast()

        hotelViewModel.getLocation()
        hotelViewModel.getMeal()

    }

    override fun onGeneralMealUpdated(mealMap: HashMap<String, HashMap<String, MealData>>) {
        generalMealMap = mealMap
    }

    override fun onMealWithMattressUpdated(mealMap: HashMap<String, HashMap<String, MealData>>) {
        withMattressMealMap = mealMap
    }

    override fun onMealWithoutMattressUpdated(mealMap: HashMap<String, HashMap<String, MealData>>) {
        withoutMattressMealMap = mealMap
    }

    private fun submit(){
//        Log.d("TAG", generalMealMap.size.toString())
        name = etHotelName.text.toString()
        rating = hotelRating.rating.toInt()
        address = etHotelAddress.text.toString()

        val room1List = ArrayList<Room1>()
        val room2List = ArrayList<Room2>()
        val room3List = ArrayList<Room3>()
        val room4List = ArrayList<Room4>()

        if(withMattressMealMap.isNotEmpty() && withoutMattressMealMap.isNotEmpty()){

            for((key, mealMap) in generalMealMap){
                val generalMealList = ArrayList<Meal>()
                val withMattressMealList = ArrayList<Meal>()
                val withoutMattressMealList = ArrayList<Meal>()

                for((i, meal) in mealMap){
                    generalMealList.add(Meal(meal.mealTypeId, meal.price))
                }

                withMattressMealMap[key]?.let {
                    for((i, meal) in withMattressMealMap[key]!!){
                        withMattressMealList.add(Meal(meal.mealTypeId, meal.price))
                    }
                }

                withoutMattressMealMap[key]?.let {
                    for((i, meal) in withoutMattressMealMap[key]!!){
                        withoutMattressMealList.add(Meal(meal.mealTypeId, meal.price))
                    }
                }

                room4List.add(Room4(key, generalMealList, withMattressMealList, withoutMattressMealList))
            }

            val hotelRequestData = HotelRequestData4(name, location_id, rating, address, room4List)
            hotelViewModel.addHotel(hotelRequestData)

        }else if(withMattressMealMap.isNotEmpty()){

            for((key, mealMap) in generalMealMap){
                val generalMealList = ArrayList<Meal>()
                val withMattressMealList = ArrayList<Meal>()

                for((i, meal) in mealMap){
                    generalMealList.add(Meal(meal.mealTypeId, meal.price))
                }

                withMattressMealMap[key]?.let {
                    for((i, meal) in withMattressMealMap[key]!!){
                        withMattressMealList.add(Meal(meal.mealTypeId, meal.price))
                    }
                }

                room3List.add(Room3(key, generalMealList, withMattressMealList))
            }

            val hotelRequestData = HotelRequestData3(name, location_id, rating, address, room3List)
            hotelViewModel.addHotel(hotelRequestData)

        }else if(withoutMattressMealMap.isNotEmpty()){

            for((key, mealMap) in generalMealMap){
                val generalMealList = ArrayList<Meal>()
                val withoutMattressMealList = ArrayList<Meal>()

                for((i, meal) in mealMap){
                    generalMealList.add(Meal(meal.mealTypeId, meal.price))
                }

                withoutMattressMealMap[key]?.let {
                    for((i, meal) in withoutMattressMealMap[key]!!){
                        withoutMattressMealList.add(Meal(meal.mealTypeId, meal.price))
                    }
                }


                room2List.add(Room2(key, generalMealList, withoutMattressMealList))
            }

            val hotelRequestData = HotelRequestData2(name, location_id, rating, address, room2List)
            hotelViewModel.addHotel(hotelRequestData)
        }else{

            for((key, mealMap) in generalMealMap){
                val generalMealList = ArrayList<Meal>()

                for((i, meal) in mealMap){
                    generalMealList.add(Meal(meal.mealTypeId, meal.price))
                }

                room1List.add(Room1(key, generalMealList))
            }

            val hotelRequestData = HotelRequestData1(name, location_id, rating, address, room1List)

            val gson = Gson()
            val json = gson.toJson(hotelRequestData)

            Log.d("TAG", json)
//            hotelViewModel.addHotel(hotelRequestData)
        }


    }

    private fun getMealList(mealMap: HashMap<String, HashMap<String, MealData>>): ArrayList<MealData>{
        val mealList = ArrayList<MealData>()
        for((key, mealMap) in mealMap){
            for((k, meal) in mealMap){
                mealList.add(meal)
            }
        }
        return mealList
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            data?.getSerializableExtra("ROOM_LIST")?.let {
                roomList.addAll(it as ArrayList<RoomData>)
                selectedRoomAdapter.notifyDataSetChanged()

            }
        }
    }

    private fun observeLocationList(){
        hotelViewModel.observeGetLocation().observe(this, {
            locationList.clear()
            locationList.addAll(it)
            if(it.isNotEmpty())
                location_id = it[0].locationId
            ad.notifyDataSetChanged()
        })
    }

    private fun observeMealList(){
        hotelViewModel.observeGetMeal().observe(this, {
            mealList.clear()
            mealList.addAll(it)
            selectedRoomAdapter.notifyDataSetChanged()
        })
    }

    private fun observeAddHotel(){
        hotelViewModel.observeAddHotel().observe(this, {
            onBackPressed()
        })
    }

    private fun observeLoader(){
        hotelViewModel.dataLoading.observe(this, {
            if(it)
                showProgress()
            else hideProgress()
        })
    }

    private fun observeToast(){
        hotelViewModel.toastMessage.observe(this, {
            showToast(it)
        })
    }

}