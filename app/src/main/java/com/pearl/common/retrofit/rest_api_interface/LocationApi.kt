package com.pearl.common.retrofit.rest_api_interface

import com.pearl.common.retrofit.data_model_class.LocationRequest
import com.pearl.common.retrofit.data_model_class.ResponseData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface LocationApi {

        @Headers("Content-Type: application/json")
        @POST("send-location")
        suspend fun updateLocation(
            @Header("Authorization") token: String,
            @Body requestData: LocationRequest
        ): Response<ResponseData>

}