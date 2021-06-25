package com.marouenekhadhraoui.smartclaimsexpert.ui.rapportExpert

import android.app.DatePickerDialog
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
import com.marouenekhadhraoui.smartclaimsexpert.databinding.FormThreeExpertBinding
import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.suividialogs.PlanifierSuiviViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_planifier_date.*
import kotlinx.android.synthetic.main.form_one_expert.*
import kotlinx.android.synthetic.main.form_one_expert.edittext
import kotlinx.android.synthetic.main.form_three_expert.*
import kotlinx.android.synthetic.main.form_three_expert.vehTextField
import kotlinx.android.synthetic.main.form_two_expert.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class FormThreeExpert : Fragment() {


    private val viewModelFour: FormFourViewModel by activityViewModels()

    private var _binding: FormThreeExpertBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var logger: Logger
    private lateinit var navDirections: NavDirections


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.form_three_expert, container, false)
        binding?.lifecycleOwner = this

        return binding?.root
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cal = Calendar.getInstance()
        val y = cal.get(Calendar.YEAR)
        val m = cal.get(Calendar.MONTH)
        val d = cal.get(Calendar.DAY_OF_MONTH)
        examTextField.setEndIconOnClickListener {
            DatePickerDialog(requireContext(), { view, year, monthOfYear, dayOfMonth ->
                dateexamtext.setText(dayOfMonth.toString() + "/ " + monthOfYear + "/" + year)
            }, y, m, d).show()
        }
        suivantThree.setOnClickListener {
            if (!CheckForm())
            {
                viewModelFour.dateexam.value = dateexamtext.text.toString()
                viewModelFour.vehexp.value = vehexpEditText.text.toString()
                viewModelFour.lieu.value = lieuTextField.text.toString()
                viewModelFour.obs.value = obsTextfield.text.toString()
                viewModelFour.marque.value = marqueTextField.text.toString()
                viewModelFour.type.value = typeTextField.text.toString()
                viewModelFour.puiss.value = puissTextField.text.toString()
                viewModelFour.indicek.value = indexTextField.text.toString()
                viewModelFour.genre.value = genreTextField.text.toString()
                viewModelFour.couleur.value = couleurTextField.text.toString()
                viewModelFour.etatveh.value = vehTextField.text.toString()
                viewModelFour.immob.value = immobTextField.text.toString()
                viewModelFour.chassis.value = chassisTextField.text.toString()
                viewModelFour.mc.value = mcTextField.text.toString()
                viewModelFour.circonstance.value = circonstanceTextField.text.toString()
                viewModelFour.vn.value = vnTextField.text.toString()
                viewModelFour.vv.value = VVTextField.text.toString()
                val bundle = bundleOf("id" to arguments?.get("id").toString())
                setNavDirectionsTo(bundle)
                val navController = findNavController()
                navController.navigate(navDirections)
            }

        }



    }

    fun setNavDirectionsTo(bundle: Bundle) {

        navDirections = object : NavDirections {
            override fun getArguments(): Bundle {
                return bundle
            }

            override fun getActionId(): Int {
                return R.id.action_formThreeExpert_to_formFourExpert
            }
        }


    }


    fun CheckForm(): Boolean {
        var empty: Boolean = false
        if (TextUtils.isEmpty(dateexamtext!!.text)) {
            empty = true
            dateexamtext!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(vehexpEditText!!.text)) {
            empty = true
            vehexpEditText!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(lieuTextField!!.text)) {
            empty = true
            lieuTextField!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(obsTextfield!!.text)) {
            empty = true
            obsTextfield!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(marqueTextField!!.text)) {
            empty = true
            marqueTextField!!.error = "Champ Vide"
        }
        if ((TextUtils.isEmpty(typeTextField!!.text))) {
            empty = true
            typeTextField!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(marqueTextField!!.text)) {
            empty = true
            marqueTextField!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(puissTextField!!.text)) {
            empty = true
            puissTextField!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(indexTextField!!.text)) {
            empty = true
            indexTextField!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(genreTextField!!.text)) {
            empty = true
            genreTextField!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(couleurTextField!!.text)) {
            empty = true
            couleurTextField!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(vehTextField!!.text)) {
            empty = true
            vehTextField!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(immobTextField!!.text)) {
            empty = true
            immobTextField!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(chassisTextField!!.text)) {
            empty = true
            chassisTextField!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(mcTextField!!.text)) {
            empty = true
            mcTextField!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(circonstanceTextField!!.text)) {
            empty = true
            circonstanceTextField!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(vnTextField!!.text)) {
            empty = true
            vnTextField!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(VVTextField!!.text)) {
            empty = true
            VVTextField!!.error = "Champ Vide"
        }
        return empty
    }











}