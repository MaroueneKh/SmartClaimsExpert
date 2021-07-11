package com.marouenekhadhraoui.smartclaimsexpert.ui.settings

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.data.local.Datapreferences
import com.marouenekhadhraoui.smartclaimsexpert.network.NetworkHelper
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.DossierRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.M)
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dossierRepository: DossierRepository,
    var networkHelper: NetworkHelper,
    var logger: Logger,
    private val apprefs: Datapreferences
) : ViewModel(),
    LifecycleObserver {
    val isdarkenabled = apprefs.darkmode.asLiveData()


    fun saveMode(context: Context, value: Boolean) {
        viewModelScope.launch {
            try {
                apprefs.setMode(context, value)

            } catch (exception: Exception) {
                logger.log("catch ")
                logger.log(exception.message.toString())

            }
        }
    }

}