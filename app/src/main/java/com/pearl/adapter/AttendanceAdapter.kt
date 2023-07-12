package com.pearl.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pearl.common.retrofit.data_model_class.AttendanceList
import com.pearl.common.retrofit.data_model_class.ShiftDM
import com.pearl.v_ride.R


class AttendanceAdapter(private val attendanceList: List<ShiftDM> ):
RecyclerView.Adapter<AttendanceAdapter.MyItemViewHolder>()
{
    class MyItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val inTime = itemView.findViewById<TextView>(R.id.inTime)
        val outTime = itemView.findViewById<TextView>(R.id.outTime)

        val calDate = itemView.findViewById<TextView>(R.id.calenderDate)
        val statusIV = itemView.findViewById<ImageView>(R.id.attendanceStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.calender_list,parent,false)
        return MyItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyItemViewHolder, position: Int) {
        val currentItem = attendanceList[position]
        holder.inTime.text = currentItem.LoginHours
        holder.outTime.text = currentItem.Shift
        holder.calDate.text = currentItem.Store
        //holder.statusIV.setImageResource(currentItem.statusI)
    }

    override fun getItemCount(): Int {
       return attendanceList.size
    }


}