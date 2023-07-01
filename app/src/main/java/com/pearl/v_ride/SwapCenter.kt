package com.pearl.v_ride

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
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
import com.pearl.v_ride_lib.Global
import com.pearl.v_ride_lib.PrefManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SwapCenter : BaseClass() {

    lateinit var nearestSRV: RecyclerView
    private lateinit var loadingDialog: Dialog
    lateinit var ivback: AppCompatImageView
    lateinit var apptitle: AppCompatTextView
    lateinit var prefManager: PrefManager
    private lateinit var resourcess: Resources

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

    }

    override fun initializeViews() {
        ivback = findViewById(R.id.ivBack)
        apptitle = findViewById(R.id.titleTVAppbar)

        apptitle.setText(R.string.my_nearest_swap_center)
        nearestSRV= findViewById(R.id.nearestSRV)

        nearestSRV.layoutManager = LinearLayoutManager(this)
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

    override fun onResume() {
        super.onResume()
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
                                    )
                                )
                            }
                        }

                        runOnUiThread {
                            nearestSRV.layoutManager = LinearLayoutManager(this@SwapCenter)
                            val recyclerViewAdapter = NearestListAapter(this@SwapCenter, listCard)
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
}