package com.example.bitlife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        val logFragment = LogListFragment()
        val dashboardFragment = DashboardFragment()

        // Display LogListFragment by default on launch
        if (savedInstanceState == null) {
            replaceFragment(logFragment)
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_log -> {
                    replaceFragment(logFragment)
                    true
                }
                R.id.nav_dashboard -> {
                    replaceFragment(dashboardFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .commit()
    }
}
