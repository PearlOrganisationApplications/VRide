package com.pearl.common.retrofit.data_model_class

import com.google.gson.annotations.SerializedName

/*data class StationRes(
    @SerializedName("station_serial_number")
    val stationSerialNumber: String,*//*
    @SerializedName("sunmccu_modelname")
    val sunmccuModelName: String,
    @SerializedName("sunmccu_time")
    val sunmccuTime: Long,
    @SerializedName("sunmccu_recordtype")
    val sunmccuRecordType: String,
    @SerializedName("sunmccu_txidkey")
    val sunmccuTxIdKey: String,
    @SerializedName("sunmccu_data")
    val sunmccuData: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("zone")
    val zone: String,*//*
    @SerializedName("total-swap")
    var totalSwap: Int,
    @SerializedName("UPS-Voltage")
    var upsVoltage: Double,
    @SerializedName("total-bp-count")
    var totalBpCount: Int,
    @SerializedName("total-swap-fail")
    var totalSwapFail: Int,
    @SerializedName("total-swap-successful")
    var totalSwapSuccessful: Int,
)*/
data class StationRes(
    @SerializedName("station_serial_number")
    val stationSerialNumber: String,
    @SerializedName("sunmccu_modelname")
    val sunmccuModelName: String,
    @SerializedName("sunmccu_time")
    val sunmccuTime: Long,
    @SerializedName("sunmccu_recordtype")
    val sunmccuRecordType: String,
    @SerializedName("sunmccu_txidkey")
    val sunmccuTxIdKey: String,
    @SerializedName("sunmccu_data")
    val sunmccuData: StationData
)

data class StationData(
    @SerializedName("total-swap")
    val totalSwap: Int,
    @SerializedName("UPS-Voltage")
    val upsVoltage: Double,
    @SerializedName("total-bp-count")
    val totalBpCount: Int,
    @SerializedName("total-swap-fail")
    val totalSwapFail: Int,
    @SerializedName("total-swap-successful")
    val totalSwapSuccessful: Int
)

data class PostApiRequest(
    @SerializedName("\$query")
    val query: QueryParams
)

data class QueryParams(
    val station_serial_number: String,
    val sunmccu_recordtype: String
)