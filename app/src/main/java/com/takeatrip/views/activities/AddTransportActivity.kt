package com.takeatrip.views.activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.graphicalab.utils.BaseActivity
import com.takeatrip.R
import com.takeatrip.models.location.LocationData
import com.takeatrip.viewModels.LocationViewModel
import com.takeatrip.viewModels.TransportViewModel
import kotlinx.android.synthetic.main.activity_add_transport.*
import kotlinx.android.synthetic.main.header.*

class AddTransportActivity : BaseActivity(), AdapterView.OnItemSelectedListener  {

    private val locationList = ArrayList<LocationData>()
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var transportViewModel: TransportViewModel
    private lateinit var locationAdapter: ArrayAdapter<*>

    private var locationId = ""
    private var day = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transport)
        tvTitle.text = "Add Transport"
        ivBack.setOnClickListener { onBackPressed() }

        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        transportViewModel = ViewModelProvider(this).get(TransportViewModel::class.java)

        locationAdapter = ArrayAdapter<LocationData>(
            this,
            android.R.layout.simple_spinner_item,
            locationList
        )

        locationAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        locationSpinner.adapter = locationAdapter
        locationSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                locationId = locationList[p2].locationId
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        val dayAdapter = ArrayAdapter<Any>(
            this,
            android.R.layout.simple_spinner_item,
            arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        )
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        nightSpinner.adapter = dayAdapter
        nightSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                day = p2+1
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        btSubmit.setOnClickListener {

            submit()
        }

        observeLocationList()
        observeAddTransport()
        observeLoader()
        observeToast()

        locationViewModel.getLocation()
    }

    private fun submit(){
        when {
            etPlan.text.toString().trim().isEmpty() -> {
                showToast("Enter travel plan")
            }
            etPrice.text.toString().trim().isEmpty() -> {
                showToast("Enter travel price")
            }
            else -> {
                transportViewModel.addTransport(locationId, day, etPlan.text.toString().trim(),
                    etPrice.text.toString().trim(), etMiscellaneous.text.toString().trim())
            }
        }
    }

    private fun observeAddTransport(){
        transportViewModel.observeAddTransport().observe(this, {
            etPlan.setText("")
            etPrice.setText("")
            etMiscellaneous.setText("")
            locationSpinner.setSelection(0)
            nightSpinner.setSelection(0)
        })
    }

    private fun observeLocationList(){
        locationViewModel.observeGetLocation().observe(this, {
            locationList.clear()
            locationList.addAll(it)
            if(it.size>0)
                locationId = it[0].locationId
            locationAdapter.notifyDataSetChanged()
        })
    }

    private fun observeLoader(){
        transportViewModel.dataLoading.observe(this, {
            if(it)
                showProgress()
            else hideProgress()
        })
    }

    private fun observeToast(){
        transportViewModel.toastMessage.observe(this, {
            showToast(it)
        })
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}