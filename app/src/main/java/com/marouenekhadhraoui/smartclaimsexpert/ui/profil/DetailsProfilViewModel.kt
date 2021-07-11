package com.marouenekhadhraoui.smartclaimsexpert.ui.profil

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
import com.marouenekhadhraoui.smartclaimsexpert.ui.signin.ExpertModel
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
class DetailsProfilViewModel @Inject constructor(
    private val dossierRepository: DossierRepository,
    var networkHelper: NetworkHelper,
    var logger: Logger,
    private val apprefs: Datapreferences
) : ViewModel(),
    LifecycleObserver {

    var list: List<DossierModel> = emptyList()
    var listAssure: List<ExpertModel> = emptyList()

    private val _dossier = MutableStateFlow(
        Resource.loading(
            data = list,
        )
    )

    // public
    val dossier: StateFlow<Resource<List<DossierModel>>> = _dossier

    private val _assure = MutableStateFlow(
        Resource.loading(
            data = listAssure,
        )
    )

    // public
    val assure: StateFlow<Resource<List<ExpertModel>>> = _assure

    private lateinit var tokenFetched: String

    init {

        _assure.value = Resource.loading(
            data = null,
        )
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                try {

                    logger.log("try")
                    apprefs.token.collect { token ->
                        _assure.value = Resource.success(
                            data = dossierRepository.getProfil(
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