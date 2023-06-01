package com.pearl.common.retrofit.data_model_class

import com.google.gson.annotations.SerializedName

data class ProfileData(
    @SerializedName("msg") val message: String?,
    @SerializedName("profile_data") val profileData: Profile?,
    @SerializedName("other_details") val otherDetails: OtherDetails?,
    @SerializedName("merchants") val merchants: List<Merchant>?,
    @SerializedName("status") val status: String?
)

data class Profile(

    @SerializedName("mobile") val mobile: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("dob") val dob: String?,
    @SerializedName("address") val address: String?,
    @SerializedName("state") val state: String?,
    @SerializedName("city") val city: String?,
    @SerializedName("pincode") val pincode: String?,
    @SerializedName("adhar_no") val adharNo: String?,
    @SerializedName("profile_pic") val profilePic: String?,
    @SerializedName("adhar_front_pic") val adharFrontPic: String?,
    @SerializedName("adhar_back_pic") val adharBackPic: String?,
    @SerializedName("address_proof_pic") val addressProofPic: String?
)

data class OtherDetails(

    @SerializedName("bank_name") val bankName: String?,
    @SerializedName("account_no") val accountNo: String?,
    @SerializedName("ifsc_code") val ifscCode: String?,
    @SerializedName("pan_no") val panNo: String?,
    @SerializedName("pan_name") val panName: String?,
    @SerializedName("pan_date") val panDate: String?,
    @SerializedName("dl_no") val dlNo: String?,
    @SerializedName("bank_photo") val bankPhoto: String?,
    @SerializedName("pan_photo") val panPhoto: String?,
    @SerializedName("dl_photo") val dlPhoto: String?

)

