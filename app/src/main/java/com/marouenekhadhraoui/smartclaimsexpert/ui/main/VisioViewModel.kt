package com.marouenekhadhraoui.smartclaimsexpert.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.data.local.Datapreferences
import com.marouenekhadhraoui.smartclaimsexpert.network.NetworkHelper
import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.VisioModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.suividialogs.SuiviModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.visiodialogs.VisioRepository
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
class VisioViewModel @Inject constructor(
    private val visioRepository: VisioRepository,
    private val dossierRepository:  DossierRepository,
    val networkHelper: NetworkHelper,
    var logger: Logger,
) : ViewModel(),
    LifecycleObserver {

    var list: List<VisioModel> = emptyList()
    var listSuivi: List<SuiviModel> = emptyList()

    var date = MutableLiveData<String>()
    var time = MutableLiveData<String>()

    private val _visio = MutableStateFlow(
        Resource.loading(
        data = list,
    ))
    val visio: StateFlow<Resource<List<VisioModel>>> = _visio

    private val _suivi = MutableStateFlow(
        Resource.loading(
            data = listSuivi,
        ))
    val suivi: StateFlow<Resource<List<SuiviModel>>> = _suivi

    fun modifierVisio(idDossier:Int,effectue:Int,resultat:String)
    {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                try {
                    _visio.value = Resource.success(
                        data = visioRepository.modifierVisio(idDossier,effectue,resultat)
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
                    _suivi.value = Resource.error(
                        data = null,
                        message = exception.message ?: otherErr
                    )
                }
            }
        }


    }
    fun modifierSuivi(idDossier:Int,effectue:Int,resultat:String)
    {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                try {
                    _suivi.value = Resource.success(
                        data = visioRepository.modifierSuivi(idDossier,effectue,resultat)
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
    fun callAssure(idAssure:Int)
    {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                try {
                    _suivi.value = Resource.success(
                        data = visioRepository.callAssure(idAssure)
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