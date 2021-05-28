package com.marouenekhadhraoui.smartclaimsexpert.ui.home

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.R
import com.marouenekhadhraoui.smartclaimsexpert.databinding.FragmentHomeBinding
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.detailsBottomSheet.DetailsBottomSheetFragment
import com.marouenekhadhraoui.smartclaimsexpert.utils.Status
import com.marouenekhadhraoui.smartclaimsexpert.utils.internetErr
import com.marouenekhadhraoui.smartclaimsexpert.utils.invisible
import com.marouenekhadhraoui.smartclaimsexpert.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {


    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding


    @Inject
    lateinit var logger: Logger

    var list: List<FiltreModel> = listOf()

    var listTri: List<TriModel> = listOf()

    var listDossiers: ArrayList<DossierModel> = ArrayList()

    private lateinit var linearLayoutManager: LinearLayoutManager


    lateinit var adapter: DossierAdapter


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding?.lifecycleOwner = this
        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        setSpinnerFiltre()
        setSpinnerTri()
        observeDossiers()

    }

    fun bindViewModel() {
        binding?.viewModel = viewModel
    }

    private fun setSpinnerFiltre() {
        list = listOf(
                FiltreModel( "En attente de confirmation"),
                FiltreModel( "En cours de traitement"),
                FiltreModel( "Validé"),
        )
        val customDropDownAdapter = SpinnerFilterCustomAdapter(requireContext(), list)
        binding?.filtreSpinner?.adapter = customDropDownAdapter

        filtre_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                when (pos) {
                    0 -> filterListByEtat("En cours d'envoie")
                    1 -> filterListByEtat("En cours de confirmation")
                    2 -> filterListByEtat("Validé")
                }

            }

            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {

            }

        }


    }
    private fun setSpinnerTri() {
        listTri = listOf(
                TriModel( "Nom",R.drawable.tri_down_icon),
                TriModel( "Nom",R.drawable.tri_up_icon),
                TriModel( "Date",R.drawable.tri_down_icon),
                TriModel( "Date",R.drawable.tri_up_icon)
        )
        val customDropDownAdapter = SpinnerTriCustomerAdapter(requireContext(), listTri)
        binding?.triSpinner?.adapter = customDropDownAdapter


        tri_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                when (pos) {
                    0 -> triListBynameAsc()
                    1 -> triListBynameDesc()
                    2 -> triListBydateAsc()
                    3 -> triListBydateDesc()
                }

            }

            override fun onNothingSelected(parent: AdapterView<out Adapter>?) {

            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun observeDossiers() {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.dossier.collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        logger.log("success")
                        if (it.data!!.isEmpty()) {
                            logger.log("null")
                        } else {
                            binding?.llProgressBar?.progressBar?.invisible()
                            listDossiers = ArrayList(it.data)
                            setAdapter()
                        }

                    }
                    Status.LOADING -> {
                        logger.log("loading")
                        binding?.llProgressBar?.progressBar?.visible()

                    }
                    Status.ERROR -> {
                        logger.log("error")
                        if (it.message.equals(internetErr)) {
                            logger.log("internet error")
                            //  Snackbar.make(findViewById(R.id.constraint), "Verifier votre connexion", Snackbar.LENGTH_LONG).show()
                        }

                    }
                }
            }
        }


    }
    fun setAdapter() {

        linearLayoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerViewDashboard?.layoutManager = linearLayoutManager
        val adapter = DossierAdapter { model ->
            parentFragmentManager.let {
                logger.log("check id")
                logger.log(model.id.toString())
                logger.log(model.identifiant)

                val bundle = bundleOf("id" to model.id.toString(),"nom" to model.identifiant )
                DetailsBottomSheetFragment.newInstance(bundle).apply {
                    arguments = bundle
                    show(it, tag)
                }
            }
        }
        adapter.setItem(listDossiers)
        binding?.recyclerViewDashboard?.adapter = adapter

        binding?.recyclerViewDashboard?.addItemDecoration(
                MarginItemDecoration(1)
        )


    }

    fun filterListByEtat(filtre: String) {
        linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerViewDashboard.layoutManager = linearLayoutManager

        val adapter = DossierAdapter { model ->
            parentFragmentManager.let {
                val bundle = bundleOf(model.id.toString() to "id",model.identifiant to "nom")
                DetailsBottomSheetFragment.newInstance(bundle).apply {
                    show(it, tag)
                }
            }
        }
        adapter.setItem(listDossiers.filter { it.etat.equals(filtre) })
        recyclerViewDashboard.adapter = adapter

        recyclerViewDashboard.addItemDecoration(
                MarginItemDecoration(1)
        )

    }
    fun triListBynameAsc() {
        linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerViewDashboard.layoutManager = linearLayoutManager
        val adapter = DossierAdapter { model ->
            parentFragmentManager.let {
                val bundle = bundleOf(model.id.toString() to "id",model.identifiant to "nom")
                DetailsBottomSheetFragment.newInstance(bundle).apply {
                    show(it, tag)
                }
            }
        }

        adapter.setItem(listDossiers.sortedBy { it.identifiant })
        recyclerViewDashboard.adapter = adapter

        recyclerViewDashboard.addItemDecoration(
                MarginItemDecoration(1)
        )

    }
    fun triListBynameDesc() {
        linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerViewDashboard.layoutManager = linearLayoutManager
        val adapter = DossierAdapter { model ->
            parentFragmentManager.let {
                val bundle = bundleOf(model.id.toString() to "id",model.identifiant to "nom")
                DetailsBottomSheetFragment.newInstance(bundle).apply {
                    show(it, tag)
                }
            }
        }

        adapter.setItem(listDossiers.sortedByDescending { it.identifiant })
        recyclerViewDashboard.adapter = adapter

        recyclerViewDashboard.addItemDecoration(
                MarginItemDecoration(1)
        )

    }
    fun triListBydateAsc() {
        linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerViewDashboard.layoutManager = linearLayoutManager
        val adapter = DossierAdapter { model ->
            parentFragmentManager.let {
                val bundle = bundleOf(model.id.toString() to "id",model.identifiant to "nom")
                DetailsBottomSheetFragment.newInstance(bundle).apply {
                    show(it, tag)
                }
            }
        }

        adapter.setItem(listDossiers.sortedBy { SimpleDateFormat("dd-MM-yyyy").parse(it.date) })
        recyclerViewDashboard.adapter = adapter

        recyclerViewDashboard.addItemDecoration(
                MarginItemDecoration(1)
        )

    }
    fun triListBydateDesc() {
        linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerViewDashboard.layoutManager = linearLayoutManager
        val adapter = DossierAdapter { model ->
            parentFragmentManager.let {
                val bundle = bundleOf(model.id.toString() to "id",model.identifiant to "nom")
                DetailsBottomSheetFragment.newInstance(bundle).apply {
                    show(it, tag)
                }
            }
        }

        adapter.setItem(listDossiers.sortedByDescending { SimpleDateFormat("dd-MM-yyyy").parse(it.date) })
        recyclerViewDashboard.adapter = adapter

        recyclerViewDashboard.addItemDecoration(
                MarginItemDecoration(1)
        )

    }





}