package com.takeatrip.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.takeatrip.R
import com.takeatrip.views.fragments.AccountFragment
import com.takeatrip.views.fragments.DashboardFragment
import com.takeatrip.views.fragments.LocationFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnItemSelectedListener{

            when(it.itemId){

                R.id.dashboard -> {
                    val dashboardFragment = DashboardFragment()
                    loadFragment(dashboardFragment, "DASHBOARD")
                }

                R.id.location -> {
                    val downloadFragment = LocationFragment()
                    loadFragment(downloadFragment, "LOCATION")
                }

                R.id.account -> {
                    val accountFragment = AccountFragment()
                    loadFragment(accountFragment, "ACCOUNT")
                }
            }

            return@setOnItemSelectedListener true
        }

        val dashboardFragment = DashboardFragment()
        loadFragment(dashboardFragment, "DASHBOARD")
    }



    private fun loadFragment(fragment: Fragment, tag: String) {
        // load fragment
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}