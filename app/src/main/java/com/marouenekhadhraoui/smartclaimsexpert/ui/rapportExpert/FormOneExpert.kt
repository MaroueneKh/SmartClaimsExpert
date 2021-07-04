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
import com.marouenekhadhraoui.smartclaimsexpert.databinding.FormOneExpertBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.form_one_expert.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class FormOneExpert : Fragment() {


    private val viewModelFour: FormFourViewModel by activityViewModels()
    private var _binding: FormOneExpertBinding? = null
    private val binding get() = _binding

    private lateinit var navDirections: NavDirections
    @Inject
    lateinit var logger: Logger


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.form_one_expert, container, false)
        binding?.lifecycleOwner = this

        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        initalizeForm()
        missionTextField.setEndIconActivated(true)
        val cal = Calendar.getInstance()
        val y = cal.get(Calendar.YEAR)
        val m = cal.get(Calendar.MONTH)
        val d = cal.get(Calendar.DAY_OF_MONTH)

        missionTextField.setEndIconOnClickListener {
            // do some code
            DatePickerDialog(requireContext(), { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in textbox
                dateTextfield.setText(dayOfMonth.toString() + "/ " + monthOfYear + "/" + year)
            }, y, m, d).show()
        }

        suivant.setOnClickListener {
            if (!CheckForm())
            {
                viewModelFour.nom.value = edittext.text.toString()
                viewModelFour.expert.value = expertEditText.text.toString()
                viewModelFour.reference.value = refTextField.text.toString()
                viewModelFour.dateMission.value = dateTextfield.text.toString()
                viewModelFour.addresse.value = addresseTextField.text.toString()
                viewModelFour.tel.value = telTextField.text.toString()
                viewModelFour.fax.value = facTextField.text.toString()

                val bundle = bundleOf("id" to arguments?.get("id").toString())
                setNavDirectionsTo(bundle)
                val navController = findNavController()
                navController.navigate(navDirections)
            }
        }



    }
    fun bindViewModel() {
        binding?.viewModel = viewModelFour
    }
     fun CheckForm(): Boolean {
        var empty: Boolean = false
        if (TextUtils.isEmpty(edittext!!.text)) {
            empty = true
            edittext!!.error = "Champ Vide"
        }
         if (TextUtils.isEmpty(expertEditText!!.text)) {
            empty = true
            expertEditText!!.error = "Champ Vide"
        }
         if (TextUtils.isEmpty(refTextField!!.text)) {
            empty = true
            refTextField!!.error = "Champ Vide"
        }
         if (TextUtils.isEmpty(dateTextfield!!.text)) {
            empty = true
            dateTextfield!!.error = "Champ Vide"
        }
         if (TextUtils.isEmpty(addresseTextField!!.text)) {
            empty = true
            addresseTextField!!.error = "Champ Vide"
        }
         if ((TextUtils.isEmpty(telTextField!!.text))||(telTextField!!.text?.length!!<8)) {
            empty = true
            telTextField!!.error = "Champ Vide"
        }
        if ((facTextField!!.text?.length!!<8)){
            empty = true
            facTextField!!.error = "Champ Vide"
        }
         if ((facTextField!!.text?.length!!!=8)){
             empty = true
             facTextField!!.error = "Ce champs doit comporter 8 chiffres"
         }
         if ((telTextField!!.text?.length!!!=8)){
             empty = true
             facTextField!!.error = "Ce champs doit comporter  8 chiffres"
         }
        return empty
    }
    fun setNavDirectionsTo(bundle: Bundle) {

        navDirections = object : NavDirections {
            override fun getArguments(): Bundle {
                return bundle
            }

            override fun getActionId(): Int {
                return R.id.action_formOneExpert_to_formTwoExpert
            }
        }


    }
    fun initalizeForm()
    {
        viewModelFour.nom.value = "Marouene Khadhraoui"
        viewModelFour.expert.value = "Malek Ben Marzouk"
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateInstance() //or use getDateInstance()
        val formatedDate = formatter.format(date)
        refTextField.setText(formatedDate.toString())
        viewModelFour.dateMission.value = formatedDate.toString()

    }

}