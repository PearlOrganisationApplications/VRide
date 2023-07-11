package com.pearl.common.retrofit.rest_api_interface

import com.pearl.common.retrofit.data_model_class.StationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StationApi {
    @GET("GetAllStations")
    suspend fun getAllStations(
        @Query("subscription-key") subscriptionKey: String
    ): Response<StationResponse>
}

