package com.marouenekhadhraoui.smartclaimsexpert.ui.main

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_confirmer_reparation.view.*


import javax.inject.Inject

@AndroidEntryPoint
class ConfirmerReparationDialog : DialogFragment() {


    private val viewModel: VisioViewModel by viewModels()

    @Inject
    lateinit var logger: Logger

    private lateinit var navDirections: NavDirections


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myview: View = inflater.inflate(R.layout.dialog_confirmer_reparation, container, false)
        myview.btnSuivant.setOnClickListener {
            if (myview.oui.isChecked)
            {
                viewModel.modifierVisio(arguments?.get("id").toString().toInt(),1,"Pret pour reparation")
                viewModel.modifierDossier(arguments?.get("id").toString().toInt(),"Pret pour reparation")
            }



else if (myview.non.isChecked)


            viewModel.modifierVisio(arguments?.get("id").toString().toInt(),1,"En attente d'avis")
            viewModel.modifierDossier(arguments?.get("id").toString().toInt(),"En attente d'avis")

            val bundle = bundleOf("id" to arguments?.get("id").toString())
            setNavDirections(bundle)
            val navController = findNavController()
            navController.navigate(navDirections)
        }


        return myview
    }
    fun setNavDirections(bundle: Bundle) {

        navDirections = object : NavDirections {
            override fun getArguments(): Bundle {
                return bundle
            }

            override fun getActionId(): Int {
                return R.id.action_confirmerReparationDialog_to_videoVisioFragment
            }
        }


    }

}