package com.marouenekhadhraoui.smartclaimsexpert.ui.signin

import com.google.gson.annotations.SerializedName

data class ExpertModel(
        @SerializedName("contrat")
        val contrat: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("identifiant")
        val identifiant: String,
        @SerializedName("mail")
        val mail: String,
        @SerializedName("password")
        val password: String,
        @SerializedName("token")
        val token: String


)