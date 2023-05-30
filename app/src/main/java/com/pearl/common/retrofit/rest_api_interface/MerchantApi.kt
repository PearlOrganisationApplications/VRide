package com.pearl.common.retrofit.rest_api_interface

import com.pearl.common.retrofit.data_model_class.MerchantResponse
import com.pearl.common.retrofit.data_model_class.SubmitMerchantsRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MerchantApi {
    @GET("api/merchant-list")
    suspend fun getMerchants(@Header("Authorization") token: String): Response<MerchantResponse>

    @POST("api/add-merchants")
    suspend fun submitMerchants(
        @Header("Authorization") token: String,
        @Body request: SubmitMerchantsRequest
    ): Response<MerchantResponse>
}
