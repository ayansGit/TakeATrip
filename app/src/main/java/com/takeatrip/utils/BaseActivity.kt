package com.graphicalab.utils

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.takeatrip.utils.ProgressLoader

open class BaseActivity: AppCompatActivity() {

    private lateinit var progressLoader: ProgressLoader

    fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun showProgress() {
        progressLoader = ProgressLoader(this)
        progressLoader.show()
    }

    fun hideProgress() {
        if(::progressLoader.isInitialized)
            progressLoader.dismiss()
    }

}