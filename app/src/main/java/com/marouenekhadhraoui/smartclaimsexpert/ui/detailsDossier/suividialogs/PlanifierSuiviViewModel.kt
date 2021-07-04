package com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.suividialogs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.data.local.Datapreferences
import com.marouenekhadhraoui.smartclaimsexpert.network.NetworkHelper
import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.VisioModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.visiodialogs.VisioRepository
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.DossierModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.DossierRepository
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
class PlanifierSuiviViewModel @Inject constructor(
    private val visioRepository: VisioRepository,
    private val dossierRepository: DossierRepository,
    val networkHelper: NetworkHelper,
    var logger: Logger,
    private val apprefs: Datapreferences
) : ViewModel(),
    LifecycleObserver {

    var list: List<SuiviModel> = emptyList()

    var listDossier: List<DossierModel> = emptyList()

    var date = MutableLiveData<String>()
    var time = MutableLiveData<String>()


    private val _visio = MutableStateFlow(Resource.loading(
        data = list,
    ))
    // public
    val visio: StateFlow<Resource<List<SuiviModel>>> = _visio


    private val _dossier = MutableStateFlow(Resource.loading(
        data = listDossier,
    ))
    // public
    val dossier: StateFlow<Resource<List<DossierModel>>> = _dossier

    private val _pressBtnPlanifierEvent = MutableLiveData<Event<Unit>>()

    fun ajouterSuivi(idDossier:Int,idAssure:Int,idExpert:Int,date:String,time:String)
    {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                try {

                    logger.log("try")
                    apprefs.token.collect { token ->
                        _visio.value = Resource.success(
                            data = visioRepository.ajouterSuivi(idDossier,idAssure,idExpert,date,time)
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
    fun modifierDossier(idDossier:Int,etat:String)
    {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                try {

                    logger.log("try")
                    apprefs.token.collect { token ->
                        _dossier.value = Resource.success(
                            data = dossierRepository.modifierDossier(idDossier,etat)
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