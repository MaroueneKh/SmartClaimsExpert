package com.marouenekhadhraoui.smartclaimsexpert.ui.home

import com.marouenekhadhraoui.smartclaimsexpert.network.ApiHelper
import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.VisioModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.rapportExpert.RapportModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DossierRepository @Inject constructor(
        private val apiHelper: ApiHelper
) {
    suspend fun getDossierPourExpert(token: String) = apiHelper.getDossierPourExpert(token)
    suspend fun getDetailAssure(token: String) = apiHelper.getDetailAssure(token)
    suspend fun getVisio(idDossier: Int) = apiHelper.getVisio(idDossier)
    suspend fun getSuivi(idDossier: Int) = apiHelper.getSuivi(idDossier)
    suspend fun ajouterRapport(idDossier: Int,rapportModel:RapportModel) = apiHelper.ajouterRapport(idDossier,rapportModel)
    suspend fun getCountDown(idAssure: Int): List<VisioModel> = apiHelper.getCountDown(idAssure)
    suspend fun modifierDossier(idDossier: Int,etat:String): List<DossierModel> = apiHelper.modifierDossier(idDossier,etat)
    suspend fun getProfil(token: String) = apiHelper.getProfil(token)

}