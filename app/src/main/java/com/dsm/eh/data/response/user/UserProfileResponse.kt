package com.dsm.eh.data.response.user

import com.google.gson.annotations.SerializedName

data class UserProfileResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("description") val description: String,
    @SerializedName("profile_image_url") val profileImageUrl: String?
)