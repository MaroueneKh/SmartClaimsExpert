package com.marouenekhadhraoui.smartclaimsexpert.ui.home.detailsBottomSheet

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.DossierHolder
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.DossierModel
import com.marouenekhadhraoui.smartclaimsexpert.utils.TO_SIGNIN_OR_SIGNUP

class DossierBottomAdapter constructor(private val onItemClicked: (DossierModel) -> Unit) : RecyclerView.Adapter<DossierBottomHolder>() {
    var list: List<DossierModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DossierBottomHolder {



        return DossierBottomHolder(parent)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: DossierBottomHolder, position: Int) {
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