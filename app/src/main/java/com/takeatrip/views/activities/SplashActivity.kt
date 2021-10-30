package com.takeatrip.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.takeatrip.R
import com.takeatrip.utils.StoragePreference

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            StoragePreference.getIsOrganisationAdded(this)?.also{
                if(it.equals("No", ignoreCase = true)){
                    startActivity(Intent(this, LoginActivity::class.java))
                }else{
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }

        }, 2000)
    }
}