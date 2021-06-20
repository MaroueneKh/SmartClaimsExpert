package com.marouenekhadhraoui.smartclaimsexpert.ui.videovisio

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.R
import com.marouenekhadhraoui.smartclaimsexpert.databinding.VideoVisioRecordedFragmentBinding
import com.marouenekhadhraoui.smartclaimsexpert.ui.main.VisioActivity
import com.marouenekhadhraoui.smartclaimsexpert.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.video_visio_recorded_fragment.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


@AndroidEntryPoint
class VideoVisioFragment : Fragment() {

    private val viewModel: VideoVisioViewModel by viewModels()

    private var _binding: VideoVisioRecordedFragmentBinding? = null
    private val binding get() = _binding


    @Inject
    lateinit var logger: Logger

    private lateinit var navDirections: NavDirections


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.video_visio_recorded_fragment,
            container,
            false
        )
        binding?.lifecycleOwner = this

        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        getVisio()
        observeVisio()
        getSuivi()






    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        viewModel.getVisio(arguments?.get("id").toString().toInt())
    }

    fun bindViewModel() {
        binding?.viewModel = viewModel
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getVisio() {
        viewModel.getVisio(arguments?.get("id").toString().toInt())
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun getSuivi() {
        viewModel.getSuivi(arguments?.get("id").toString().toInt())
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun observeVisio() {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.visio.collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        logger.log("success")
                        if (it.data!!.isEmpty()) {
                            btnValider.isEnabled = false
                            btnValider.background =
                                resources.getDrawable(R.drawable.button_se_connecter_disabled)
                            btnValider.setTextColor(resources.getColor(R.color.whitedisabled))
                            logger.log("null")
                            binding?.btnVisio?.setOnClickListener(View.OnClickListener {
                                val bundle = bundleOf("id" to arguments?.get("id").toString())
                                setNavDirections(bundle)
                                val navController = findNavController()
                                navController.navigate(navDirections)


                            })
                        } else {
                            if (it.data[0].effectue == 0) {

                                btnVisio.text = getString(R.string.Commencer)
                                btnValider.isEnabled = false
                                btnValider.background =
                                    resources.getDrawable(R.drawable.button_se_connecter_disabled)
                                btnValider.setTextColor(resources.getColor(R.color.whitedisabled))
                                binding?.btnVisio?.setOnClickListener(View.OnClickListener {

                                    startActivityToVisio(VisioActivity(), requireView())

                                })


                            } else if (it.data[0].effectue == 1) {
                                if (it.data[0].resultat.equals("Pret pour reparation"))
                                {
                                    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                                        viewModel.suivi.collect {
                                            when (it.status) {
                                                Status.SUCCESS -> {
                                                    logger.log("success")
                                                    if (it.data!!.isEmpty()) {
                                                        logger.log("suivi non planifieé")
                                                        btnValider.isEnabled = false
                                                        btnValider.background =
                                                            resources.getDrawable(R.drawable.button_se_connecter_disabled)
                                                        btnValider.setTextColor(resources.getColor(R.color.whitedisabled))
                                                        jz_video.visible()
                                                        textVideo.invisible()
                                                        btnVisio.text = getString(R.string.PlanifierSuivi)
                                                        btnVisio.setOnClickListener(View.OnClickListener {
                                                            val bundle = bundleOf("id" to arguments?.get("id").toString())
                                                            setNavDirectionsToPlanifierSuivi(bundle)
                                                            val navController = findNavController()
                                                            navController.navigate(navDirections)
                                                        })
                                                    } else {
                                                        if (it.data[0].effectue == 0)
                                                        {
                                                            logger.log("suivi non planifieé")
                                                            btnValider.isEnabled = false
                                                            btnValider.background =
                                                                resources.getDrawable(R.drawable.button_se_connecter_disabled)
                                                            btnValider.setTextColor(resources.getColor(R.color.whitedisabled))
                                                            jz_video.visible()
                                                            textVideo.invisible()
                                                            btnVisio.text = getString(R.string.CommencerSuivi)
                                                            binding?.btnVisio?.setOnClickListener(View.OnClickListener {
                                                                startActivityToVisio(VisioActivity(), requireView())
                                                            })

                                                        }
                                                        if (it.data[0].effectue == 1)
                                                        {

                                                            if (it.data[0].resultat == "En attente de facture")
                                                            {
                                                                btnVisio.isEnabled = false
                                                                btnVisio.background =
                                                                    resources.getDrawable(R.drawable.button_se_connecter_disabled)
                                                                btnValider.isEnabled = true
                                                                jz_video.visible()
                                                                txt_videoSuivi.visible()
                                                                jz_videoSuivi.visible()
                                                                textVideo.invisible()
                                                                btnVisio.text = getString(R.string.CommencerSuivi)
                                                                txt_facture.visible()
                                                                img_facture.visible()
                                                                textFactureSmart.visible()
                                                                binding?.btnValider?.setOnClickListener(View.OnClickListener {


                                                                })

                                                            }
                                                            else{
                                                                btnVisio.isEnabled = false
                                                                btnVisio.background =
                                                                    resources.getDrawable(R.drawable.button_se_connecter_disabled)
                                                                btnValider.isEnabled = true
                                                                jz_video.visible()
                                                                txt_videoSuivi.visible()
                                                                jz_videoSuivi.visible()
                                                                textVideo.invisible()
                                                                btnVisio.text = getString(R.string.CommencerSuivi)
                                                                binding?.btnValider?.setOnClickListener(View.OnClickListener {
                                                                    //    startActivityToVisio(VisioActivity(), requireView())
                                                                    val bundle = bundleOf("id" to arguments?.get("id").toString())
                                                                    setNavDirectionsToAcquisition(bundle)
                                                                    val navController = findNavController()
                                                                    navController.navigate(navDirections)
                                                                })

                                                            }

                                                        }

                                                        //planifier la visio suivi
                                                    }

                                                }
                                                Status.LOADING -> {
                                                    logger.log("loading")


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
                                else{
                                    logger.log("suivi planifié")
                                    btnVisio.isEnabled = false
                                    btnVisio.background =
                                        resources.getDrawable(R.drawable.button_se_connecter_disabled)
                                    btnVisio.text = getString(R.string.Commencer)
                                    jz_video.visible()
                                    textVideo.invisible()

                                    btnValider.isEnabled = true
                                    btnValider.background =
                                        resources.getDrawable(R.drawable.button_se_connecter)
                                    btnValider.setTextColor(resources.getColor(R.color.white))
                                    setButtonValider()

                                }


                            }
                        }
                    }
                    Status.LOADING -> {
                        logger.log("loading")
                        btnValider.isEnabled = false
                        // binding?.llProgressBar?.progressBar?.visible()
                    }
                    Status.ERROR -> {
                        btnValider.isEnabled = false
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

    fun setButtonValider() {
        btnValider.setOnClickListener {
            val bundle = bundleOf("id" to arguments?.get("id").toString())
            setNavDirectionsTo(bundle)
            val navController = findNavController()
            navController.navigate(navDirections)

        }
    }
    fun setNavDirectionsToPlanifierSuivi(bundle: Bundle) {

        navDirections = object : NavDirections {
            override fun getArguments(): Bundle {
                return bundle
            }

            override fun getActionId(): Int {
                return R.id.action_videoVisioFragment_to_planifierSuiviDialogFragment
            }
        }


    }
    fun setNavDirectionsToAcquisition(bundle: Bundle) {

        navDirections = object : NavDirections {
            override fun getArguments(): Bundle {
                return bundle
            }

            override fun getActionId(): Int {
                return R.id.action_videoVisioFragment_to_demandeAcquisitionDialog
            }
        }


    }

    fun setNavDirections(bundle: Bundle) {

        navDirections = object : NavDirections {
            override fun getArguments(): Bundle {
                return bundle
            }

            override fun getActionId(): Int {
                return R.id.action_videoVisioFragment_to_planifierVisioDialogFragment
            }
        }


    }

    fun setNavDirectionsTo(bundle: Bundle) {

        navDirections = object : NavDirections {
            override fun getArguments(): Bundle {
                return bundle
            }

            override fun getActionId(): Int {
                return R.id.action_videoVisioFragment_to_confirmerReparationDialog
            }
        }


    }

    fun startActivityToVisio(activity: Activity, view: View) {
        val intent = Intent(view.context, activity::class.java)
        val bundle = bundleOf("id" to arguments?.get("id").toString())
        intent.putExtras(bundle)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        intent.action = TO_SIGNIN_OR_SIGNUP
        view.context.startActivity(intent)
    }


}