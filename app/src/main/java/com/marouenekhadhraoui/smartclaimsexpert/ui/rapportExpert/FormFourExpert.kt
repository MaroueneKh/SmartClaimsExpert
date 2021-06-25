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
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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

    lateinit var adapter: TextAdapter

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

        valider.setOnClickListener {
            if (!CheckForm())
            {
                viewModel.accord.value = edittext.text.toString()
                viewModel.impact.value = filtreText.text.toString()
                viewModel.fourniture1.value = designation.text.toString()
                viewModel.fourniture2.value = designation2.text.toString()
                viewModel.fourniture3.value = designation3.text.toString()
                viewModel.montant1.value = montant.text.toString()
                viewModel.montant2.value = montant2.text.toString()
                viewModel.montant3.value = montant3.text.toString()
                viewModel.v1.value = v.text.toString()
                viewModel.v2.value = v2.text.toString()
                viewModel.v3.value = v3.text.toString()
                viewModel.v3.value = v3.text.toString()
                viewModel.nature.value = naturetext.text.toString()



                viewModel.ajouterRapport(arguments?.get("id").toString().toInt())



            }

        }

    }
    private fun setPointDeChoc() {

        val items = listOf("Avant", "Arriere", "Gauche","Droite")
        val adapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_filtres, items)

        (filtreText)?.setAdapter(adapter)

    }


    fun CheckForm(): Boolean {
        var empty: Boolean = false
        if (TextUtils.isEmpty(edittext!!.text)) {
            empty = true
            edittext!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(designation!!.text)) {
            empty = true
            designation!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(montant!!.text)) {
            empty = true
            montant!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(v!!.text)) {
            empty = true
            v!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(designation2!!.text)) {
            empty = true
            designation2!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(montant2!!.text)) {
            empty = true
            montant2!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(v2!!.text)) {
            empty = true
            v2!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(designation3!!.text)) {
            empty = true
            designation3!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(montant3!!.text)) {
            empty = true
            montant3!!.error = "Champ Vide"
        }
        if (TextUtils.isEmpty(v3!!.text)) {
            empty = true
            v3!!.error = "Champ Vide"
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
















}