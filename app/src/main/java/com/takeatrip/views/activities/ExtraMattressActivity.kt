package com.takeatrip.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.graphicalab.utils.BaseActivity
import com.takeatrip.R
import com.takeatrip.adapters.HotelRoomAdapter
import com.takeatrip.adapters.MealAdapter
import com.takeatrip.models.hotel.addHotelRequest.ExtraMattressRequestData
import com.takeatrip.models.hotel.addHotelRequest.Meal
import com.takeatrip.models.location.LocationData
import com.takeatrip.models.meal.MealData
import com.takeatrip.models.room.RoomData
import com.takeatrip.utils.hide
import com.takeatrip.utils.show
import com.takeatrip.viewModels.ExtraMattressViewModel
import kotlinx.android.synthetic.main.activity_extra_mattress.*
import kotlinx.android.synthetic.main.header.*

class ExtraMattressActivity : BaseActivity() {

    private lateinit var extraMattressViewModel: ExtraMattressViewModel
    private lateinit var ad: ArrayAdapter<*>
    private val roomList = ArrayList<RoomData>()
    private lateinit var without_mattress_meal_adapter: MealAdapter
    private lateinit var with_mattress_meal_adapter: MealAdapter
    private val mealList1 = ArrayList<MealData>()
    private val mealList2 = ArrayList<MealData>()

    private val mealWithMattress: ArrayList<Meal> = ArrayList()
    private val mealWithoutMattress: ArrayList<Meal> = ArrayList()

    private var mealWithoutMattressMap: HashMap<String, MealData> = HashMap()
    private var mealWithMattressMap: HashMap<String, MealData> = HashMap()

    private var roomId: String = ""
    private var hotelId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extra_mattress)

        extraMattressViewModel = ViewModelProvider(this).get(ExtraMattressViewModel::class.java)
        ad = ArrayAdapter<RoomData>(
            this,
            android.R.layout.simple_spinner_item,
            roomList
        )
        ad.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        roomSpinner.adapter = ad
        roomSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                roomId = roomList[p2].roomTypeId
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        rcMealType2.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        with_mattress_meal_adapter = MealAdapter(this, mealList1) {
            mealWithMattressMap = it
        }
        rcMealType2.adapter = with_mattress_meal_adapter

        rcMealType3.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        without_mattress_meal_adapter = MealAdapter(this, mealList2) {
            mealWithoutMattressMap = it
        }
        rcMealType3.adapter = without_mattress_meal_adapter

        hotelId = intent.getStringExtra("HOTEL_ID").toString()

        extraMattressViewModel.getRoom(hotelId)

        extraMattressViewModel.getMeal()

        cb1.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                rcMealType2.show()
            } else rcMealType2.hide()
        }

        cb2.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                rcMealType3.show()
            } else rcMealType3.hide()
        }

        btSubmit.setOnClickListener {
            submit()
        }

        ivBack.setOnClickListener {
            onBackPressed()
        }

        observeRooms()
        observerAddExtraMattress()
        observeMeal()
        observeLoader()
        observeToast()
    }

    private fun submit() {
        for ((k, v) in mealWithMattressMap) {
            mealWithMattress.add(Meal(k, v.price))
        }

        for ((k, v) in mealWithoutMattressMap) {
            mealWithoutMattress.add(Meal(k, v.price))
        }

        val extraMattressRequestData = ExtraMattressRequestData(hotelId, roomId, mealWithMattress, mealWithoutMattress)

        val json = Gson().toJson(extraMattressRequestData)

        Log.d("TAG", json)

        extraMattressViewModel.addExtraMattress(extraMattressRequestData)
    }

    private fun observeRooms() {
        extraMattressViewModel.observeGetRoom().observe(this, {
            roomList.clear()
            roomList.addAll(it)
            ad.notifyDataSetChanged()
        })
    }

    private fun observerAddExtraMattress(){
        extraMattressViewModel.observeAddExtraMattress().observe(this, {
            finish()
        })
    }

    private fun observeMeal() {
        extraMattressViewModel.observeGetMeal().observe(this, {

            mealList1.clear()
            mealList2.clear()

            mealList1.addAll(it)
            mealList2.addAll(it)

            with_mattress_meal_adapter.notifyDataSetChanged()
            without_mattress_meal_adapter.notifyDataSetChanged()
        })
    }

    private fun observeLoader() {
        extraMattressViewModel.dataLoading.observe(this, {
            if (it)
                showProgress()
            else hideProgress()
        })
    }

    private fun observeToast() {
        extraMattressViewModel.toastMessage.observe(this, {
            showToast(it)
        })
    }
}