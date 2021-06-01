package com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.R
import com.marouenekhadhraoui.smartclaimsexpert.utils.fadeTo
import com.marouenekhadhraoui.smartclaimsexpert.utils.invisible
import com.marouenekhadhraoui.smartclaimsexpert.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_planifier.*
import kotlinx.android.synthetic.main.dialog_planifier.view.*
import javax.inject.Inject
import android.view.LayoutInflater as LayoutInflater1

private const val NUM_PAGES = 3
@AndroidEntryPoint
class PlanifierVisioDialogFragment : DialogFragment() {

    @Inject
    lateinit var logger: Logger

    private lateinit var viewPager: ViewPager2

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

            viewPager.currentItem = viewPager.currentItem + 1

           if (viewPager.currentItem == 2)
           {
               dismiss()
           }
        })

        myview.cancel.setOnClickListener(View.OnClickListener {

              dismiss()
        })
        return myview
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment =
                when (position) {
                    0 -> PickDateFragment()
                    1 -> PickTimeFragment()
                    2->  PlanifierExpertiseFragment()

                    else -> PickDateFragment()
                }
    }

}