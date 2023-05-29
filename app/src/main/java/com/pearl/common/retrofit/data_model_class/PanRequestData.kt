package com.pearl.common.retrofit.data_model_class

data class PanRequestData(
    val pan_image: String,
    val pan_no: String,
    val pan_name: String
)

data class PanResponseData(
    val msg: String,
    val status: String
)