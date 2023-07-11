package com.pearl.v_ride

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pearl.adapter.NearestListAapter
import com.pearl.common.retrofit.data_model_class.Station
import com.pearl.common.retrofit.rest_api_interface.StationApi
import com.pearl.v_ride_lib.BaseClass
import com.pearl.v_ride_lib.Dialog

import com.pearl.v_ride_lib.Global
import com.pearl.v_ride_lib.PrefManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NearestServiceActivity : BaseClass(),NearestListAapter.NearestAdapterCallback {
    private lateinit var recyclerView: RecyclerView
    lateinit var ivback: AppCompatImageView
    lateinit var apptitle: AppCompatTextView
    lateinit var prefManager: PrefManager
    private lateinit var resourcess: Resources
    private lateinit var loadingDialog: Dialog

    //    private lateinit var newArrayList: ArrayList<NearestList>
/*    lateinit var placeName: Array<String>
    lateinit var placeAddress: Array<String*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefManager = PrefManager(this)
        Global.language(this, resources)
        setLayoutXml()
        initializeViews()
        initializeClickListners()

        loadingDialog.startLoadingDialog()
        nearestService()


    }

    override fun onResume() {
        super.onResume()
        registerReceiver(gpsBroadcastReceiver, filter)
        apptitle.text = resourcess.getString(R.string.my_nearest_service)

    }

    override fun setLayoutXml() {
        setContentView(R.layout.activity_nearest_service)
        resourcess = Global.language(this, resources)
    }

    override fun initializeViews() {
        loadingDialog = Dialog(this)
        ivback = findViewById(R.id.ivBack)
        apptitle = findViewById(R.id.titleTVAppbar)

        apptitle.setText(R.string.my_nearest_service)


        recyclerView = findViewById(R.id.nearestRV)


        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun initializeClickListners() {
        ivback.setOnClickListener {
            onBackPressed()
        }
    }

    override fun initializeInputs() {
        TODO("Not yet implemented")
    }

    override fun initializeLabels() {
        TODO("Not yet implemented")
    }

    /*fun nearestService() {

        val bearerToken = prefManager.getToken()// Replace with your actual bearer token

        val retrofit = Retrofit.Builder()
            .baseUrl(Global.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(NearestServiceApi::class.java)

        val call = service.getNearestServiceCenters("Bearer $bearerToken")

        call.enqueue(object : Callback<ServiceResponseData> {
            override fun onResponse(
                call: Call<ServiceResponseData>,
                response: Response<ServiceResponseData>
            ) {
                if (response.isSuccessful) {
                    val responseData = response.body()
                    if (responseData != null) {
                        val serviceCenters = responseData.nearestServiceCenters
                        // Handle the list of service centers as per your requirements
                        val listCard = ArrayList<ServiceCenter>()
                        for (serviceCenter in serviceCenters) {
                            val locationName = serviceCenter.locationName
                            val latitude = serviceCenter.latitude
                            val longitude = serviceCenter.longitude
                            val state = serviceCenter.state
                            val city = serviceCenter.city
                            // Process the data as needed
                            Log.d("Response1", locationName + latitude + longitude)

                            listCard.add(
                                ServiceCenter(
                                    locationName,
                                    latitude,
                                    longitude,
                                    0,
                                    0,
                                    city,
                                    state
                                )
                            )

                        }
                        val recyclerViewAapter = NearestListAapter(context = this@NearestServiceActivity,listCard)
                        recyclerView.adapter = recyclerViewAapter
                    }
                } else {
                    // Handle unsuccessful response
                    val errorResponseCode = response.code()
                    val errorResponseBody = response.errorBody()?.string()
                    // Handle the error response code and body
                    Log.e(
                        "API Error",
                        "Response Code: $errorResponseCode, Body: $errorResponseBody"
                    )
                }
            }

            override fun onFailure(call: Call<ServiceResponseData>, t: Throwable) {
                // Handle failure
                Log.d("failure", t.toString())
            }
        })

    }*/

    fun nearestService() {

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
                    val listCard = ArrayList<Station>()
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
                                        stateName,
                                        /*0,
                                        0.0,
                                        0,
                                        0,
                                        0,
*/
                                    )
                                )
                            }
                        }

                        runOnUiThread {
                            recyclerView.layoutManager = LinearLayoutManager(this@NearestServiceActivity)
                            val recyclerViewAdapter = NearestListAapter(this@NearestServiceActivity, listCard,this@NearestServiceActivity)
                            recyclerView.adapter = recyclerViewAdapter
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

    override fun onPause() {
        super.onPause()
        unregisterReceiver(gpsBroadcastReceiver)
    }

    override fun onCartClicked(stationSerialNumber: String) {

    }

}

/*
lateinit var ivback: AppCompatImageView
lateinit var apptitle: AppCompatTextView

ivback=findViewById(R.id.ivBack)
apptitle = findViewById(R.id.titleTVAppbar)

apptitle.text =title
ivback.setOnClickListener {
    onBackPressed()
}

*/