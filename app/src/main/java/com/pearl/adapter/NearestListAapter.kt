package com.pearl.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.pearl.common.retrofit.data_model_class.PostApiRequest
import com.pearl.common.retrofit.data_model_class.QueryParams
import com.pearl.common.retrofit.data_model_class.Station
import com.pearl.common.retrofit.data_model_class.StationRes
import com.pearl.common.retrofit.rest_api_interface.StationApiService
import com.pearl.v_ride.R
import com.pearl.v_ride_lib.PrefManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NearestListAapter(private val context: Context, private val nearestList: ArrayList<Station>, private val stationRes: ArrayList<StationRes> , val callback: NearestAdapterCallback):
    RecyclerView.Adapter<NearestListAapter.MyViewHolder>() {

    var prefManager: PrefManager = PrefManager(context)
    var isDetaisVisible = false
//    val stationRes: ArrayList<StationRes> = ArrayList()

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val title = itemView.findViewById<TextView>(R.id.placeName)
        val cityName = itemView.findViewById<TextView>(R.id.placeAddress)
        val stateName = itemView.findViewById<TextView>(R.id.stateName)
        val navigateMap = itemView.findViewById<ImageView>(R.id.navigateMap)
        val listCard = itemView.findViewById<CardView>(R.id.listCard)
        val detailMoreLL = itemView.findViewById<LinearLayout>(R.id.detailMoreLL)
        val showMore = itemView.findViewById<TextView>(R.id.showMore)

        // New TextViews for additional fields
        val totalSwapTextView = itemView.findViewById<TextView>(R.id.totalSwapTextView)
        val upsVoltageTextView = itemView.findViewById<TextView>(R.id.upsVoltageTextView)
        val totalBpCountTextView = itemView.findViewById<TextView>(R.id.totalBpCountTextView)
        val totalSwapFailTextView = itemView.findViewById<TextView>(R.id.totalSwapFailTextView)
        val totalSwapSuccessfulTextView = itemView.findViewById<TextView>(R.id.totalSwapSuccessfulTextView)


        fun bind(stationSerialNumber: String){
            itemView.setOnClickListener{

            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.nearest_service_list,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = nearestList[position]
        val stationItem: StationRes? = if (position < stationRes.size) stationRes[position] else null
        holder.title.text =currentItem.serviceLocation
        holder.cityName.text = currentItem.stationInstallerName
        holder.stateName.text = currentItem.zone

    /*    if (stationItem != null) {
            holder.totalSwapTextView.text = stationItem.totalSwap.toString()
            holder.totalSwapFailTextView.text = stationItem.totalSwapFail.toString()
            holder.totalBpCountTextView.text = stationItem.totalBpCount.toString()
            holder.totalSwapSuccessfulTextView.text = stationItem.totalSwapSuccessful.toString()
            holder.upsVoltageTextView.text = stationItem.upsVoltage.toString()
        } else {
            // Handle the case when stationItem is null or not available
            holder.totalSwapTextView.text = "-"
            holder.totalSwapFailTextView.text = "-"
            holder.totalBpCountTextView.text = "-"
            holder.totalSwapSuccessfulTextView.text = "-"
            holder.upsVoltageTextView.text = "-"
        }*/

        holder.showMore.setOnClickListener {
            if (!isDetaisVisible) {
                val stationSerialNumber = currentItem.stationSerialNumber
                prefManager.setStationSerialNumber(stationSerialNumber)
                holder.showMore.setText(R.string.show_less)
                holder.detailMoreLL.visibility = View.VISIBLE
                Toast.makeText(context,stationSerialNumber,Toast.LENGTH_SHORT).show()
                var res = listOf<StationRes>()
                callback.onCartClicked(stationSerialNumber) { result ->
                    res=result
                    Log.d("ResResult",res.toString())
                    // Handle the result here
                    // For example, update UI or perform further processing
                    // with the received list of StationRes objects
                    // ...
                    for(response in res){
//                    val totalSwap = response.totalSwap
                        holder.totalSwapTextView.text = response.totalSwap.toString()
                        holder.totalSwapFailTextView.text = response.totalSwapFail.toString()
                        holder.totalBpCountTextView.text = response.totalBpCount.toString()
                        holder.totalSwapSuccessfulTextView.text = response.totalSwapSuccessful.toString()
                        holder.upsVoltageTextView.text = response.upsVoltage.toString()

                    }
                }
                Log.d("AdapterRes",res.toString())

                Log.d("AdapterRes1",res.toString())
            } else {
                holder.detailMoreLL.visibility = View.GONE
                holder.showMore.setText(R.string.show_more)
            }
            isDetaisVisible = !isDetaisVisible
        }

        /*
        holder.showMore.setOnClickListener {
            if (!isDetaisVisible) {
                val stationSerialNumber = currentItem.stationSerialNumber
                prefManager.setStationSerialNumber(stationSerialNumber)
                holder.showMore.setText(R.string.show_less)
                holder.detailMoreLL.visibility = View.VISIBLE
                callback.onCartClicked(stationSerialNumber)
            }else{
                holder.detailMoreLL.visibility = View.GONE
                holder.showMore.setText(R.string.show_more)
            }
            isDetaisVisible = !isDetaisVisible
        }*/

        holder.navigateMap.setOnClickListener {
           val lat = currentItem.latitude
           val lng = currentItem.longitude
            val label = holder.title.toString()

            val geoUri =
                "http://maps.google.com/maps?q=loc:$lat,$lng"
            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(geoUri))

            // Check if Google Maps app is available
            if (mapIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(mapIntent)
            } else {

            }


        }

    }

    override fun getItemCount(): Int {
        return  nearestList.size
    }

    interface NearestAdapterCallback{
        fun onCartClicked(stationSerialNumber: String, callback: (List<StationRes>) -> Unit)
    }
}


/*
fun getBP(stationSerialNumber: String) {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://stationapiserver.azurewebsites.net/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService = retrofit.create(StationApiService::class.java)

    val queryParams = QueryParams(
        station_serial_number = stationSerialNumber,
        sunmccu_recordtype = "STATION-HEARTBEAT"
    )

    val request = PostApiRequest(query = queryParams)
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = apiService.getBPAvailability(request)
            if (response.isSuccessful) {
                val result = response.body()
                val totalSwap = result?.totalSwap
                val totalSwapFail = result?.totalSwapFail
                val totalBPCount = result?.totalBpCount
                val totalSwapScuccesful = result?.totalSwapSuccessful
                val upsVoltage = result?.upsVoltage

                // Update UI with the retrieved data
                holder.totalSwapTextView.text = totalSwap.toString()
                holder.totalSwapFailTextView.text = totalSwapFail.toString()
                holder.totalBpCountTextView.text = totalBPCount.toString()
                holder.totalSwapSuccessfulTextView.text = totalSwapScuccesful.toString()
                holder.upsVoltageTextView.text = upsVoltage.toString()


                Log.d("dkjlfkds", response.message())
                Log.d("response", result.toString())
            } else {
                // Handle error case
                val errorBody = response.errorBody().toString()
                // Handle the error body if needed
            }
        } catch (e: Exception) {
            // Handle exception
            e.printStackTrace()
        }
    }
}*/
