package com.marouenekhadhraoui.smartclaimsexpert.ui.signin

import com.marouenekhadhraoui.smartclaimsexpert.network.ApiHelper
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ExpertRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    suspend fun getAssure(mail: String, password: String) = apiHelper.getExpert(mail, password)


    /*   fun checkifNull(assure:AssureModel): Boolean {
           return assure == null
      }
      */
}