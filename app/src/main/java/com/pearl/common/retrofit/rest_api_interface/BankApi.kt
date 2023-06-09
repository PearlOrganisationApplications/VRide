package com.pearl.common.retrofit.rest_api_interface

import com.pearl.common.retrofit.data_model_class.BankRequestData
import com.pearl.common.retrofit.data_model_class.ResponseData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface BankApi {
    @Headers("Content-Type: application/json")
    @POST("api/add-bank")
    suspend fun updateBankData(
        @Header("Authorization") token: String,
        @Body requestData: BankRequestData
    ): Response<ResponseData>
}