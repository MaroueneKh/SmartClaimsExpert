package com.marouenekhadhraoui.smartclaimsexpert.ui.splash

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.marouenekhadhraoui.smartclaimsexpert.R
import com.marouenekhadhraoui.smartclaimsexpert.databinding.ActivitySecondSplashBinding
import com.marouenekhadhraoui.smartclaimsexpert.ui.main.MainActivity
import com.marouenekhadhraoui.smartclaimsexpert.ui.signin.SignInActivity
import com.marouenekhadhraoui.smartclaimsexpert.utils.TO_SIGNIN_OR_SIGNUP
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondSplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondSplashBinding
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        bindViewModel()
        subscribeToData()

    }

    private fun subscribeToData() {
        val connObserver = Observer<Boolean> { connected ->
            // Update the UI, in this case, a TextView.
            if (connected)
               startActivity(MainActivity())
            else
                startActivity(SignInActivity())
        }
        splashViewModel.isConnected.observe(this, connObserver)

        val darkObserver = Observer<Boolean> { darkmode ->
            val defaultMode = if (darkmode) {
                AppCompatDelegate.MODE_NIGHT_NO
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(defaultMode)
        }
        splashViewModel.isdarkenabled.observe(this, darkObserver)
        /*  splashViewModel.isdarkenabled.observe(this) { darkmode ->
              val defaultMode = if (darkmode) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
                AppCompatDelegate.setDefaultNightMode(defaultMode)
            }
          */
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second_splash)
        binding.lifecycleOwner = this
    }

    fun bindViewModel() {
        binding.viewModelSplash = splashViewModel
    }

    fun startActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        intent.action = TO_SIGNIN_OR_SIGNUP
        startActivity(intent)
        finish()
    }


}