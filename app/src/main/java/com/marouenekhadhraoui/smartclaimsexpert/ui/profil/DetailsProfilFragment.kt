package com.marouenekhadhraoui.smartclaimsexpert.ui.profil

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.R
import com.marouenekhadhraoui.smartclaimsexpert.databinding.DetailProfilFragmentBinding
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.DossierAdapter
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.DossierModel
import com.marouenekhadhraoui.smartclaimsexpert.utils.Status
import com.marouenekhadhraoui.smartclaimsexpert.utils.internetErr
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.detail_profil_fragment.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class DetailsProfilFragment : Fragment() {


    private lateinit var binding: DetailProfilFragmentBinding


    private val profilViewModel: DetailsProfilViewModel by activityViewModels()



    @Inject
    lateinit var logger: Logger


    var list: ArrayList<DossierModel> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.detail_profil_fragment, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        observeAssure()


    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun observeAssure() {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            profilViewModel.assure.collect {

                when (it.status) {
                    Status.SUCCESS -> {
                        logger.log("success")
                        if (it.data!!.isEmpty()) {
                            logger.log("null")
                        } else {
                            editText.setText(it.data[0].identifiant)
                            editTextNumero.setText(it.data[0].contrat)
                            editTextMail.setText(it.data[0].mail)
                            editTextAgence.setText(it.data[0].agence)
                            //   editTextID.setText(it.data!![0].id)

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

    fun bindViewModel() {
        binding.viewModel = profilViewModel
    }


}