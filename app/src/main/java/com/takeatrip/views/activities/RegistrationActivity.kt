package com.takeatrip.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.graphicalab.utils.BaseActivity
import com.takeatrip.R
import com.takeatrip.utils.isEmailValid
import com.takeatrip.viewModels.AuthViewModel
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.header.*

class RegistrationActivity : BaseActivity() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        btSubmit.setOnClickListener {
            if(etEmail.text.toString().trim().isEmpty()){
                etEmail.error = "Email cannot be empty."
            }else if(!isEmailValid(etEmail.text.toString().trim())){
                etEmail.error = "Please enter a valid email."
            }else if(etFullName.text.toString().trim().isEmpty()){
                etFullName.error = "Please enter your full name."
            }else if(etMobile.text.toString().trim().isEmpty()){
                etMobile.error = "Please enter your mobile number."
            }else if(etMobile.text.toString().trim().length < 10){
                etMobile.error = "Please enter a valid mobile number."
            }else if(etPassword.text.toString().trim().isEmpty()){
                etPassword.error = "Password cannot be empty."
            }else if(etPassword.text.toString().trim().length <6){
                etPassword.error = "Password should be more that 5 character."
            }else{
                authViewModel.register(etEmail.text.toString().trim(), etFullName.text.toString().trim(),
                    etMobile.text.toString().trim(), etPassword.text.toString().trim())
            }
        }
        ivBack.setOnClickListener {
            onBackPressed()
        }

        observeRegister()
        observeLoader()
        observeToast()
    }

    private fun observeRegister(){
        authViewModel.observeRegistration().observe(this, {
            startActivity(Intent(this, OrganisationActivity::class.java))
        })
    }

    private fun observeLoader(){
        authViewModel.dataLoading.observe(this, {
            if(it)
                showProgress()
            else hideProgress()
        })
    }

    private fun observeToast(){
        authViewModel.toastMessage.observe(this, {
            showToast(it)
        })
    }
}