package com.takeatrip.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.graphicalab.utils.BaseActivity
import com.takeatrip.R
import com.takeatrip.viewModels.RoomViewModel
import kotlinx.android.synthetic.main.activity_add_room.*
import kotlinx.android.synthetic.main.header.*

class AddRoomActivity : BaseActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var roomViewModel: RoomViewModel
    private val extraBeds = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
    private var allowExtraBed = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_room)
        tvTitle.text = "Add Room"
        roomViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)

        ivBack.setOnClickListener { onBackPressed() }

        val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this,
            android.R.layout.simple_spinner_item,
            extraBeds
        )
        ad.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        capacitySpinner.adapter = ad
        capacitySpinner.onItemSelectedListener = this

        btSubmit.setOnClickListener {
            if(etRoomName.text.toString().trim().isEmpty()){
                showToast("Enter room type")
            }else{
                roomViewModel.addRoom(etRoomName.text.toString().trim(), allowExtraBed)
            }
        }

        observeAddRoom()
        observeLoader()
        observeToast()


    }



    private fun observeAddRoom(){
        roomViewModel.observeAddRoom().observe(this, {
            onBackPressed()
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

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        allowExtraBed = extraBeds[p2].toInt()
        Log.d("TAG", allowExtraBed.toString())
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}