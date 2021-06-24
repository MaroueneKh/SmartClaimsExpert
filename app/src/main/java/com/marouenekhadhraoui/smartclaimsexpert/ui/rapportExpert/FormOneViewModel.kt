package com.marouenekhadhraoui.smartclaimsexpert.ui.rapportExpert

import android.os.Build
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.data.local.Datapreferences
import com.marouenekhadhraoui.smartclaimsexpert.network.NetworkHelper
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.DossierRepository
import com.marouenekhadhraoui.smartclaimsexpert.ui.signin.SignInViewModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.validators.LiveDataValidator
import com.marouenekhadhraoui.smartclaimsexpert.ui.validators.LiveDataValidatorResolver
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.M)
@HiltViewModel
class FormOneViewModel @Inject constructor(
    var networkHelper: NetworkHelper,
    var logger: Logger,
) : ViewModel(),
    LifecycleObserver {


    var nom = MutableLiveData<String>()
    var expert = MutableLiveData<String>()
    var reference = MutableLiveData<String>()
    var dateMission = MutableLiveData<String>()
    var addresse = MutableLiveData<String>()
    var tel = MutableLiveData<String>()
    var fax = MutableLiveData<String>()




}