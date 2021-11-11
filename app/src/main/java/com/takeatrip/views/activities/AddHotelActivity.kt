package com.takeatrip.views.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.graphicalab.utils.BaseActivity
import com.takeatrip.R
import com.takeatrip.adapters.HotelRoomAdapter
import kotlinx.android.synthetic.main.activity_add_hotel.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.tvTitle
import kotlinx.android.synthetic.main.header.*
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.takeatrip.adapters.MealAdapter
import com.takeatrip.adapters.SelectedRoomAdapter
import com.takeatrip.models.location.LocationData
import com.takeatrip.models.room.RoomData
import com.takeatrip.viewModels.HotelViewModel
import com.takeatrip.viewModels.LocationViewModel


class AddHotelActivity : BaseActivity() {

    private val locationList = ArrayList<LocationData>()
    private lateinit var hotelViewModel: HotelViewModel
    private lateinit var ad: ArrayAdapter<*>
    private val roomList = ArrayList<RoomData>()
    private lateinit var selectedRoomAdapter: SelectedRoomAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_hotel)
        hotelViewModel = ViewModelProvider(this).get(HotelViewModel::class.java)
        tvTitle.text = "Add Hotels"
        ivBack.setOnClickListener {
            onBackPressed()
        }
        selectedRoomAdapter = SelectedRoomAdapter(this, roomList)
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

        rcMealType.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rcMealType.adapter = MealAdapter(this)

        ivAdd.setOnClickListener {
            startActivityForResult(Intent(this, SelectRoomActivity::class.java), 1)
        }

        btAddMore.setOnClickListener {
            startActivityForResult(Intent(this, SelectRoomActivity::class.java), 1)
        }


        observeLocationList()
        observeLoader()
        observeToast()

        hotelViewModel.getLocation()

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
            ad.notifyDataSetChanged()
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