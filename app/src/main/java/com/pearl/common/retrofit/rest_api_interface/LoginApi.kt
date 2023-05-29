package com.pearl.common.retrofit.rest_api_interface

import com.google.firebase.auth.UserInfo
import com.pearl.common.retrofit.data_model_class.LoginInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginApi {
    @Headers("Content-Type: application/json")
    @POST("login")
        fun addUser(@Body userData: Map<String,String>): Call<LoginInfo>
}