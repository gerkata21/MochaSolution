package com.android.interviewtask.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import  com.android.interviewtask.model.Feature
import  com.android.interviewtask.model.WeatherData
import com.android.interviewtask.databinding.AlertItemBinding
import com.android.interviewtask.utils.getDisplayformatTime
import com.android.interviewtask.utils.loadImagefromUrl

class AlertsAdapter (
    private val listOfAlerts: MutableList<Feature> = mutableListOf()
) : RecyclerView.Adapter<AlertViewHolder>() {

    var onItemClick: ((Feature,Int) -> Unit)? = null

    fun loadData(listOfAlerts: MutableList<Feature>){
        this.listOfAlerts.addAll(listOfAlerts)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder {
        return AlertViewHolder(AlertItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        holder.setAlertData(listOfAlerts[position],position)
        holder.itemView.rootView.setOnClickListener {
            onItemClick?.invoke(listOfAlerts[position],position)
        }
    }

    override fun getItemCount(): Int {
        return listOfAlerts.size
    }
}

class AlertViewHolder(itemView: AlertItemBinding) : RecyclerView.ViewHolder(itemView.root) {
    val alertTitle: TextView = itemView.alertTitle
    val alertStarttime: TextView = itemView.alertStarttime
    val alertEndtime: TextView = itemView.alertEndtime
    val alertSource: TextView = itemView.alertSource
    val alertHeadline: TextView = itemView.alertHeadline
    val alertImage: ImageView = itemView.alertImage

    fun setAlertData(feature: Feature,position:Int) {
        alertTitle.text = feature.properties.event + " (${feature.properties.category})"
        alertHeadline.text = feature.properties.headline

        alertStarttime.text= getDisplayformatTime(feature.properties.onset)
        alertEndtime.text= getDisplayformatTime(feature.properties.expires)
        alertSource.text=feature.properties.senderName
        alertImage.loadImagefromUrl(position)
    }
}