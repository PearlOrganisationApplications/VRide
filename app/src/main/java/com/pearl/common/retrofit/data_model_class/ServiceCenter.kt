package com.pearl.common.retrofit.data_model_class

import com.google.gson.annotations.SerializedName


data class ServiceCenter(
    @SerializedName("location_name")
    val locationName: String,
    val latitude: String,
    val longitude: String,
    @SerializedName("state_id")
    val stateId: Int,
    @SerializedName("city_id")
    val cityId: Int,
    val city: String,
    val state: String
)
data class ServiceResponseData(
    @SerializedName("nearest service center")
    val nearestServiceCenters: List<ServiceCenter>
)
