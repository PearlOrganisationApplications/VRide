package com.pearl.common.retrofit.rest_api_interface

import com.pearl.common.retrofit.data_model_class.KotlinShiftDM
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ShiftApiInterface {
    @GET("api/user-shifts")
    public fun getShifts(@Header("Authorization") token: String): Call<KotlinShiftDM>
}