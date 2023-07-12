package com.pearl.common.retrofit.data_model_class

data class KotlinShiftDM(
    var status:String,
    var shifts:List<ShiftDM>,
    var msg:String
)

data class ShiftDM(
    var LoginHours:String,
    var Shift:String,
    var WorkerCode:String,
    var Store:String,
    var date:String,
    var TotalOrders:String,
    var city:String,
    var Vendor:String,
)
