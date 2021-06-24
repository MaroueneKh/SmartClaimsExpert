//<editor-fold desc="Description">
package com.marouenekhadhraoui.smartclaimsexpert.ui.rapportExpert

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
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
import javax.inject.Singleton

@RequiresApi(Build.VERSION_CODES.M)
@HiltViewModel
class FormFourViewModel @Inject constructor(
    private val dossierRepository: DossierRepository,
    var networkHelper: NetworkHelper,
    var logger: Logger,
    private val apprefs: Datapreferences
) : ViewModel(),
    LifecycleObserver {
    var nom = MutableLiveData<String>()
    var expert = MutableLiveData<String>()
    var reference = MutableLiveData<String>()
    var dateMission = MutableLiveData<String>()
    var addresse = MutableLiveData<String>()
    var tel = MutableLiveData<String>()
    var fax = MutableLiveData<String>()

    var mandant = MutableLiveData<String>()
    var dateAccident = MutableLiveData<String>()
    var assure = MutableLiveData<String>()
    var immatriculation = MutableLiveData<String>()
    var contrat = MutableLiveData<String>()
    var nDossier = MutableLiveData<String>()
    var tiers = MutableLiveData<String>()
    var veh = MutableLiveData<String>()
    var cont = MutableLiveData<String>()
    var dos = MutableLiveData<String>()
    var cie = MutableLiveData<String>()

    var dateexam = MutableLiveData<String>()
    var vehexp = MutableLiveData<String>()
    var lieu = MutableLiveData<String>()
    var obs = MutableLiveData<String>()
    var marque = MutableLiveData<String>()
    var type = MutableLiveData<String>()
    var puiss = MutableLiveData<String>()
    var indicek = MutableLiveData<String>()
    var genre = MutableLiveData<String>()
    var couleur = MutableLiveData<String>()
    var etatveh = MutableLiveData<String>()
    var immob = MutableLiveData<String>()
    var chassis = MutableLiveData<String>()
    var mc = MutableLiveData<String>()
    var circonstance = MutableLiveData<String>()
    var vn = MutableLiveData<String>()
    var vv = MutableLiveData<String>()

    var accord = MutableLiveData<String>()
    var impact = MutableLiveData<String>()
    var fourniture1 = MutableLiveData<String>()
    var fourniture2 = MutableLiveData<String>()
    var fourniture3 = MutableLiveData<String>()
    var montant1 = MutableLiveData<String>()
    var montant2 = MutableLiveData<String>()
    var montant3 = MutableLiveData<String>()
    var v1 = MutableLiveData<String>()
    var v2 = MutableLiveData<String>()
    var v3 = MutableLiveData<String>()
    var nature = MutableLiveData<String>()

    var list: List<RapportModel> = emptyList()

    private val _rapport = MutableStateFlow(Resource.loading(
        data = list,
    ))

    // public
    val rapport: StateFlow<Resource<List<RapportModel>>> = _rapport


fun ajouterRapport(idDossier:Int)
{
    if (networkHelper.isNetworkConnected()) {
        viewModelScope.launch {
            try {

                logger.log("try")
                apprefs.token.collect { token ->
                    _rapport.value = Resource.success(
                        data = dossierRepository.ajouterRapport(
                            idDossier,
                            RapportModel(nom.value!!,expert.value!!,reference.value!!,dateMission.value!!,addresse.value!!,tel.value!!,fax.value!!,mandant.value!!,
                                    dateAccident.value!!,assure.value!!,immatriculation.value!!,contrat.value!!,nDossier.value!!,tiers.value!!,veh.value!!,cont.value!!,dos.value!!,
                                        cie.value!!,dateexam.value!!,vehexp.value!!,lieu.value!!,obs.value!!,marque.value!!,type.value!!,puiss.value!!,indicek.value!!,genre.value!!,
                                couleur.value!!,etatveh.value!!,immob.value!!,chassis.value!!,mc.value!!,circonstance.value!!,vn.value!!,vv.value!!,accord.value!!,impact.value!!,
                                fourniture1.value!!,fourniture2.value!!,fourniture3.value!!,montant1.value!!,montant2.value!!,montant3.value!!,v1.value!!,v2.value!!,v3.value!!,nature.value!!)
                        )
                    )
                }

            } catch (exception: Exception) {
                logger.log("catch")
                logger.log(exception.message.toString())
                _rapport.value = Resource.error(
                    data = null,
                    message = exception.message ?: otherErr
                )
            }
        }



}

}

    }
