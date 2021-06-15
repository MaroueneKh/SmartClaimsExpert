
package com.marouenekhadhraoui.smartclaimsexpert.ui.home

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.R
import com.marouenekhadhraoui.smartclaimsexpert.databinding.FragmentHomeBinding
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

    private lateinit var navDirections: NavDirections
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
       // val customDropDownAdapter = SpinnerFilterCustomAdapter(requireContext(), list)

        //(textField.editText as? AutoCompleteTextView)?.setAdapter(adapter) = customDropDownAdapter
        val items = listOf("En cours d'envoie", "En cours de confirmation", "Validé")
        val adapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_filtres, items)

        (filtreText)?.setAdapter(adapter)
        filtreText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                logger.log("on text changed")
                if (s.toString() == "En cours d'envoie")
                {
                    filterListByEtat("en cours d'envoie")
                }
                else if(s.toString().equals("Validé"))
                {
                    filterListByEtat("Validé")
                }
                else if(s.toString().equals("En cours de confirmation"))
                {
                    filterListByEtat("En cours de confirmation")
                }

            }
        })

    }
    private fun setSpinnerTri() {

        val items = listOf("Nom Ascendant", "Nom Descendant", "Date Ascendant","Date Descendant")
        val adapter = ArrayAdapter(requireContext(), R.layout.custom_spinner_tri, items)

        (triText)?.setAdapter(adapter)



        triText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                logger.log("on text changed")
                if (s.toString() == "Nom Ascendant")
                {
                    triListBynameAsc()
                }
                else if(s.toString().equals("Nom Descendant"))
                {
                    triListBynameDesc()
                }
                else if(s.toString().equals("Date Ascendant"))
                {
                    triListBydateAsc()
                }
                else if(s.toString().equals("Date Descendant"))
                {
                    triListBydateDesc()
                }

            }
        })

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
    fun setNavDirections(bundle: Bundle) {

        navDirections = object : NavDirections {
            override fun getArguments(): Bundle {
                return bundle
            }

            override fun getActionId(): Int {
                return R.id.action_homeFragment_to_bottomSheet
            }
        }


    }
    fun setAdapter() {

        linearLayoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerViewDashboard?.layoutManager = linearLayoutManager

        val adapter = DossierAdapter { model ->
            logger.log("id model to string")
            logger.log(model.id.toString())
            logger.log(model.id.toString())
            logger.log(model.id.toString())
            logger.log(model.id.toString())
            val bundle = bundleOf("id" to model.idDossier.toString(),"nom" to model.identifiant )
            setNavDirections(bundle)
            Navigation.findNavController(requireView()).navigate(navDirections)
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
            val bundle = bundleOf("id" to model.idDossier.toString(),"nom" to model.identifiant )
            setNavDirections(bundle)
            Navigation.findNavController(requireView()).navigate(navDirections)

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
            val bundle = bundleOf("id" to model.idDossier.toString(),"nom" to model.identifiant )
            setNavDirections(bundle)
            Navigation.findNavController(requireView()).navigate(navDirections)

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
            val bundle = bundleOf("id" to model.idDossier.toString(),"nom" to model.identifiant )
            setNavDirections(bundle)
            Navigation.findNavController(requireView()).navigate(navDirections)
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
            val bundle = bundleOf("id" to model.idDossier.toString(),"nom" to model.identifiant )
            setNavDirections(bundle)
            Navigation.findNavController(requireView()).navigate(navDirections)
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
            val bundle = bundleOf("id" to model.idDossier.toString(),"nom" to model.identifiant )
            setNavDirections(bundle)
            Navigation.findNavController(requireView()).navigate(navDirections)
        }

        adapter.setItem(listDossiers.sortedByDescending { SimpleDateFormat("dd-MM-yyyy").parse(it.date) })
        recyclerViewDashboard.adapter = adapter

        recyclerViewDashboard.addItemDecoration(
                MarginItemDecoration(1)
        )

    }





}