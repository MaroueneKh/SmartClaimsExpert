package com.marouenekhadhraoui.smartclaimsexpert.ui.rapportExpert

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputEditText
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.R
import com.marouenekhadhraoui.smartclaimsexpert.databinding.DialogPlanifierDateSuiviBinding
import com.marouenekhadhraoui.smartclaimsexpert.databinding.FormFourExpertBinding
import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.suividialogs.PlanifierSuiviViewModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.DossierAdapter
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.DossierModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.MarginItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_planifier_date.*
import kotlinx.android.synthetic.main.form_four_expert.*
import kotlinx.android.synthetic.main.form_three_expert.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.filtreText
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class FormFourExpert : Fragment() {


    private val viewModel: FormFourViewModel by activityViewModels()

    private var _binding: FormFourExpertBinding? = null
    private val binding get() = _binding

    var list: ArrayList<TextModel> = ArrayList()

    private lateinit var linearLayoutManager: LinearLayoutManager


    @Inject
    lateinit var adapter: TextAdapter

    private lateinit var navDirections: NavDirections



    @Inject
    lateinit var logger: Logger


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.form_four_expert, container, false)
        binding?.lifecycleOwner = this

        return binding?.root
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPointDeChoc()
        setAdapter()

        valider.setOnClickListener {
            if (!CheckForm())
            {
                viewModel.accord.value = edittext.text.toString()
                viewModel.impact.value = filtreText.text.toString()

                viewModel.nature.value = naturetext.text.toString()

              var textInputEditText1 : TextInputEditText =   binding?.recyclerView?.getChildAt(0)!!.findViewById<View>(R.id.edittext1) as (TextInputEditText)
                var textInputEditText2 : TextInputEditText =   binding?.recyclerView?.getChildAt(0)!!.findViewById<View>(R.id.edittext2) as (TextInputEditText)
                var textInputEditText3 : TextInputEditText =   binding?.recyclerView?.getChildAt(0)!!.findViewById<View>(R.id.edittext3) as (TextInputEditText)

                var textInputEditText11 : TextInputEditText =   binding?.recyclerView?.getChildAt(1)!!.findViewById<View>(R.id.edittext1) as (TextInputEditText)
                var textInputEditText21 : TextInputEditText =   binding?.recyclerView?.getChildAt(1)!!.findViewById<View>(R.id.edittext2) as (TextInputEditText)
                var textInputEditText31 : TextInputEditText =   binding?.recyclerView?.getChildAt(1)!!.findViewById<View>(R.id.edittext3) as (TextInputEditText)


                var textInputEditText12 : TextInputEditText =   binding?.recyclerView?.getChildAt(2)!!.findViewById<View>(R.id.edittext1) as (TextInputEditText)
                var textInputEditText22 : TextInputEditText =   binding?.recyclerView?.getChildAt(2)!!.findViewById<View>(R.id.edittext2) as (TextInputEditText)
                var textInputEditText32 : TextInputEditText =   binding?.recyclerView?.getChildAt(2)!!.findViewById<View>(R.id.edittext3) as (TextInputEditText)





                viewModel.fourniture1.value = textInputEditText1.text.toString()
                viewModel.fourniture2.value = textInputEditText11.text.toString()
                viewModel.fourniture3.value = textInputEditText12.text.toString()

                viewModel.montant1.value = textInputEditText2.text.toString()
                viewModel.montant2.value = textInputEditText21.text.toString()
                viewModel.montant3.value = textInputEditText22.text.toString()

                viewModel.v1.value = textInputEditText3.text.toString()
                viewModel.v2.value = textInputEditText31.text.toString()
                viewModel.v3.value = textInputEditText32.text.toString()

                viewModel.ajouterRapport(arguments?.get("id").toString().toInt())
                viewModel.modifierDossier(arguments?.get("id").toString().toInt(),"Validé")

                viewModel.modifierSuivi(arguments?.get("id").toString().toInt(),1,"Facture ajouté")

               val bundle = bundleOf("id" to arguments?.get("id").toString())
                setNavDirectionsTo(bundle)
                val navController = findNavController()
                navController.navigate(navDirections)



            }

        }

    }
    private fun setPointDeChoc() {

        val items = listOf("Avant", "Arriere", "Gauche","Droite")
        val adapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_filtres, items)

        (filtreText)?.setAdapter(adapter)

    }
    fun setNavDirectionsTo(bundle: Bundle) {

        navDirections = object : NavDirections {
            override fun getArguments(): Bundle {
                return bundle
            }

            override fun getActionId(): Int {
                return R.id.action_formFourExpert_to_homeFragment
            }
        }


    }


    fun CheckForm(): Boolean {
        var empty: Boolean = false
        if (TextUtils.isEmpty(edittext!!.text)) {
            empty = true
            edittext!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(naturetext!!.text)) {
            empty = true
            naturetext!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(filtreText.text)) {
            empty = true
            naturetext!!.error = "Champ Vide"
        }
        return empty
    }

    fun setAdapter() {

        linearLayoutManager = LinearLayoutManager(requireContext())

        binding?.recyclerView?.layoutManager = linearLayoutManager
        list.add(TextModel("slslsls"))
        list.add(TextModel("slslsls"))
        list.add(TextModel("slslsls"))
        adapter.setItem(list)
        binding?.recyclerView?.adapter = adapter

        binding?.recyclerView?.addItemDecoration(
            MarginItemDecoration(1)
        )


    }

















}