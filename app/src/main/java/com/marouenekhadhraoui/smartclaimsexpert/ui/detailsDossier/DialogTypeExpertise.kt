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
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_type_expertise.*
import kotlinx.android.synthetic.main.dialog_type_expertise.view.*
import javax.inject.Inject
import android.view.LayoutInflater as LayoutInflater1


@AndroidEntryPoint
class DialogTypeExpertise : DialogFragment() {

    @Inject
    lateinit var logger: Logger


    private lateinit var navDirections: NavDirections
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater1,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myview: View = inflater.inflate(R.layout.dialog_type_expertise, container, false)

        myview.btnSuivant.setOnClickListener(View.OnClickListener {

           val bundle = bundleOf("id" to arguments?.get("id").toString())
            setNavDirections(bundle)
            val navController = findNavController()
            navController.navigate(navDirections)

        })


        return myview
    }

    fun setNavDirections(bundle: Bundle) {

        navDirections = object : NavDirections {
            override fun getArguments(): Bundle {
                return bundle
            }

            override fun getActionId(): Int {
                return R.id.action_dialogTypeExpertise_to_videoVisioFragment
            }
        }


    }

}