package com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier

data class VisioModel(
        val id: Int,
        val idDossier: Int,
        val idAssure: Int,
        val idExpert: Int,
        val date: String,
        val time: String,
        val effectue: Int,
        val resultat: String,
)