package com.pearl.common.retrofit.rest_api_interface

import com.pearl.common.retrofit.data_model_class.ServiceResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface NearestServiceApi {

    @GET("api/nearest-service") // Replace with your actual endpoint
    fun getNearestServiceCenters(@Header("Authorization") token: String): Call<ServiceResponseData>
}