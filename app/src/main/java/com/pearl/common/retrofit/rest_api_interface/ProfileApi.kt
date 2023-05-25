package com.pearl.common.retrofit.rest_api_interface

import com.pearl.common.retrofit.data_model_class.ProfileData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface ProfileApi {

//    @Headers({"Authorization", "Bearer "+ 33|H2UAYjgfVA4O21n1dxqTNnsXUGHQ8Lu4lOKKpShV})
    @Headers("Content-Type: application/json")
    @GET("get-details")
//    suspend fun getProfileData(): Response<ProfileData>
    fun getProfileData(@Header("Authorization") token: String): Call<ProfileData>

}
