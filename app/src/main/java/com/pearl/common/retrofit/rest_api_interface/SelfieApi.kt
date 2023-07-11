package com.pearl.common.retrofit.rest_api_interface

import com.pearl.common.retrofit.data_model_class.ResponseData
import com.pearl.common.retrofit.data_model_class.SelfieRequestData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface SelfieApi {
    @Headers("Content-Type: application/json")
    @POST("api/add-profile")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Body selfie: SelfieRequestData
    ): Response<ResponseData>
}