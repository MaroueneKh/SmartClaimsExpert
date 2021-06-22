package com.marouenekhadhraoui.smartclaimsexpert.ui.main


import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.R
import com.marouenekhadhraoui.smartclaimsexpert.utils.TO_VISIO_FRAGMENT
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit  var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    @Inject
    lateinit var logger: Logger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
         navController = findNavController(R.id.nav_host_fragment)
        if (intent.action.equals(TO_VISIO_FRAGMENT))
        {
            logger.log("navigationTO")
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_videoVisioFragment)
        }
        else
        navView.setupWithNavController(navController)

    }

    override fun onResume() {
        super.onResume()
        if (intent.action.equals(TO_VISIO_FRAGMENT))
        {
            logger.log("navigationTO")

            findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_videoVisioFragment)
        }
    }
}
