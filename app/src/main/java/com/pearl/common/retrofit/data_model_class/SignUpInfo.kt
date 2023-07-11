package com.pearl.common.retrofit.data_model_class

import com.google.gson.annotations.SerializedName

data class SignUpInfo(
   /* @SerializedName("full_name") val fullName: String?,
    @SerializedName("email_id") val emailID: String?,
    @SerializedName("mobile_no") val mobileNo: String?,
    @SerializedName("dob") val dob: String?,*/
    val msg: String,
    val status: String,
    val token: String,
    val device_token:String
)
