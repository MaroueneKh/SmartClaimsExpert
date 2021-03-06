package com.marouenekhadhraoui.smartclaimsexpert.network


import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.VisioModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.suividialogs.SuiviModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.detailsBottomSheet.DetailAssureModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.DossierModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.rapportExpert.RapportModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.signin.ExpertModel

import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun getExpert(mail: String, password: String): List<ExpertModel> = apiService.getExpert(mail, password)
    override suspend fun getDossierPourExpert(token: String): List<DossierModel> = apiService.getDossierPourExpert(token)
    override suspend fun getDetailAssure(token: String): List<DetailAssureModel> = apiService.getDetailAssure(token)
    override suspend fun ajouterVisio(idDossier:Int,idAssure: Int, idExpert: Int, date: String, time: String,effectue:Int): List<VisioModel> = apiService.ajouterVisio(idDossier,idAssure,idExpert,date,time,0)
    override suspend fun getVisio(idDossier: Int): List<VisioModel> = apiService.getVisio(idDossier)
    override suspend fun modifierVisio(idDossier: Int, effectue: Int,resultat:String):List<VisioModel> = apiService.modifierVisio(idDossier,effectue,resultat)

    override suspend fun ajouterSuivi(idDossier:Int,idAssure: Int, idExpert: Int, date: String, time: String,effectue:Int): List<SuiviModel> = apiService.ajouterSuivi(idDossier,idAssure,idExpert,date,time,0)
    override suspend fun getSuivi(idDossier: Int): List<SuiviModel> = apiService.getSuivi(idDossier)
    override suspend fun modifierSuivi(idDossier: Int, effectue: Int,resultat:String):List<SuiviModel> = apiService.modifierSuivi(idDossier,effectue,resultat)
   override suspend fun callAssure(idAssure:Int,call:String) : List<SuiviModel> = apiService.callAssure(idAssure,call)
    override suspend fun ajouterRapport(idAssure: Int, rapportModel: RapportModel): List<RapportModel>  = apiService.AjouterRapport(idAssure,rapportModel)
    override suspend fun getCountDown(idAssure: Int): List<VisioModel>  = apiService.getCountDown(idAssure)
   override suspend fun modifierDossier(idDossier: Int,etat:String): List<DossierModel> = apiService.modifierDossier(idDossier,etat)
    override suspend fun getProfil(token: String): List<ExpertModel> =
        apiService.getProfil(token)

}


