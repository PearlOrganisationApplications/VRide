package com.pearl.v_ride

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pearl.test5.R

class NotificationAdapter(private val notificationList: ArrayList<NotificationList>):
RecyclerView.Adapter<NotificationAdapter.MyItemViewHolder>(){
    class MyItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        val nTitle = itemView.findViewById<TextView>(R.id.nTitle)
        val nDescription = itemView.findViewById<TextView>(R.id.nDescription)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.notification_list,parent,false)
        return MyItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyItemViewHolder, position: Int) {
        val currentItem = notificationList[position]
        holder.nTitle.text = currentItem.nTitle
        holder.nDescription.text = currentItem.nDescription
    }

    override fun getItemCount(): Int {
       return notificationList.size
    }
}