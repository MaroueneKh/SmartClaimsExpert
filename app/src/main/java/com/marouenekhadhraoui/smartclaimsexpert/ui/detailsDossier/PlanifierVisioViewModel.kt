package com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.data.local.Datapreferences
import com.marouenekhadhraoui.smartclaimsexpert.network.NetworkHelper
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.DossierModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.DossierRepository
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.detailsBottomSheet.DetailAssureModel
import com.marouenekhadhraoui.smartclaimsexpert.utils.Event
import com.marouenekhadhraoui.smartclaimsexpert.utils.Resource
import com.marouenekhadhraoui.smartclaimsexpert.utils.otherErr
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.M)
@HiltViewModel
class PlanifierVisioViewModel @Inject constructor(
        val networkHelper: NetworkHelper,
        var logger: Logger,
) : ViewModel(),
        LifecycleObserver {






    private val _pressSuivantEvent = MutableLiveData<Event<Unit>>()
    val pressSuivantEvent: LiveData<Event<Unit>> = _pressSuivantEvent


    fun pressButtonSuivant()
    {
        _pressSuivantEvent.value = Event(Unit)

    }



}