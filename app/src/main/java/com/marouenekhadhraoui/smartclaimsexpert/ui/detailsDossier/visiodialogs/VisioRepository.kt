package com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.visiodialogs

import com.marouenekhadhraoui.smartclaimsexpert.network.ApiHelper
import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.suividialogs.SuiviModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VisioRepository @Inject constructor(
        private val apiHelper: ApiHelper
) {
    suspend fun ajouterVisio(idDossier:Int,idAssure: Int, idExpert: Int, date: String, time: String)= apiHelper.ajouterVisio(idDossier,idAssure,idExpert,date,time,0)
    suspend fun modifierVisio(idDossier:Int,effectue:Int,resultat:String)= apiHelper.modifierVisio(idDossier,effectue,resultat)
    suspend fun ajouterSuivi(idDossier:Int,idAssure: Int, idExpert: Int, date: String, time: String)= apiHelper.ajouterSuivi(idDossier,idAssure,idExpert,date,time,0)
    suspend fun modifierSuivi(idDossier:Int,effectue:Int,resultat:String)= apiHelper.modifierSuivi(idDossier,effectue,resultat)
     suspend fun callAssure(idAssure:Int) = apiHelper.callAssure(idAssure)
}