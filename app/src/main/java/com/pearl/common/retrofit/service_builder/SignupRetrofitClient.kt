/*
package com.pearl.common.retrofit.service_builder

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SignupRetrofitClient {
*/
/*    private var retrofit: Retrofit? = null
    private const val BASE_URL = ""*//*


    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://test.pearl-developer.com/vrun/public") // change this IP for testing by your actual machine IP
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}*/
