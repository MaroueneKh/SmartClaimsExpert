package com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.suividialogs

data class SuiviModel(
    val id: Int,
    val idDossier: Int,
    val idAssure: Int,
    val idExpert: Int,
    val date: String,
    val time: String,
    val effectue: Int,
    val resultat: String,
)