package com.pearl.common.retrofit.rest_api_interface

import com.pearl.common.retrofit.data_model_class.AdharRequestData
import com.pearl.common.retrofit.data_model_class.AdharResponseData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AdharApi {
    @Headers("Content-Type: application/json")
    @POST("add-adhar")
    suspend fun saveAdharData(
        @Header("Authorization") token: String,
        @Body requestData: AdharRequestData
    ): Response<AdharResponseData>
}