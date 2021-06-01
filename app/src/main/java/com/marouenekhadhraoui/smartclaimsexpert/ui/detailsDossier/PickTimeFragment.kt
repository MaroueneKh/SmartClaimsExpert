package com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier

import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.R
import com.marouenekhadhraoui.smartclaimsexpert.databinding.DialogPlanifierTimeBinding
import com.marouenekhadhraoui.smartclaimsexpert.databinding.FragmentDetailsDossierBinding
import javax.inject.Inject

class PickTimeFragment  : Fragment()  {

    private var _binding: DialogPlanifierTimeBinding? = null
    private val binding get() = _binding


    @Inject
    lateinit var logger: Logger





    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.dialog_planifier_time, container, false)
        binding?.lifecycleOwner = this

        return binding?.root
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }












}