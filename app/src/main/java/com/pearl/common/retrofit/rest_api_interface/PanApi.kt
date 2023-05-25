package com.pearl.common.retrofit.rest_api_interface

import com.pearl.common.retrofit.data_model_class.PanRequestData
import com.pearl.common.retrofit.data_model_class.PanResponseData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface PanApi {
    @Headers("Content-Type: application/json")
    @POST("api/add-pan")
    suspend fun savePanData(
        @Header("Authorization") token: String,
        @Body requestData: PanRequestData
    ): Response<PanResponseData>
}
