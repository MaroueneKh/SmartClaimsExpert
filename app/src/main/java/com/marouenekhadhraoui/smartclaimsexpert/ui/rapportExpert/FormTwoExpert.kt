package com.marouenekhadhraoui.smartclaimsexpert.ui.rapportExpert

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.R
import com.marouenekhadhraoui.smartclaimsexpert.databinding.DialogPlanifierDateSuiviBinding
import com.marouenekhadhraoui.smartclaimsexpert.databinding.FormTwoExpertBinding
import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.suividialogs.PlanifierSuiviViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_planifier_date.*
import kotlinx.android.synthetic.main.form_one_expert.*
import kotlinx.android.synthetic.main.form_one_expert.edittext
import kotlinx.android.synthetic.main.form_two_expert.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class FormTwoExpert : Fragment() {

    private var _binding: FormTwoExpertBinding? = null
    private val binding get() = _binding
    private val viewModelFour: FormFourViewModel by activityViewModels()

    private lateinit var navDirections: NavDirections

    @Inject
    lateinit var logger: Logger


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.form_two_expert, container, false)
        binding?.lifecycleOwner = this

        return binding?.root
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initalizeForm()
        suivantTwo.setOnClickListener {
            if (!CheckForm())
            {
                viewModelFour.mandant.value = mandanttext.text.toString()
                viewModelFour.dateAccident.value = dateEditText.text.toString()
                viewModelFour.assure.value = assureTextField.text.toString()
                viewModelFour.immatriculation.value = immTextfield.text.toString()
                viewModelFour.contrat.value = contratTextField.text.toString()
                viewModelFour.nDossier.value = dossierTextField.text.toString()
                viewModelFour.tiers.value = tierTextField.text.toString()
                viewModelFour.veh.value = vehTextField.text.toString()
                viewModelFour.cont.value = dosTextField.text.toString()
                viewModelFour.cie.value = cieTextField.text.toString()

                val bundle = bundleOf("id" to arguments?.get("id").toString())
                setNavDirectionsTo(bundle)
                val navController = findNavController()
                navController.navigate(navDirections)

            }
        }


    }
    fun CheckForm(): Boolean {
        var empty: Boolean = false
        if (TextUtils.isEmpty(mandanttext!!.text)) {
            empty = true
            mandanttext!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(dateEditText!!.text)) {
            empty = true
            dateEditText!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(assureTextField!!.text)) {
            empty = true
            assureTextField!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(immTextfield!!.text)) {
            empty = true
            immTextfield!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(contratTextField!!.text)) {
            empty = true
            contratTextField!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(dossierTextField!!.text)) {
            empty = true
            dossierTextField!!.error = "Champ Vide"
        }
        if ((tierTextField!!.text?.length!!<8)){
            empty = true
            tierTextField!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(vehTextField!!.text)){
            empty = true
            vehTextField!!.error = "Champs vide"
        }
        if (TextUtils.isEmpty(contTextField!!.text)){
            empty = true
            contTextField!!.error = "Champs vide"
        }
        if (TextUtils.isEmpty(cieTextField!!.text)){
            empty = true
            cieTextField!!.error = "Champs vide"
        }
        if (TextUtils.isEmpty(dosTextField!!.text)){
            empty = true
            dosTextField!!.error = "Champs vide"
        }
        if (immTextfield!!.text?.length!!<8){
            empty = true
            immTextfield!!.error = "Champs vide"
        }
        return empty
    }




    fun setNavDirectionsTo(bundle: Bundle) {

        navDirections = object : NavDirections {
            override fun getArguments(): Bundle {
                return bundle
            }

            override fun getActionId(): Int {
                return R.id.action_formTwoExpert_to_formThreeExpert
            }
        }


    }
    fun initalizeForm()
    {
        dateEditText.setText("12/05/2021")
        assureTextField.setText("1")
        dossierTextField.setText("51")

    }








}