package com.pearl.common.retrofit.data_model_class

import com.google.gson.annotations.SerializedName

data class LoginInfo(
     val msg: String,
     val signin: String,
     val profile: String,
     val verification: String,
     //    @SerializedName("otp") val Otp: String?,
)
