package com.pearl.common.retrofit.rest_api_interface

import com.pearl.common.retrofit.data_model_class.ResponseData
import com.pearl.common.retrofit.data_model_class.ServiceRequestData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ServiceApi {
    @POST("service-request")
    fun sendRequest(
        @Header("Authorization") token: String,
        @Body requestData: ServiceRequestData
    ): Call<ResponseData>
}