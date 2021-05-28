package com.marouenekhadhraoui.smartclaimsexpert.ui.home

import android.app.Activity
import android.app.ProgressDialog.show
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.RecyclerView
import com.marouenekhadhraoui.smartclaimsexpert.utils.TO_SIGNIN_OR_SIGNUP
import javax.inject.Inject
import javax.inject.Singleton


class DossierAdapter constructor(private val onItemClicked: (DossierModel) -> Unit) : RecyclerView.Adapter<DossierHolder>() {
    var list: List<DossierModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DossierHolder {



        return DossierHolder(parent)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: DossierHolder, position: Int) {
        holder.bind(list[position])
        holder.setIsRecyclable(true)
        holder.itemView.setOnClickListener { onItemClicked(list[position]) }


    }

    fun setItem(list: List<DossierModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size




    fun startActivity(activity: Activity, view: View, date: String) {
        val intent = Intent(view.context, activity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        intent.action = TO_SIGNIN_OR_SIGNUP
        intent.putExtra("date", date)
        view.context.startActivity(intent)
    }
}