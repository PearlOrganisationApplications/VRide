package com.pearl.common.retrofit.rest_api_interface

import com.pearl.common.retrofit.data_model_class.AddressRequestData
import com.pearl.common.retrofit.data_model_class.ResponseData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AddressApi {
    @Headers("Content-Type: application/json")
    @POST("add-address")
    suspend fun updateProfileData(
        @Header("Authorization") token: String,
        @Body requestData: AddressRequestData
    ): Response<ResponseData>
}