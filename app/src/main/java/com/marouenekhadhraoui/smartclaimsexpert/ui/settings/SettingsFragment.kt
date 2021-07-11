package com.marouenekhadhraoui.smartclaimsexpert.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer

import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.R
import com.marouenekhadhraoui.smartclaimsexpert.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SettingsFragment : Fragment() {


    private lateinit var binding: FragmentSettingsBinding


    private val settingsViewModel: SettingsViewModel by activityViewModels()


    @Inject
    lateinit var logger: Logger

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        binding.imgArrow2.isSelected = false

        val darkObserver = Observer<Boolean> { darkmode ->
            binding.imgArrow2.isSelected = darkmode
            //  AppCompatDelegate.setDefaultNightMode(defaultMode)
        }
        settingsViewModel.isdarkenabled.observe(viewLifecycleOwner, darkObserver)



        binding.imgArrow2.setOnCheckedChangeListener { compoundButton, b ->
            logger.log("set on checked")
            val defaultMode = if (b) {

                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(defaultMode)
        }


    }

    fun bindViewModel() {
        binding.viewModel = settingsViewModel
    }


}