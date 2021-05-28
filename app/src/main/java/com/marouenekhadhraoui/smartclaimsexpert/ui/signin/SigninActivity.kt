package com.marouenekhadhraoui.smartclaimsexpert.ui.signin

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.R
import com.marouenekhadhraoui.smartclaimsexpert.databinding.ActivitySigninBinding
import com.marouenekhadhraoui.smartclaimsexpert.ui.main.MainActivity
import com.marouenekhadhraoui.smartclaimsexpert.utils.Status
import com.marouenekhadhraoui.smartclaimsexpert.utils.TO_SIGNIN_OR_SIGNUP
import com.marouenekhadhraoui.smartclaimsexpert.utils.fadeTo
import com.marouenekhadhraoui.smartclaimsexpert.utils.internetErr
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private val signInViewModel: SignInViewModel by viewModels()

    @Inject
    lateinit var logger: Logger


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setupNavigation()
        setuplogin()
        bindViewModel()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setuplogin() {
        signInViewModel.pressBtnSeConnecterEvent.observe(this, Observer {
            observeAssure()
        })
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun observeAssure() {
        signInViewModel.assure.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    logger.log("success")
                    if (it.data!!.isEmpty()) {
                        logger.log("null")
                        binding.warning.fadeTo(true)
                    } else {
                        signInViewModel.saveToken(this, it.data[0].token)
                        signInViewModel.saveConnected(this)
                        logger.log("not null")
                        binding.warning.fadeTo(false)
                        startActivity(MainActivity())

                    }

                }
                Status.LOADING -> {
                    logger.log("loading")
                }
                Status.ERROR -> {
                    logger.log("error")
                    if (it.message.equals(internetErr)) {
                        logger.log("internet error")
                        Snackbar.make(findViewById(R.id.constraint), "Verifier votre connexion", Snackbar.LENGTH_LONG).show()
                    }

                }
            }
        })

    }

    private fun setupNavigation() {
        signInViewModel.pressBtnSinscrireEvent.observe(this, Observer {
            it?.let {
             //   startActivity(SignUPActivity())
            }
        })

        signInViewModel.ressLinkoublieEvent.observe(this, Observer {
            it?.let {
             //   startActivity(RecuperationMotDePasseActivity())
            }
        })
    }


    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signin)
        binding.lifecycleOwner = this
    }

    fun bindViewModel() {
        binding.viewModel = signInViewModel
    }

    fun startActivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        intent.action = TO_SIGNIN_OR_SIGNUP
        startActivity(intent)
        finish()

    }

}