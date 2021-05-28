package com.marouenekhadhraoui.smartclaimsexpert

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

import javax.inject.Inject

@HiltAndroidApp
class SmartClaimsExpertApplication : Application() {
    @Inject
    lateinit var logger: Logger

    override fun onCreate() {
        super.onCreate()
        logger.configure()

    }
}