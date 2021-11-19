package com.takeatrip.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.graphicalab.utils.BaseActivity
import com.takeatrip.R
import com.takeatrip.adapters.TransportAdapter
import com.takeatrip.models.location.LocationData
import com.takeatrip.models.transport.Transport
import com.takeatrip.models.transport.TransportLocation
import com.takeatrip.viewModels.LocationViewModel
import com.takeatrip.viewModels.TransportViewModel
import kotlinx.android.synthetic.main.activity_add_transport.*
import kotlinx.android.synthetic.main.activity_login.tvTitle
import kotlinx.android.synthetic.main.activity_vehicle.*
import kotlinx.android.synthetic.main.activity_vehicle.locationSpinner
import kotlinx.android.synthetic.main.header.*

class TransportActivity : BaseActivity() {

    private val locationList = ArrayList<TransportLocation>()
    private val transportList = ArrayList<Transport>()
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var transportViewModel: TransportViewModel
    private lateinit var locationAdapter: ArrayAdapter<*>
    private lateinit var transportAdapter: TransportAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle)

        fab.setOnClickListener {
            startActivity(Intent(this, AddTransportActivity::class.java))
        }
        ivBack.setOnClickListener { onBackPressed() }

        tvTitle.text = "Transport"

        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        transportViewModel = ViewModelProvider(this).get(TransportViewModel::class.java)

        transportAdapter = TransportAdapter(this, transportList)
        rvTransport.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvTransport.adapter = transportAdapter

        locationAdapter = ArrayAdapter<TransportLocation>(
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
                transportList.clear()
                transportList.addAll(locationList[p2].transports)
                transportAdapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        /*val dayAdapter = ArrayAdapter<Any>(
            this,
            android.R.layout.simple_spinner_item,
            arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        )
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        daySpinner.adapter = dayAdapter*/

        observeGetTransport()
        observeLoader()
        observeToast()

    }

    private fun observeGetTransport(){
        transportViewModel.observeGetTransport().observe(this, {
            locationList.clear()
            locationList.addAll(it)
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

    override fun onResume() {
        super.onResume()
        transportViewModel.getTransport()
    }
}