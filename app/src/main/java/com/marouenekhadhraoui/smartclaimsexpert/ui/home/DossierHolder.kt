package com.marouenekhadhraoui.smartclaimsexpert.ui.home

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi

import androidx.recyclerview.widget.RecyclerView
import com.marouenekhadhraoui.smartclaimsexpert.R
import kotlinx.android.synthetic.main.fragment_home.view.textView
import kotlinx.android.synthetic.main.layout_dossier_item.view.*

class DossierHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
    constructor(parent: ViewGroup) :
            this(
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.layout_dossier_item, parent, false)
            )

    @RequiresApi(Build.VERSION_CODES.M)
    fun bind(dossier: DossierModel) {

        itemView.textView.text = "marouene"
        itemView.textView2.text = dossier.id.toString()
        itemView.textView3.text = dossier.date.subSequence(0,10)
        itemView.textView4.text = dossier.etat





    }



}