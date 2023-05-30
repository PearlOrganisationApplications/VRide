package com.pearl.common.retrofit.data_model_class

data class Merchant(
    val id: Int,
    val name: String,
//    var isSelected: Boolean = false
)
data class MerchantResponse(
    val merchants: List<Merchant>,
    val msg: String,
    val status: String
)
data class SubmitMerchantsRequest(
    val merchants: List<MutableList<Int>>
)
