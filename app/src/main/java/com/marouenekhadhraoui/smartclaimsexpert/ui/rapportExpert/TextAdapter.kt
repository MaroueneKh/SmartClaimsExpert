package com.marouenekhadhraoui.smartclaimsexpert.ui.rapportExpert

import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

class TextAdapter @Inject constructor(): RecyclerView.Adapter<TextHolder>() {
    var list: List<TextModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextHolder {

        return TextHolder(parent)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: TextHolder, position: Int) {
        holder.bind(list[position])

        holder.setIsRecyclable(true)


    }

    fun setItem(list: List<TextModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size




}