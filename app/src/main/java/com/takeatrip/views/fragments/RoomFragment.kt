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
import com.takeatrip.adapters.HotelRoomAdapter
import com.takeatrip.models.room.RoomData
import com.takeatrip.utils.hide
import com.takeatrip.viewModels.RoomViewModel
import com.takeatrip.views.activities.AddRoomActivity
import kotlinx.android.synthetic.main.fragment_room.*
import kotlinx.android.synthetic.main.header.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RoomFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RoomFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var roomViewModel: RoomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_room, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivBack.hide()
        tvTitle.text = "Rooms"
        roomViewModel = ViewModelProvider(this).get(RoomViewModel::class.java)


        fab.setOnClickListener {
            startActivity(Intent(requireContext(), AddRoomActivity::class.java))
        }

        observeRooms()
        observeLoader()
        observeToast()

    }

    override fun onResume() {
        super.onResume()
        roomViewModel.getRoom()
    }

    private fun observeRooms(){
        roomViewModel.observeGetRoom().observe(viewLifecycleOwner, {
            rvRooms.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            rvRooms.adapter = HotelRoomAdapter(requireContext(), false, it as ArrayList<RoomData>){

            }
        })
    }

    private fun observeLoader(){
        roomViewModel.dataLoading.observe(viewLifecycleOwner, {
            if(it)
                showProgress()
            else hideProgress()
        })
    }

    private fun observeToast(){
        roomViewModel.toastMessage.observe(viewLifecycleOwner, {
            showToast(it)
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RoomFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RoomFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}