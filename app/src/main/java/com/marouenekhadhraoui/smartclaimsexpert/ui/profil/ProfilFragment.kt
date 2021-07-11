
package com.marouenekhadhraoui.smartclaimsexpert.ui.profil

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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.R
import com.marouenekhadhraoui.smartclaimsexpert.databinding.FragmentProfilBinding
import com.marouenekhadhraoui.smartclaimsexpert.ui.signin.SignInActivity
import com.marouenekhadhraoui.smartclaimsexpert.utils.Status
import com.marouenekhadhraoui.smartclaimsexpert.utils.TO_SIGNIN_OR_SIGNUP
import com.marouenekhadhraoui.smartclaimsexpert.utils.internetErr
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profil.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class ProfilFragment : Fragment() {


    private lateinit var binding: FragmentProfilBinding


    private val profilViewModel: ProfilViewModel by activityViewModels()


    @Inject
    lateinit var logger: Logger


    private lateinit var navDirections: NavDirections



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profil, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        setButtons()
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
                            textView12.text = it.data[0].identifiant


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

    fun setNavDirections() {

        val bundle = bundleOf("date" to "dd")

        navDirections = object : NavDirections {
            override fun getArguments(): Bundle {
                return bundle
            }

            override fun getActionId(): Int {
                return R.id.action_profilFragment_to_detailsProfilFragment
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setButtons() {
        informationsView.setOnClickListener {
            setNavDirections()
            Navigation.findNavController(it).navigate(navDirections)
        }
        deconnexionView.setOnClickListener {
            profilViewModel.saveConnected(requireActivity())
            startActivity(SignInActivity())

        }

    }

    fun startActivity(activity: Activity) {
        val intent = Intent(requireActivity(), activity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        intent.action = TO_SIGNIN_OR_SIGNUP
        requireActivity().startActivity(intent)
        requireActivity().finish()
    }


}