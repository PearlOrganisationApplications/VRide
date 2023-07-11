package com.pearl.common.retrofit.data_model_class

data class AddressRequestData(
    val city: String,
    val state: String,
    val landmark: String,
    val address: String,
    val pincode: String,
    val address_proof: String,
    val ref1_name: String,
    val ref1_number: String,
    val ref2_name: String,
    val ref2_number: String
)
/*
"city,state,address,pincode,address_proof(b64),landmark,
ref1_name,ref1_number,ref2_name,ref2_number,"*/
