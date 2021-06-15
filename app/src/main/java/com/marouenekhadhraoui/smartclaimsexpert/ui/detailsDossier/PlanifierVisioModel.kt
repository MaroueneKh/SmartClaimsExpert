package com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.data.local.Datapreferences
import com.marouenekhadhraoui.smartclaimsexpert.network.NetworkHelper
import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.visiodialogs.VisioRepository
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
class PlanifierVisioModelView @Inject constructor(
        private val visioRepository: VisioRepository,
        val networkHelper: NetworkHelper,
        var logger: Logger,
        private val apprefs: Datapreferences
) : ViewModel(),
        LifecycleObserver {

    var list: List<VisioModel> = emptyList()

    var date = MutableLiveData<String>()
    var time = MutableLiveData<String>()


    private val _visio = MutableStateFlow(Resource.loading(
            data = list,
    ))
    // public
    val visio: StateFlow<Resource<List<VisioModel>>> = _visio
    private val _pressBtnPlanifierEvent = MutableLiveData<Event<Unit>>()

    fun ajouterVisio(idDossier:Int,idAssure:Int,idExpert:Int,date:String,time:String)
    {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                try {

                    logger.log("try")
                    apprefs.token.collect { token ->
                        _visio.value = Resource.success(
                                data = visioRepository.ajouterVisio(idDossier,idAssure,idExpert,date,time)
                        )
                    }

                } catch (exception: Exception) {
                    logger.log("catch")
                    logger.log(exception.message.toString())
                    _visio.value = Resource.error(
                            data = null,
                            message = exception.message ?: otherErr
                    )
                }
            }
        }

    }



}