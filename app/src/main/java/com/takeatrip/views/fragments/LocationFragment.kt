package com.takeatrip.views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.graphicalab.utils.BaseFragment
import com.takeatrip.R
import com.takeatrip.adapters.LocationAdapter
import com.takeatrip.models.location.LocationData
import com.takeatrip.utils.hide
import com.takeatrip.viewModels.LocationViewModel
import com.takeatrip.views.activities.AddLocationActivity
import kotlinx.android.synthetic.main.fragment_location.*
import kotlinx.android.synthetic.main.header.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LocationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LocationFragment : BaseFragment() {

    private lateinit var locationAdapter: LocationAdapter
    private val locationList = ArrayList<LocationData>()
    private lateinit var locationViewModel: LocationViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        ivBack.hide()
        tvTitle.text = "Locations"
        locationAdapter = LocationAdapter(requireContext(), locationList)
        rvLocations.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvLocations.adapter = locationAdapter

        fab.setOnClickListener {
            startActivity(Intent(requireContext(), AddLocationActivity::class.java))
        }

        observeLocationList()
        observeLoader()
        observeToast()


    }

    private fun observeLocationList(){
        locationViewModel.observeGetLocation().observe(viewLifecycleOwner, {
            locationList.clear()
            locationList.addAll(it)
            locationAdapter.notifyDataSetChanged()
        })
    }

    private fun observeLoader(){
        locationViewModel.dataLoading.observe(viewLifecycleOwner, {
            if(it)
                showProgress()
            else hideProgress()
        })
    }

    private fun observeToast(){
        locationViewModel.toastMessage.observe(viewLifecycleOwner, {
            showToast(it)
        })
    }

    override fun onResume() {
        super.onResume()
        locationViewModel.getLocation()
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LocationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = LocationFragment()
    }
}