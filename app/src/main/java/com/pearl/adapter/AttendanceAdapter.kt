package com.pearl.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pearl.data.AttendanceList
import com.pearl.test5.R


class AttendanceAdapter(private val attendanceList: ArrayList<AttendanceList> ):
RecyclerView.Adapter<AttendanceAdapter.MyItemViewHolder>()
{
    class MyItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val agentName = itemView.findViewById<TextView>(R.id.agentName)
        val calDate = itemView.findViewById<TextView>(R.id.calenderDate)
        val statusIV = itemView.findViewById<ImageView>(R.id.attendanceStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.calender_list,parent,false)
        return MyItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyItemViewHolder, position: Int) {
        val currentItem = attendanceList[position]
        holder.agentName.text = currentItem.agentName
        holder.calDate.text = currentItem.calDate
        holder.statusIV.setImageResource(currentItem.statusI)
    }

    override fun getItemCount(): Int {
       return attendanceList.size
    }


}