package com.pearl.common.retrofit.rest_api_interface

import com.pearl.common.retrofit.data_model_class.PostApiRequest
import com.pearl.common.retrofit.data_model_class.StationRes
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface StationApiService {
    @POST("stationpostgresapi/sm_wheeler_station_twin/get")
    suspend fun getBPAvailability(@Body request: PostApiRequest): Response<List<StationRes>>
}