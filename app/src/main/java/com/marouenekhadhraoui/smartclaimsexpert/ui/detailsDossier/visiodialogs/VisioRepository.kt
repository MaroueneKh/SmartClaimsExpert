package com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.visiodialogs

import com.marouenekhadhraoui.smartclaimsexpert.network.ApiHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VisioRepository @Inject constructor(
        private val apiHelper: ApiHelper
) {
    suspend fun ajouterVisio(idDossier:Int,idAssure: Int, idExpert: Int, date: String, time: String)= apiHelper.ajouterVisio(idDossier,idAssure,idExpert,date,time,0)
    suspend fun modifierVisio(idDossier:Int,effectue:Int)= apiHelper.modifierVisio(idDossier,effectue)

}