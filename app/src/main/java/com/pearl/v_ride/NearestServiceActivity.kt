package com.pearl.v_ride

import android.content.Context
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pearl.adapter.NearestListAapter
import com.pearl.common.retrofit.data_model_class.ServiceCenter
import com.pearl.common.retrofit.data_model_class.ServiceResponseData
import com.pearl.common.retrofit.rest_api_interface.NearestServiceApi

import com.pearl.v_ride_lib.Global
import com.pearl.v_ride_lib.PrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NearestServiceActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    lateinit var ivback: AppCompatImageView
    lateinit var apptitle: AppCompatTextView
    lateinit var prefManager: PrefManager
    private lateinit var resourcess: Resources

    //    private lateinit var newArrayList: ArrayList<NearestList>
/*    lateinit var placeName: Array<String>
    lateinit var placeAddress: Array<String*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefManager = PrefManager(this)
        Global.language(this, resources)
        setContentView(R.layout.activity_nearest_service)
        resourcess = Global.language(this, resources)

        ivback = findViewById(R.id.ivBack)
        apptitle = findViewById(R.id.titleTVAppbar)

        apptitle.setText(R.string.my_nearest_service)
        ivback.setOnClickListener {
            onBackPressed()
        }


        /*   nearestService()
               listCard.add(
                   ServiceCenter(
                       "Dehradoon", "Arahat Bazar Dehradun Dehradun UTTARAKHAND 248001")
               )
               listCard.add(
                   ServiceCenter(
                       "Dehradoon", "Arahat Bazar Dehradun Dehradun UTTARAKHAND 248001")
               )
               listCard.add(
                   ServiceCenter(
                       "Dehradoon", "Arahat Bazar Dehradun Dehradun UTTARAKHAND 248001","",0,0,"","",)
               )
       */
        recyclerView = findViewById(R.id.nearestRV)


        recyclerView.layoutManager = LinearLayoutManager(this)
        nearestService()


    }

    override fun onResume() {
        super.onResume()
        apptitle.text = resourcess.getString(R.string.my_nearest_service)

    }

    fun nearestService() {

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