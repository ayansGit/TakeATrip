package com.takeatrip.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.graphicalab.utils.BaseActivity
import com.takeatrip.R
import com.takeatrip.viewModels.LocationViewModel
import kotlinx.android.synthetic.main.activity_add_location.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.btSubmit
import kotlinx.android.synthetic.main.activity_login.tvTitle
import kotlinx.android.synthetic.main.header.*

class AddLocationActivity : BaseActivity() {

    private lateinit var locationViewModel: LocationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_location)
        tvTitle.text = "Add Location"
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)

        ivBack.setOnClickListener {
            onBackPressed()
        }

        btSubmit.setOnClickListener {
            if(etPlace.text.toString().trim().isEmpty()){
                showToast("Enter name of the place")
            }else if(etState.text.toString().trim().isEmpty()){
                showToast("Enter state or country name")
            }else{
                locationViewModel.addLocation(etPlace.text.toString().trim(), etState.text.toString().trim())
            }
        }

        observerAddLocation()
        observeLoader()
        observeToast()

    }

    private fun observerAddLocation(){
        locationViewModel.observeAddLocation().observe(this, {
            onBackPressed()
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