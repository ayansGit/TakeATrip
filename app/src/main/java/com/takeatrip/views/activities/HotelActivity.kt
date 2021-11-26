package com.takeatrip.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.graphicalab.utils.BaseActivity
import com.takeatrip.R
import com.takeatrip.adapters.HotelAdapter
import com.takeatrip.adapters.LocationAdapter
import com.takeatrip.models.hotel.Hotel
import com.takeatrip.utils.hide
import com.takeatrip.utils.show
import com.takeatrip.viewModels.HotelViewModel
import kotlinx.android.synthetic.main.activity_hotel.*
import kotlinx.android.synthetic.main.activity_hotel.fab
import kotlinx.android.synthetic.main.activity_hotel.tvEmptyText
import kotlinx.android.synthetic.main.activity_vehicle.*
import kotlinx.android.synthetic.main.header.*

class HotelActivity : BaseActivity() {

    private lateinit var hotelAdapter: HotelAdapter
    private val hotelList = ArrayList<Hotel>()
    private lateinit var hotelViewModel: HotelViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotel)
        tvTitle.text = "Hotels"
        hotelViewModel = ViewModelProvider(this).get(HotelViewModel::class.java)
        ivBack.setOnClickListener { onBackPressed() }
        hotelAdapter = HotelAdapter(this, hotelList, object : HotelAdapter.HotelAdapterListener {
            override fun deleteHotel(hotelId: String) {
                hotelViewModel.deleteHotel(hotelId)
            }

            override fun addExtraMattress(hotelId: String) {
            }

        })
        rvHotels.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvHotels.adapter = hotelAdapter

        fab.setOnClickListener {
            startActivity(Intent(this, AddHotelActivity::class.java))
        }

        observeAddHotel()
        observeDeleteHotel()
        observeLoader()
        observeToast()

    }

    override fun onResume() {
        super.onResume()
        hotelViewModel.getHotel()
    }

    private fun observeAddHotel() {
        hotelViewModel.observeGetHotel().observe(this, {
            hotelList.clear()
            hotelList.addAll(it)
            if (it.size > 0)
                tvEmptyText.hide()
            else tvEmptyText.show()
            hotelAdapter.notifyDataSetChanged()
        })
    }

    private fun observeDeleteHotel() {
        hotelViewModel.observeDeleteHotel().observe(this, {
            hotelViewModel.getHotel()
        })
    }

    private fun observeLoader() {
        hotelViewModel.dataLoading.observe(this, {
            if (it)
                showProgress()
            else hideProgress()
        })
    }

    private fun observeToast() {
        hotelViewModel.toastMessage.observe(this, {
            showToast(it)
        })
    }
}