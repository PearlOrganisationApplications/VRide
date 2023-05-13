package com.pearl.adapter

import android.view.LayoutInflater
import android.view.View
import com.pearl.v_ride_lib.Global
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pearl.data.HistoryList
import com.pearl.v_ride.R


class HistoryAdapter(private val historyList: ArrayList<HistoryList>):
    RecyclerView.Adapter<HistoryAdapter.MyItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.history_list,parent,false)
        return MyItemViewHolder(itemView)
    }



    override fun onBindViewHolder(holder: MyItemViewHolder, position: Int) {

        val currentItem = historyList[position]
        holder.histroyImage.setImageResource(currentItem.histroyImage)
        holder.historyTitle.text =currentItem.historyTitle
        holder.historyDec.text =currentItem.historyDec
        holder.historyDate.text = currentItem.historyDate
        holder.historyTime.text = currentItem.historyTime
    }

    override fun getItemCount(): Int {
       return historyList.size
    }

    class MyItemViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        val histroyImage = itemView.findViewById<ImageView>(R.id.history_profile)
        val historyTitle =itemView.findViewById<TextView>(R.id.history_title)
        val historyDec = itemView.findViewById<TextView>(R.id.history_description)
        val historyDate = itemView.findViewById<TextView>(R.id.history_Date)
        val historyTime= itemView.findViewById<TextView>(R.id.history_Time)
    }
}