package com.marouenekhadhraoui.smartclaimsexpert.ui.home.detailsBottomSheet

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.data.local.Datapreferences
import com.marouenekhadhraoui.smartclaimsexpert.network.NetworkHelper
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.DossierModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.DossierRepository
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
class DetailsBottomViewModel @Inject constructor(
        private val dossierRepository: DossierRepository,
        var networkHelper: NetworkHelper,
        var logger: Logger,
        private val apprefs: Datapreferences
) : ViewModel(),
        LifecycleObserver {

    var list: List<DetailAssureModel> = emptyList()

    var listHistorique: List<DossierModel> = emptyList()
    private val _detailassure = MutableStateFlow(Resource.loading(
            data = list,
    ))

    // public
    val detailassure: StateFlow<Resource<List<DetailAssureModel>>> = _detailassure

    private val _dossier = MutableStateFlow(Resource.loading(
            data = listHistorique,
    ))

    // public
    val dossier: StateFlow<Resource<List<DossierModel>>> = _dossier

    private lateinit var tokenFetched: String

    init {
        _detailassure.value = Resource.loading(
                data = null,
        )
    }
    fun detailAssure(id:Int)
    {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                try {

                    logger.log("try")
                    apprefs.token.collect { token ->
                        _detailassure.value = Resource.success(
                                data = dossierRepository.getDetailAssure(
                                        token
                                )
                        )
                    }

                } catch (exception: Exception) {
                    logger.log("catch")
                    logger.log(exception.message.toString())
                    _detailassure.value = Resource.error(
                            data = null,
                            message = exception.message ?: otherErr
                    )
                }
            }
        }

    }
fun afficherHistorique()
{
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


}