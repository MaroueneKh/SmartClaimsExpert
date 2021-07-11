package com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.suividialogs

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.R
import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.PlanifierVisioModelView
import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.visiodialogs.PickDateFragment
import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.visiodialogs.PlanifierExpertiseFragment
import com.marouenekhadhraoui.smartclaimsexpert.utils.invisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_planifier.view.*
import kotlinx.android.synthetic.main.dialog_suivi_planifier.*
import javax.inject.Inject

private const val NUM_PAGES = 3
@AndroidEntryPoint
class PlanifierSuiviDialogFragment : DialogFragment() {


    private val viewModel: PlanifierSuiviViewModel by activityViewModels()

    @Inject
    lateinit var logger: Logger

    private lateinit var navDirections: NavDirections

    private lateinit var viewPager: ViewPager2

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myview: View = inflater.inflate(R.layout.dialog_suivi_planifier, container, false)
        viewPager = myview.findViewById(R.id.pager)
        // SETUP THE VIEWPAGER AND THE TABLAYOUT HERE
        // USE childFragmentManager
        val pagerAdapter = ScreenSlidePagerAdapter(requireActivity())
        viewPager.adapter = pagerAdapter
        myview.suivant.setOnClickListener(View.OnClickListener {
            when(viewPager.currentItem)
            {
                0-> viewPager.currentItem = viewPager.currentItem + 1
                1->{
                    degatsText.invisible()
                    logger.log("ajouter suivi")
                    logger.log(arguments?.get("id").toString())
                    viewModel.ajouterSuivi(arguments?.get("id").toString().toInt(),1,1,viewModel.date.value!!,viewModel.time.value!!)


                    viewPager.currentItem = viewPager.currentItem + 1


                }
                2-> {
                    val bundle = bundleOf("id" to arguments?.get("id").toString())
                    setNavDirectionsTo(bundle)
                    val navController = findNavController()
                    navController.navigate(navDirections)
                }

            }

        })
        myview.cancel.setOnClickListener(View.OnClickListener {
            dismiss()
        })
        return myview
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
                0 -> PlanifierDateSuiviFragment()
                1 -> PlanifierTimeSuiviFragment()
                2->  PlanifierExpertiseFragment()

                else -> PickDateFragment()
            }
    }
}