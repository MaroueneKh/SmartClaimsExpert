package com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.visiodialogs

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels

import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.R
import com.marouenekhadhraoui.smartclaimsexpert.databinding.DialogPlanifierDateBinding
import com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier.PlanifierVisioModelView
import kotlinx.android.synthetic.main.dialog_planifier_date.*
import java.util.*


import javax.inject.Inject

class PickDateFragment : Fragment() {


    private val viewModel: PlanifierVisioModelView by activityViewModels()

    private var _binding: DialogPlanifierDateBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var logger: Logger


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.dialog_planifier_date, container, false)
        binding?.lifecycleOwner = this

        return binding?.root
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val today = Calendar.getInstance()
        datePicker1.minDate = System.currentTimeMillis()
        datePicker1.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
           viewModel.date.postValue("$year/$month/$day")

        }


    }












}