/*
package com.pearl.common.retrofit.rest_api_service

import com.google.firebase.auth.UserInfo
import com.pearl.common.retrofit.rest_api_interface.SignupApi
import com.pearl.common.retrofit.service_builder.SignupRetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignupRestApiService {
    fun addUser(userData: UserInfo, onResult: (UserInfo?) -> Unit){
        val retrofit = SignupRetrofitClient.buildService(SignupApi::class.java)
        retrofit.addUser(userData).enqueue(
            object : Callback<UserInfo> {
                override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                    val addedUser = response.body()
                    onResult(addedUser)
                }
            }
        )
    }
}*/
