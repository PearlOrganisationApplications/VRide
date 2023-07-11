package com.pearl.v_ride

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pearl.adapter.NearestListAapter
import com.pearl.common.retrofit.data_model_class.PostApiRequest
import com.pearl.common.retrofit.data_model_class.QueryParams
import com.pearl.common.retrofit.data_model_class.Station
import com.pearl.common.retrofit.data_model_class.StationRes
import com.pearl.common.retrofit.rest_api_interface.StationApi
import com.pearl.common.retrofit.rest_api_interface.StationApiService
import com.pearl.v_ride_lib.BaseClass
import com.pearl.v_ride_lib.Dialog
import com.pearl.v_ride_lib.Global
import com.pearl.v_ride_lib.PrefManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SwapCenter : BaseClass(),NearestListAapter.NearestAdapterCallback {

    lateinit var nearestSRV: RecyclerView
    private lateinit var loadingDialog: Dialog
    lateinit var ivback: AppCompatImageView
    lateinit var apptitle: AppCompatTextView
    lateinit var prefManager: PrefManager
    private lateinit var resourcess: Resources
    val listCard = ArrayList<Station>()
    val nearestList = ArrayList<StationRes>()
    lateinit var recyclerViewAdapter: NearestListAapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setLayoutXml()
        initializeViews()
        initializeClickListners()

        loadingDialog.startLoadingDialog()
        nearestSwapCenter()

    }

    override fun setLayoutXml() {
        setContentView(R.layout.activity_swap_center)
        resourcess = Global.language(this, resources)
        loadingDialog = Dialog(this)
        prefManager = PrefManager(this)
        Global.language(this, resources)
        recyclerViewAdapter = NearestListAapter(this,listCard,nearestList,this)

    }

    override fun initializeViews() {
        ivback = findViewById(R.id.ivBack)
        apptitle = findViewById(R.id.titleTVAppbar)

        apptitle.setText(R.string.my_nearest_swap_center)
        nearestSRV= findViewById(R.id.nearestSRV)

        nearestSRV.layoutManager = LinearLayoutManager(this)
        /*val adapter = NearestListAapter(this, listCard, this)
        recyclerView.adapter = adapter*/
    }

    override fun initializeClickListners() {

        ivback.setOnClickListener {
            onBackPressed()
        }
    }

    override fun initializeInputs() {
    }

    override fun initializeLabels() {
        TODO("Not yet implemented")
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(gpsBroadcastReceiver, filter)
        apptitle.text = resourcess.getString(R.string.my_nearest_swap_center)
    }

    fun nearestSwapCenter() {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://smartnetwork-dev.azure-api.net/clone/api/Station/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val stationApi = retrofit.create(StationApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = stationApi.getAllStations("01f8624d6e8e46338d9c4633d1f0491b")
                if (response.isSuccessful) {

                    val stationResponse = response.body()
                    val stations = stationResponse?.stations

                    stations?.let { stationList ->
                        for (station in stationList) {
                            val locationName = station.serviceLocation
                            val latitude = station.latitude
                            val longitude = station.longitude
                            val stationSerialNumber = station.stationSerialNumber
                            val stationTypeId = station.stationTypeId
                            val serviceLocationId = station.serviceLocationId
                            val partnerId = station.partnerId
                            val cityName = station.stationInstallerName ?: ""
                            val stateName = station.zone ?: ""
                            // Handle the values as needed
                            if (latitude != null && longitude != null) {
                                Log.d("Response1", locationName + latitude + "     " + longitude)
                                Log.d("Response3", latitude + "    " + longitude)
                                loadingDialog.dismissDialog()
                                listCard.add(
                                    Station(
                                        stationSerialNumber,
                                        stationTypeId,
                                        serviceLocationId,
                                        partnerId,
                                        latitude,
                                        longitude,
                                        locationName,
                                        cityName,
                                        stateName
                                        /*0,
                                        0.0,
                                        0,
                                        0,
                                        0,*/
                                    )
                                )
                            }
                        }

                        runOnUiThread {
                            nearestSRV.layoutManager = LinearLayoutManager(this@SwapCenter)
                            recyclerViewAdapter = NearestListAapter(this@SwapCenter, listCard,nearestList ,this@SwapCenter)
                            nearestSRV.adapter = recyclerViewAdapter
                        }
                    }
                } else {
                    val errorMessage = response.errorBody()?.string()
                    // Handle the error case
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle any exceptions that occurred during the API request
            }
        }

    }



    fun getBP() {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://stationapiserver.azurewebsites.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(StationApiService::class.java)

        val queryParams = QueryParams(
            station_serial_number = prefManager.getStationSerialNumber(),
            sunmccu_recordtype = "STATION-HEARTBEAT"
        )

        val request = PostApiRequest(query = queryParams)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getBPAvailability(request)
                if (response.isSuccessful) {
                    val result = response.body()

                    Log.d("dkjlfkds",response.message())
                    Log.d("response",result.toString())
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
    }



    override fun onPause() {
        super.onPause()
        unregisterReceiver(gpsBroadcastReceiver)
    }

    override fun onCartClicked(stationSerialNumber: String) {
//        getBP(stationSerialNumber)
        getBP()
    }
}


/*                    val totalSwap = result?.totalSwap
                    val totalSwapFail = result?.totalSwapFail
                    val totalBPCount = result?.totalBpCount
                    val totalSwapScuccesful = result?.totalSwapSuccessful
                    val upsVoltage = result?.upsVoltage*/
// Handle the response here
/*  runOnUiThread{
      Toast.makeText(this@SwapCenter,response.isSuccessful,Toast.LENGTH_SHORT).show()
  }*/