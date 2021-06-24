package com.marouenekhadhraoui.smartclaimsexpert.ui.rapportExpert

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.data.local.Datapreferences
import com.marouenekhadhraoui.smartclaimsexpert.network.NetworkHelper
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.DossierRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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







}