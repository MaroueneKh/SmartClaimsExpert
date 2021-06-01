package com.marouenekhadhraoui.smartclaimsexpert.ui.home.detailsBottomSheet

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.R
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.DossierModel
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.MarginItemDecoration
import com.marouenekhadhraoui.smartclaimsexpert.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bottomsheet.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class DetailsBottomSheetFragment : BottomSheetDialogFragment() {

    private val viewModel: DetailsBottomViewModel by viewModels()
    private lateinit var navDirections: NavDirections

    @Inject
    lateinit var logger: Logger

    var listDossiers: ArrayList<DossierModel> = ArrayList()

    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottomsheet, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setUpViews() {
        // We can have cross button on the top right corner for providing elemnet to dismiss the bottom sheet
        //iv_close.setOnClickListener { dismissAllowingStateLoss() }

        setNom()
        setDetailsAssure()
        observeDossiers()


    }

    fun setNom() {
        val nom: String? = arguments?.get("nom").toString()
        textViewNom.text = nom
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun setDetailsAssure()
    {
        val id  = arguments?.get("id").toString()
        logger.log(id)
        viewModel.detailAssure(id?.toInt()!!)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.detailassure.collect {
                when (it.status) {
                    Status.SUCCESS -> {

                        if (it.data!!.isEmpty()) {


                        } else {
                          //  logger.log(it.data[0].nbeDossiers.toString())
                            Nombre.text = it.data[0].nbeDossiers.toString()
                            Assurance.text = it.data[0].agence

                        }

                    }
                    Status.LOADING -> {



                    }
                    Status.ERROR -> {

                        if (it.message.equals(internetErr)) {

                            //  Snackbar.make(findViewById(R.id.constraint), "Verifier votre connexion", Snackbar.LENGTH_LONG).show()
                        }

                    }
                }
            }
        }



    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun observeDossiers() {
        viewModel.afficherHistorique()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.dossier.collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        logger.log("success")
                        if (it.data!!.isEmpty()) {
                            logger.log("null")
                        } else {
                            llProgressBarForBottom?.invisible()
                            listDossiers = ArrayList(it.data)
                            setAdapter()
                        }

                    }
                    Status.LOADING -> {
                        logger.log("loading")
                        llProgressBarForBottom?.visible()

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
                return R.id.action_bottomSheet_to_detailsDossierFragment
            }
        }


    }

    fun setAdapter() {

        linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerViewDashboard?.layoutManager = linearLayoutManager
        val adapter = DossierBottomAdapter { model ->
            parentFragmentManager.let {
                logger.log("check id")
                logger.log(model.id.toString())
                logger.log(model.identifiant)

                val bundle = bundleOf("id" to model.idDossier.toString())
                setNavDirections(bundle)
                val navController = findNavController()
               navController.navigate(navDirections)
            }
        }
        adapter.setItem(listDossiers)
        recyclerViewDashboard?.adapter = adapter

        recyclerViewDashboard?.addItemDecoration(
                MarginItemDecoration(1)
        )


    }


    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): DetailsBottomSheetFragment {
            val fragment = DetailsBottomSheetFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


}