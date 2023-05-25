package com.pearl.common.retrofit.data_model_class

data class AddressRequestData(
    val city: String,
    val state: String,
    val address: String,
    val pincode: String,
    val address_proof: String
)
