package com.marouenekhadhraoui.smartclaimsexpert.network

import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.VisioModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.suividialogs.SuiviModel
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

    @POST("api/ajouterVisio")
    suspend fun ajouterVisio(
        @Query(value = "idDossier") idDossier: Int,
        @Query(value = "idAssure") idAssure: Int,
                             @Query(value = "idExpert") idExpert: Int,
                             @Query(value = "date") date: String,
                             @Query(value = "time") time: String,
        @Query(value = "effectue") effectue: Int,
                             ): List<VisioModel>
    @POST("api/getVisio")
    suspend fun getVisio(
        @Query(value = "idDossier") idDossier: Int,
        ): List<VisioModel>
    @POST("api/modifierVisio")
    suspend fun modifierVisio(
        @Query(value = "idDossier") idDossier: Int,
        @Query(value = "effectue") effectue: Int,
        @Query(value = "resultat") resultat: String
    ): List<VisioModel>
    @POST("api/ajouterSuivi")
    suspend fun ajouterSuivi(
        @Query(value = "idDossier") idDossier: Int,
        @Query(value = "idAssure") idAssure: Int,
        @Query(value = "idExpert") idExpert: Int,
        @Query(value = "date") date: String,
        @Query(value = "time") time: String,
        @Query(value = "effectue") effectue: Int,
    ): List<SuiviModel>
    @POST("api/getSuivi")
    suspend fun getSuivi(
        @Query(value = "idDossier") idDossier: Int,
    ): List<SuiviModel>
    @POST("api/modifierSuivi")
    suspend fun modifierSuivi(
        @Query(value = "idDossier") idDossier: Int,
        @Query(value = "effectue") effectue: Int,
        @Query(value = "resultat") resultat: String
    ): List<SuiviModel>
    @POST("api/callAssure")
    suspend fun callAssure(
        @Query(value = "idAssure") idDossier: Int,
    ): List<SuiviModel>



}