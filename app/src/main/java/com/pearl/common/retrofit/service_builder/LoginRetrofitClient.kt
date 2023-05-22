/*
package com.pearl.common.retrofit.service_builder

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LoginRetrofitClient {

    public val retrofit = Retrofit.Builder()
        .baseUrl("https://test.pearl-developer.com/vrun/public/") // change this IP for testing by your actual machine IP
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun<T> createUser(service: Class<T>): T{
        return retrofit.create(service)
    }
}*/
