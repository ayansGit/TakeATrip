package com.takeatrip.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.takeatrip.R
import com.takeatrip.adapters.HotelAdapter
import com.takeatrip.adapters.LocationAdapter
import kotlinx.android.synthetic.main.activity_hotel.*
import kotlinx.android.synthetic.main.header.*

class HotelActivity : AppCompatActivity() {

    private lateinit var hotelAdapter: HotelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel)
        tvTitle.text = "Hotels"
        ivBack.setOnClickListener { onBackPressed() }

        hotelAdapter = HotelAdapter(this)
        rvHotels.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvHotels.adapter = hotelAdapter
    }
}