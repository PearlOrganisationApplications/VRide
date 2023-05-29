package com.pearl.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.pearl.common.retrofit.data_model_class.ServiceCenter
import com.pearl.v_ride.R


class NearestListAapter(private val context: Context, private  val nearestList: ArrayList<ServiceCenter>):
    RecyclerView.Adapter<NearestListAapter.MyViewHolder>() {


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val title = itemView.findViewById<TextView>(R.id.placeName)
        val cityName = itemView.findViewById<TextView>(R.id.placeAddress)
        val stateName = itemView.findViewById<TextView>(R.id.stateName)
        val navigateMap = itemView.findViewById<ImageView>(R.id.navigateMap)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.nearest_service_list,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = nearestList[position]
        holder.title.text =currentItem.locationName
        holder.cityName.text = currentItem.city
        holder.stateName.text = currentItem.state

        holder.navigateMap.setOnClickListener {
           val lat = currentItem.latitude
           val lng = currentItem.latitude
            val label =""
                 holder.title.toString()

//            val geoUri = "http://maps.google.com/maps?q=loc:$lat,$lng + ${holder.title} "
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(geoUri))
//            startActivity(,Intent(Intent.ACTION_VIEW, Uri.parse(geoUri)))

//            val gmmIntentUri = Uri.parse("geo:$lat,$lng?q=$lat,$lng(${holder.title})")
            val geoUri =
                "http://maps.google.com/maps?q=loc:$lng,$lat($label) "
            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(geoUri))
//            mapIntent.setPackage("com.google.android.apps.maps") // Specify the package to ensure opening in Google Maps app

            // Check if Google Maps app is available
            if (mapIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(mapIntent)
            } else {
                // Handle the case when Google Maps app is not installed
                // You can redirect the user to the Play Store to download the app, or use a different approach
            }


        }

    }

    override fun getItemCount(): Int {
        return  nearestList.size
    }
}