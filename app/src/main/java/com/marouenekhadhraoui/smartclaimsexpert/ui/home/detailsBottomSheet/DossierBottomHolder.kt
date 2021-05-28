package com.marouenekhadhraoui.smartclaimsexpert.ui.home.detailsBottomSheet

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.marouenekhadhraoui.smartclaimsexpert.R
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.DossierModel
import kotlinx.android.synthetic.main.layout_historique_item.view.*

class DossierBottomHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
    constructor(parent: ViewGroup) :
            this(
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.layout_historique_item, parent, false)
            )

    @RequiresApi(Build.VERSION_CODES.M)
    fun bind(dossier: DossierModel) {

        itemView.numeroDossier.text = dossier.id.toString()
        itemView.etatDossier.text = dossier.etat

    }



}