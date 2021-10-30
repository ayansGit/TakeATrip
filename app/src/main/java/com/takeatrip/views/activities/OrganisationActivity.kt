package com.takeatrip.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.graphicalab.utils.BaseActivity
import com.takeatrip.R
import com.takeatrip.utils.hide
import com.takeatrip.utils.isEmailValid
import com.takeatrip.viewModels.OrganisationViewModel
import kotlinx.android.synthetic.main.activity_organisation.*
import kotlinx.android.synthetic.main.activity_organisation.btSubmit
import kotlinx.android.synthetic.main.activity_organisation.etMobile
import kotlinx.android.synthetic.main.header.*
import kotlinx.android.synthetic.main.header.tvTitle

class OrganisationActivity : BaseActivity() {

    private lateinit var organisationViewModel: OrganisationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organisation)
        organisationViewModel = ViewModelProvider(this).get(OrganisationViewModel::class.java)
        tvTitle.text = getString(R.string.organisaton)
        ivBack.hide()
        btSubmit.setOnClickListener {
            if(etOrgName.text.toString().trim().isEmpty()){
                etOrgName.error = "Please enter organisation name."
            }else if(etOrgEmail.text.toString().trim().isEmpty()){
                etOrgEmail.error = "Please enter organisation email."
            }else if(!isEmailValid(etOrgEmail.text.toString().trim())){
                etOrgEmail.error = "Please enter a valid email."
            }else if(etMobile.text.toString().trim().isEmpty()){
                etMobile.error = "Please enter organisation phone number."
            }else if(etMobile.text.toString().trim().length < 10){
                etMobile.error = "Please enter a valid phone number."
            }else if(etAddress.text.toString().trim().isEmpty()){
                etAddress.error = "Address cannot be empty."
            }else{
                organisationViewModel.addOrganisation(etOrgName.text.toString().trim(), etOrgEmail.text.toString().trim(),
                    etMobile.text.toString().trim(), etAddress.text.toString().trim())
            }
        }

        observeAddOrganisation()
        observeLoader()
        observeToast()

    }

    private fun observeAddOrganisation(){
        organisationViewModel.observeAddOrganisation().observe(this, {
            startActivity(Intent(this, MainActivity::class.java))
        })
    }

    private fun observeLoader(){
        organisationViewModel.dataLoading.observe(this, {
            if(it)
                showProgress()
            else hideProgress()
        })
    }

    private fun observeToast(){
        organisationViewModel.toastMessage.observe(this, {
            showToast(it)
        })
    }
}