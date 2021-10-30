package com.graphicalab.utils

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.takeatrip.utils.ProgressLoader

open class BaseFragment: Fragment() {

    private lateinit var progressLoader: ProgressLoader

    fun showToast(message: String){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showProgress() {
        context?.let {
            progressLoader = ProgressLoader(it)
            progressLoader.setCancelable(false)
            progressLoader.setCanceledOnTouchOutside(false)
            progressLoader.show()
        }

    }

    fun hideProgress() {
        if(::progressLoader.isInitialized)
            progressLoader.dismiss()
    }
}