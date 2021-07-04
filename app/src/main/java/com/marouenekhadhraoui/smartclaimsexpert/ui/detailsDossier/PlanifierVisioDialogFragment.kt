package com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier


import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.R
import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.visiodialogs.PickDateFragment
import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.visiodialogs.PickTimeFragment
import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.visiodialogs.PlanifierExpertiseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_planifier.view.*
import javax.inject.Inject
import android.view.LayoutInflater as LayoutInflater1

private const val NUM_PAGES = 3
@AndroidEntryPoint
class PlanifierVisioDialogFragment : DialogFragment() {


    private lateinit var navDirections: NavDirections
    private val viewModel: PlanifierVisioModelView by activityViewModels()

    @Inject
    lateinit var logger: Logger

    private lateinit var viewPager: ViewPager2

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
            inflater: LayoutInflater1,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val myview: View = inflater.inflate(R.layout.dialog_planifier, container, false)
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
                   viewModel.ajouterVisio(arguments?.get("id").toString().toInt(),1,1,viewModel.date.value!!,viewModel.time.value!!)
                   viewModel.modifierDossier(arguments?.get("id").toString().toInt(),"En attende de visio")


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
        myview.cancel.setOnClickListener {
            dismiss()
        }
        return myview
    }
    fun setNavDirectionsTo(bundle: Bundle) {

        navDirections = object : NavDirections {
            override fun getArguments(): Bundle {
                return bundle
            }

            override fun getActionId(): Int {
                return R.id.action_planifierVisioDialogFragment_to_videoVisioFragment
            }
        }


    }
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment =
                when (position) {
                    0 -> PickDateFragment()
                    1 -> PickTimeFragment()
                    2-> PlanifierExpertiseFragment()

                    else -> PickDateFragment()
                }
    }
}