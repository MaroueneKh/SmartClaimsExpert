package com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.R
import com.marouenekhadhraoui.smartclaimsexpert.utils.fadeTo
import com.marouenekhadhraoui.smartclaimsexpert.utils.invisible
import com.marouenekhadhraoui.smartclaimsexpert.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_planifier.*
import javax.inject.Inject


@AndroidEntryPoint
class PlanifierVisioDialogTimeFragment : DialogFragment() {


    private val viewModel: PlanifierVisioViewModel by viewModels()



    @Inject
    lateinit var logger: Logger
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.dialog_planifier_time, null))
                    .setPositiveButton(R.string.Suivant,
                            DialogInterface.OnClickListener { dialog, id ->
                                // sign in the user ...

                            })
                    .setNegativeButton(R.string.Cancel,
                            DialogInterface.OnClickListener { dialog, id ->
                                dismiss()
                            })
            // Add action buttons

            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logger.log("suivant")

    }



}