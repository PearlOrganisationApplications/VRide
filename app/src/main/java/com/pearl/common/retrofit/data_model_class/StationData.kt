package com.pearl.common.retrofit.data_model_class

data class StationData(
    val stationSerialNumber: String,
    val sunmccuModelName: String,
    val sunmccuTime: Long,
    val sunmccuRecordType: String,
    val sunmccuTxidKey: String,
    val sunmccuData: SunmccuData,
    val location: String,
    val zone: String
)

data class SunmccuData(
    val upsSoc: Double,
    val upsInput: Double,
    val upsState: Int,
    val commError: Boolean,
    val totalSwap: Int,
    val upsVoltage: Double,
    val energyError: Boolean,
    val safetyError: Boolean,
    val atls012Count: Int,
    val lnmc400Count: Int,
    val lnmc415Count: Int,
    val lnmcl400Count: Int,
    val lnmcl415Count: Int,
    val internetError: Boolean,
    val totalBpCount: Int,
    val upsTemperature: Double,
    val dispenseConfig: List<DispenseConfig>,
    val totalSwapFail: Int,
    val dockDisableInfo: DockDisableInfo,
    val dispensableBpCount: Int,
    val atls012DispenseCount: Int,
    val lnmc400DispenseCount: Int,
    val lnmc415DispenseCount: Int,
    val totalSwapSuccessful: Int,
    val lnmcl400DispenseCount: Int,
    val lnmcl415DispenseCount: Int
)

data class DispenseConfig(
    val soc: SocRange,
    val soh: SohRange,
    val type: String,
    val maxTemp: TempRange,
    val minTemp: TempRange,
    val packVoltage: VoltageRange,
    val kwHrConstant: Double
)

data class SocRange(val gte: Double, val lte: Double)
data class SohRange(val gte: Double)
data class TempRange(val gte: Double, val lte: Double)
data class VoltageRange(val gte: Double)

data class DockDisableInfo(val dockDisableCount: Int, val dockDisableDetails: List<DockDisableDetail>)
data class DockDisableDetail(val battery: String)
