package com.marouenekhadhraoui.smartclaimsexpert.ui.rapportExpert

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.marouenekhadhraoui.smartclaimsexpert.R


class TextHolder constructor(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    constructor(parent: ViewGroup) :
            this(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.custom_table_item, parent, false)
            )

    @RequiresApi(Build.VERSION_CODES.M)
    fun bind(dossier: TextModel) {





    }



}