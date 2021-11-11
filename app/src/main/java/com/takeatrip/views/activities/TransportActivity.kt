package com.takeatrip.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import com.graphicalab.utils.BaseActivity
import com.takeatrip.R
import com.takeatrip.models.location.LocationData
import com.takeatrip.viewModels.LocationViewModel
import kotlinx.android.synthetic.main.activity_add_transport.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.tvTitle
import kotlinx.android.synthetic.main.activity_vehicle.*
import kotlinx.android.synthetic.main.activity_vehicle.daySpinner
import kotlinx.android.synthetic.main.activity_vehicle.locationSpinner
import kotlinx.android.synthetic.main.header.*

class TransportActivity : BaseActivity() {

    private val locationList = ArrayList<LocationData>()
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var locationAdapter: ArrayAdapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle)

        fab.setOnClickListener {
            startActivity(Intent(this, AddTransportActivity::class.java))
        }
        ivBack.setOnClickListener { onBackPressed() }

        tvTitle.text = "Transport"

        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)

        locationAdapter = ArrayAdapter<LocationData>(
            this,
            android.R.layout.simple_spinner_item,
            locationList
        )

        locationAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        locationSpinner.adapter = locationAdapter

        val dayAdapter = ArrayAdapter<Any>(
            this,
            android.R.layout.simple_spinner_item,
            arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        )
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        daySpinner.adapter = dayAdapter

        observeLocationList()
        observeLoader()
        observeToast()

        locationViewModel.getLocation()

    }

    private fun observeLocationList(){
        locationViewModel.observeGetLocation().observe(this, {
            locationList.clear()
            locationList.addAll(it)
            locationAdapter.notifyDataSetChanged()
        })
    }

    private fun observeLoader(){
        locationViewModel.dataLoading.observe(this, {
            if(it)
                showProgress()
            else hideProgress()
        })
    }

    private fun observeToast(){
        locationViewModel.toastMessage.observe(this, {
            showToast(it)
        })
    }
}