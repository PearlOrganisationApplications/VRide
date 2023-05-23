package com.pearl.common.retrofit.rest_api_interface

import com.google.firebase.auth.UserInfo
import com.pearl.common.retrofit.data_model_class.SignUpInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SignupApi {
    @Headers("Content-Type: application/json")
    @POST("api/signup")
    fun addUser(@Body userData: Map<String,String>): Call<SignUpInfo>

}