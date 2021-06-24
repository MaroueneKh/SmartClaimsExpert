package com.marouenekhadhraoui.smartclaimsexpert.ui.rapportExpert

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.R
import com.marouenekhadhraoui.smartclaimsexpert.databinding.RapportExpertFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.form_one_expert.*
import kotlinx.android.synthetic.main.rapport_expert_fragment.*
import kotlinx.android.synthetic.main.rapport_expert_fragment.cancel
import kotlinx.android.synthetic.main.rapport_expert_fragment.pager
import kotlinx.android.synthetic.main.rapport_expert_fragment.suivant
import javax.inject.Inject



private const val NUM_PAGES = 4

@AndroidEntryPoint
class RapportExpertFragment : Fragment() {


    private var _binding: RapportExpertFragmentBinding? = null
    private val binding get() = _binding
    private val viewModel: RapportExpertViewModel by activityViewModels()

    @Inject
    lateinit var logger: Logger

    private lateinit var navDirections: NavDirections


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DataBindingUtil.inflate(inflater, R.layout.rapport_expert_fragment, container, false)
        binding?.lifecycleOwner = this
        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()

        // USE childFragmentManager
        pager.apply {
            adapter = ScreenSlidePagerAdapter(requireActivity())

        }
        when (pager.currentItem) {
            0 ->
            {
         //       suivant.isEnabled = viewModel.isLoginFormValidMediator.value!!
            }
            1 -> {
              //  suivant.isEnabled = viewModelFormOne.isLoginFormValidMediator.value!!
            }
            2 -> {
                val bundle = bundleOf("id" to arguments?.get("id").toString())
                setNavDirectionsTo(bundle)
                val navController = findNavController()
                navController.navigate(navDirections)
            }

        }


        suivant.setOnClickListener {
            when (pager.currentItem) {
                0 ->
                {

                    pager.currentItem = pager.currentItem + 1

                }
                1 -> {
                    logger.log("ajouter suivi")
                    logger.log(arguments?.get("id").toString())
                    //     viewModel.ajouterSuivi(arguments?.get("id").toString().toInt(),1,1,viewModel.date.value!!,viewModel.time.value!!)
                    pager.currentItem = pager.currentItem + 1

                }
                2 -> {
                    val bundle = bundleOf("id" to arguments?.get("id").toString())
                    setNavDirectionsTo(bundle)
                    val navController = findNavController()
                    navController.navigate(navDirections)
                }

            }

        }
        cancel.setOnClickListener(View.OnClickListener {

        })


    }

    fun bindViewModel() {
        binding?.viewModel = viewModel
    }

    fun setNavDirectionsTo(bundle: Bundle) {

        navDirections = object : NavDirections {
            override fun getArguments(): Bundle {
                return bundle
            }

            override fun getActionId(): Int {
                return R.id.action_planifierSuiviDialogFragment_to_videoVisioFragment
            }
        }


    }
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment =
            when (position) {
                0 -> FormOneExpert()
                1 -> FormOneExpert()
                2->  FormThreeExpert()
                3->  FormFourExpert()

                else -> FormOneExpert()
            }
    }
}