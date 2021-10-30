package com.takeatrip.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.takeatrip.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.tvTitle
import kotlinx.android.synthetic.main.header.*

class AddLocationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_location)
        tvTitle.text = "Add Location"
        ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}