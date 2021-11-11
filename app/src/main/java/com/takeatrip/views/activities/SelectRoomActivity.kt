package com.takeatrip.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.graphicalab.utils.BaseActivity
import com.takeatrip.R
import com.takeatrip.adapters.HotelRoomAdapter
import com.takeatrip.models.room.RoomData
import com.takeatrip.viewModels.RoomViewModel
import kotlinx.android.synthetic.main.fragment_room.*
import kotlinx.android.synthetic.main.header.*
import android.app.Activity

import android.content.Intent




class SelectRoomActivity : BaseActivity() {

    private lateinit var roomViewModel: RoomViewModel
    var selectedRooms = HashMap<String, RoomData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_room)
        ivBack.setOnClickListener { onBackPressed() }
        tvTitle.text = "Select Rooms"
        roomViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)

        fab.setOnClickListener {
            val selectedRoomArrayList = ArrayList<RoomData>()
            for ((key, value) in selectedRooms) {
                selectedRoomArrayList.add(value)
            }
            val returnIntent = Intent()
            returnIntent.putExtra("ROOM_LIST", selectedRoomArrayList)
            setResult(RESULT_OK, returnIntent)
            finish()
        }

        observeRooms()
        observeLoader()
        observeToast()
    }

    override fun onBackPressed() {
        val returnIntent = Intent()
        setResult(RESULT_CANCELED, returnIntent)
        finish()
    }

    private fun observeRooms(){
        roomViewModel.observeGetRoom().observe(this, {
            rvRooms.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            rvRooms.adapter = HotelRoomAdapter(this, true, it as ArrayList<RoomData>){ rooms ->
                selectedRooms = rooms
            }
        })
    }

    private fun observeLoader(){
        roomViewModel.dataLoading.observe(this, {
            if(it)
                showProgress()
            else hideProgress()
        })
    }

    private fun observeToast(){
        roomViewModel.toastMessage.observe(this, {
            showToast(it)
        })
    }

    override fun onResume() {
        super.onResume()
        roomViewModel.getRoom()
    }
}