package com.marouenekhadhraoui.smartclaimsexpert.network


import com.marouenekhadhraoui.smartclaimsexpert.ui.home.detailsBottomSheet.DetailAssureModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.DossierModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.signin.ExpertModel


interface ApiHelper {

    suspend fun getExpert(mail: String, password: String): List<ExpertModel>
    suspend fun getDossierPourExpert(token: String): List<DossierModel>
    suspend fun getDetailAssure(token: String): List<DetailAssureModel>
}