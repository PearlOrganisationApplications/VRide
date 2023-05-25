package com.pearl.common.retrofit.data_model_class

data class AdharRequestData(
    val adhar_front: String,
    val adhar_back: String,
    val adhar_no: String
)

data class AdharResponseData(
    val msg: String,
    val status: String
)