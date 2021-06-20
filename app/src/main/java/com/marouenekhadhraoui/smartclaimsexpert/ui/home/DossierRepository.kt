package com.marouenekhadhraoui.smartclaimsexpert.ui.home

import com.marouenekhadhraoui.smartclaimsexpert.network.ApiHelper
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

}