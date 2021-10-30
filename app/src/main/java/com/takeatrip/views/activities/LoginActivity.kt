package com.takeatrip.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.graphicalab.utils.BaseActivity
import com.takeatrip.R
import com.takeatrip.utils.isEmailValid
import com.takeatrip.viewModels.AuthViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        tvSignUp.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java)) }

        btSubmit.setOnClickListener {
            if(etEmail.text.toString().trim().isEmpty()){
                etEmail.error = "Email cannot be empty."
            }else if(!isEmailValid(etEmail.text.toString().trim())){
                etEmail.error = "Please enter a valid email."
            }else if(etPassword.text.toString().trim().isEmpty()){
                etPassword.error = "Password cannot be empty."
            }else if(etPassword.text.toString().trim().length <6){
                etPassword.error = "Password should be more that 5 character."
            }else{
                authViewModel.login(etEmail.text.toString().trim(), etPassword.text.toString().trim())
            }
        }

        observeLogin()
        observeLoader()
        observeToast()

    }

    private fun observeLogin(){
        authViewModel.observeLogin().observe(this, {
            if(it.isOrganisationAdded.equals("No", ignoreCase = true)){
                startActivity(Intent(this, OrganisationActivity::class.java))
            }else {
                startActivity(Intent(this, MainActivity::class.java))
            }
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