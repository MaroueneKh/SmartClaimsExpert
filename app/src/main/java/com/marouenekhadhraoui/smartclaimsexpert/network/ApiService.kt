package com.marouenekhadhraoui.smartclaimsexpert.network

import com.marouenekhadhraoui.smartclaimsexpert.ui.home.detailsBottomSheet.DetailAssureModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.DossierModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.signin.ExpertModel
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @Headers(
            "Content-Type: application/json"
    )
    @POST("api/expert")
    suspend fun getExpert(@Query(value = "identifiant") mail: String,
                          @Query(value = "password") password: String): List<ExpertModel>
    @POST("api/getDossierPourExpert")
    suspend fun getDossierPourExpert(@Query(value = "token") token: String): List<DossierModel>

    @POST("api/getDetailsAssure")
    suspend fun getDetailAssure(@Query(value = "token") token: String): List<DetailAssureModel>



}