package com.marouenekhadhraoui.smartclaimsexpert.ui.videovisio

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.data.local.Datapreferences
import com.marouenekhadhraoui.smartclaimsexpert.network.NetworkHelper
import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.VisioModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.suividialogs.SuiviModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.visiodialogs.VisioRepository
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
class VideoVisioViewModel @Inject constructor(
    private val dossierRepository: DossierRepository,
    var networkHelper: NetworkHelper,
    var logger: Logger,
    private val apprefs: Datapreferences
) : ViewModel(),
    LifecycleObserver {

    var list: List<VisioModel> = emptyList()

    private val _visio = MutableStateFlow(
        Resource.loading(
        data = list,
    ))
    val visio: StateFlow<Resource<List<VisioModel>>> = _visio

    // public


    var listSuivi: List<SuiviModel> = emptyList()

    private val _suivi = MutableStateFlow(
        Resource.loading(
            data = listSuivi,
        ))
    val suivi: StateFlow<Resource<List<SuiviModel>>> = _suivi
    // public



    private lateinit var tokenFetched: String

    fun getVisio(idDossier:Int)
    {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                try {

                    _visio.value = Resource.success(
                        data = dossierRepository.getVisio(idDossier)
                    )

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
    fun getSuivi(idDossier:Int)
    {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                try {

                    _suivi.value = Resource.success(
                        data = dossierRepository.getSuivi(idDossier)
                    )

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