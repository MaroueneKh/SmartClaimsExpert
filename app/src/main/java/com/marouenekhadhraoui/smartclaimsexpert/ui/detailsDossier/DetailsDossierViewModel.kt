package com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.data.local.Datapreferences
import com.marouenekhadhraoui.smartclaimsexpert.network.NetworkHelper
import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.suividialogs.SuiviModel
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
class DetailsDossierViewModel @Inject constructor(
        private val dossierRepository: DossierRepository,
        val networkHelper: NetworkHelper,
        var logger: Logger,
        private val apprefs: Datapreferences
) : ViewModel(),
        LifecycleObserver {

    var list: List<DetailAssureModel> = emptyList()

    var listHistorique: List<DossierModel> = emptyList()

    var listVisios: List<VisioModel> = emptyList()


    private val _dossier = MutableStateFlow(Resource.loading(
            data = listHistorique,
    ))

    // public
    val dossier: StateFlow<Resource<List<DossierModel>>> = _dossier



    private val _suivi = MutableStateFlow(Resource.loading(
        data = listVisios,
    ))

    // public
    val suivi: StateFlow<Resource<List<VisioModel>>> = _suivi


    private val _pressBtnPlanifierEvent = MutableLiveData<Event<Unit>>()
    val pressBtnPlanifierEvent: LiveData<Event<Unit>> = _pressBtnPlanifierEvent

    init {

        _dossier.value = Resource.loading(
                data = null,
        )
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                try {

                    logger.log("try")
                    apprefs.token.collect { token ->
                        _dossier.value = Resource.success(
                                data = dossierRepository.getDossierPourExpert(
                                        token
                                )
                        )
                    }

                } catch (exception: Exception) {
                    logger.log("catch")
                    logger.log(exception.message.toString())
                    _dossier.value = Resource.error(
                            data = null,
                            message = exception.message ?: otherErr
                    )
                }

            }

        }


    }
    fun pressButtonPlanifier()
    {
        _pressBtnPlanifierEvent.value = Event(Unit)

    }
    fun getVisio(idDossier:Int)
    {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                try {

                    logger.log("try")
                    apprefs.token.collect { token ->
                        _suivi.value = Resource.success(
                            data = dossierRepository.getVisio(idDossier)
                        )
                    }

                } catch (exception: Exception) {
                    logger.log("catch")
                    logger.log(exception.message.toString())
                    _suivi.value = Resource.error(
                        data = null,
                        message = exception.message ?: otherErr
                    )
                }
            }
        }


    }



}