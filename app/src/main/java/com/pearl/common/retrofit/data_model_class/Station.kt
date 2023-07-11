package com.pearl.common.retrofit.data_model_class

import com.google.gson.annotations.SerializedName

data class Station(
    @SerializedName("stationSerialNumber") val stationSerialNumber: String,
    @SerializedName("stationTypeId") val stationTypeId: Int,
    @SerializedName("serviceLocationId") val serviceLocationId: Int,
    @SerializedName("partnerId") val partnerId: String?,
    // Add other properties as per the response
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("serviceLocation") val serviceLocation: String,
    // Add city and state name properties
    @SerializedName("stationInstallerName") val stationInstallerName: String?,
    @SerializedName("zone") val zone: String?,

/*
    val totalSwap: Int,
    val upsVoltage: Double,
    val totalBpCount: Int,
    val totalSwapFail: Int,
    val totalSwapSuccessful: Int,*/

)


data class StationResponse(
    @SerializedName("version") val version: String,
    @SerializedName("statusCode") val statusCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("isError") val isError: Boolean,
    @SerializedName("result") val stations: List<Station>
)


