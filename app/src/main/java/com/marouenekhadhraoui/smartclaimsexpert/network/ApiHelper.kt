package com.marouenekhadhraoui.smartclaimsexpert.network


import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.VisioModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.suividialogs.SuiviModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.detailsBottomSheet.DetailAssureModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.DossierModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.signin.ExpertModel


interface ApiHelper {

    suspend fun getExpert(mail: String, password: String): List<ExpertModel>
    suspend fun getDossierPourExpert(token: String): List<DossierModel>
    suspend fun getDetailAssure(token: String): List<DetailAssureModel>
    suspend fun ajouterVisio(idDossier:Int,idAssure: Int,idExpert:Int,date:String,time:String,effectue:Int): List<VisioModel>
    suspend fun getVisio(idDossier:Int): List<VisioModel>
    suspend fun modifierVisio(idDossier:Int,effectue: Int,resultat:String) : List<VisioModel>
    suspend fun ajouterSuivi(idDossier:Int,idAssure: Int,idExpert:Int,date:String,time:String,effectue:Int): List<SuiviModel>
    suspend fun getSuivi(idDossier:Int): List<SuiviModel>
    suspend fun modifierSuivi(idDossier:Int,effectue: Int,resultat:String) : List<SuiviModel>
}