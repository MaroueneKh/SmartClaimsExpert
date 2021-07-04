package com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.suividialogs

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
import com.marouenekhadhraoui.smartclaimsexpert.ui.main.VisioViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.demande_acquisition_dialog.view.*
import kotlinx.android.synthetic.main.dialog_type_expertise.view.*
import javax.inject.Inject


@AndroidEntryPoint
class DemandeAcquisitionDialog : DialogFragment() {

    @Inject
    lateinit var logger: Logger

    private val viewModel: VisioViewModel by viewModels()

    private lateinit var navDirections: NavDirections
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myview: View = inflater.inflate(R.layout.demande_acquisition_dialog, container, false)

        myview.btnSubmit.setOnClickListener(View.OnClickListener {
            viewModel.modifierSuivi(arguments?.get("id").toString().toInt(),1,"En attente de facture")
            viewModel.modifierDossier(arguments?.get("id").toString().toInt(),"En attente de facture")


            val bundle = bundleOf("id" to arguments?.get("id").toString())
            setNavDirections(bundle)
            val navController = findNavController()
            navController.navigate(navDirections)

        })


        return myview
    }

    fun setNavDirections(bundle: Bundle) {

        navDirections = object : NavDirections {
            override fun getArguments(): Bundle {
                return bundle
            }

            override fun getActionId(): Int {
                return R.id.action_demandeAcquisitionDialog_to_videoVisioFragment
            }
        }


    }

}