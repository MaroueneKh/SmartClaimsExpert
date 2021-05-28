package com.marouenekhadhraoui.smartclaimsexpert.network


import com.marouenekhadhraoui.smartclaimsexpert.ui.home.detailsBottomSheet.DetailAssureModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.DossierModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.signin.ExpertModel

import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun getExpert(mail: String, password: String): List<ExpertModel> = apiService.getExpert(mail, password)
    override suspend fun getDossierPourExpert(token: String): List<DossierModel> = apiService.getDossierPourExpert(token)
    override suspend fun getDetailAssure(token: String): List<DetailAssureModel> = apiService.getDetailAssure(token)


}


