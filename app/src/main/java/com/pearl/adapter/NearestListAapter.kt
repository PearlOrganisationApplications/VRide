package com.pearl.adapter

import android.view.LayoutInflater
import android.view.View
import com.pearl.v_ride_lib.Global
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pearl.data.NearestList
import com.pearl.test5.R

class NearestListAapter(private  val nearestList: ArrayList<NearestList>):
    RecyclerView.Adapter<NearestListAapter.MyViewHolder>() {


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val title = itemView.findViewById<TextView>(R.id.placeName)
        val place_address = itemView.findViewById<TextView>(R.id.placeAddress)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.nearest_service_list,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = nearestList[position]
        holder.title.text =currentItem.place_name
        holder.place_address.text = currentItem.place_address

    }

    override fun getItemCount(): Int {
        return  nearestList.size
    }
}