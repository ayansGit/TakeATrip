package com.takeatrip.views.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.takeatrip.R
import kotlinx.android.synthetic.main.activity_travel_plan.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.graphicalab.utils.BaseActivity
import java.util.*
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.takeatrip.adapters.AddHotelRoomAdapter
import com.takeatrip.models.location.LocationData
import com.takeatrip.models.meal.MealData
import com.takeatrip.models.room.RoomData
import com.takeatrip.utils.hide
import com.takeatrip.utils.show
import com.takeatrip.viewModels.TravelPlanViewModel
import kotlinx.android.synthetic.main.activity_add_hotel.*
import kotlinx.android.synthetic.main.activity_travel_plan.daySpinner
import kotlinx.android.synthetic.main.activity_travel_plan.locationSpinner
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.collections.HashMap


class TravelPlanActivity : BaseActivity(), AddHotelRoomAdapter.SelectedRoomListener {

    val daysList = ArrayList<String>()
    val roomList = ArrayList<RoomData>()
    val mealList = ArrayList<MealData>()
    private val locationList = ArrayList<LocationData>()
    lateinit var dayAdapter:  ArrayAdapter<String>
    private lateinit var ad: ArrayAdapter<*>
    var startDate: String = ""
    var endDate: String = ""
    private lateinit var travelPlanViewModel: TravelPlanViewModel
    private lateinit var addHotelRoomAdapter: AddHotelRoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_plan)

        travelPlanViewModel = ViewModelProvider(this).get(TravelPlanViewModel::class.java)
        dayAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            daysList
        )
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        daySpinner.adapter = dayAdapter

        ad = ArrayAdapter<LocationData>(
            this,
            android.R.layout.simple_spinner_item,
            locationList
        )
        ad.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        locationSpinner.adapter = ad

        etStartDate.setOnClickListener {
            showCalendar(etStartDate, (System.currentTimeMillis() - 1000)){
                val myFormat = "dd/MM/yyyy" //In which you need put here
                val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
                startDate = sdf.format(it)

                etEndDate.setText("")
                daySpinner.hide()
                tvSelectDay.hide()
                margin2.hide()
                tvSelectHotel.hide()
                hotelSpinner.hide()
                llHotel.hide()
                margin3.hide()
                llTransport.hide()

            }
        }

        etEndDate.setOnClickListener {
            if(etStartDate.text.toString().isEmpty()){
                showToast("Enter start date")
            }else {
                val startDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(etStartDate.text.toString())
                val calendar = Calendar.getInstance()
                calendar.time = startDate
                calendar.add(Calendar.DATE, 1)
                showCalendar(etEndDate, calendar.timeInMillis){
                    val myFormat = "dd/MM/yyyy" //In which you need put here
                    val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
                    endDate = sdf.format(it)

                    val startDate = sdf.parse(this.startDate)

                    val diff = it.time - startDate.time
                    val dayCount = diff.toFloat() / (24 * 60 * 60 * 1000) +1f
                    for(i in 1..dayCount.toInt()){
                        daysList.add(i.toString())
                    }
                    daySpinner.show()
                    tvSelectDay.show()
                    margin2.show()
                    tvSelectHotel.show()
                    hotelSpinner.show()
                    llHotel.show()
                    margin3.show()
                    llTransport.show()
                    dayAdapter.notifyDataSetChanged()
                    showToast(dayCount.toInt().toString())
                }
            }
        }

        rvRooms.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        addHotelRoomAdapter = AddHotelRoomAdapter(this, roomList, mealList, this)
        rvRooms.adapter = addHotelRoomAdapter


        observeLocationList()
        observeMealList()
        observeLoader()
        observeToast()

        travelPlanViewModel.getLocation()
        travelPlanViewModel.getMeal()
    }

    private fun showCalendar(inputField: EditText, minDate: Long, onDateSet: (date: Date) -> Unit) {
        val myCalendar = Calendar.getInstance();
        val date =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                val myFormat = "dd/MM/yyyy" //In which you need put here
                val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
                inputField.setText(sdf.format(myCalendar.time))
                onDateSet(myCalendar.time)
            }

        val dpDialog = DatePickerDialog(
            this, date, myCalendar[Calendar.YEAR],
            myCalendar[Calendar.MONTH],
            myCalendar[Calendar.DAY_OF_MONTH]
        )

        dpDialog.datePicker.minDate = minDate

        dpDialog.show()
    }

    override fun onMealSelected(mealMap: HashMap<String, MealData>) {
    }

    private fun observeLocationList(){
        travelPlanViewModel.observeGetLocation().observe(this, {
            locationList.clear()
            locationList.addAll(it)
            ad.notifyDataSetChanged()
        })
    }

    private fun observeMealList(){
        travelPlanViewModel.observeGetMeal().observe(this, {
            mealList.clear()
            mealList.addAll(it)
            addHotelRoomAdapter.notifyDataSetChanged()
        })
    }

    private fun observeLoader(){
        travelPlanViewModel.dataLoading.observe(this, {
            if(it)
                showProgress()
            else hideProgress()
        })
    }

    private fun observeToast(){
        travelPlanViewModel.toastMessage.observe(this, {
            showToast(it)
        })
    }
}