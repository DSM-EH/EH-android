package com.dsm.eh.data.api

import com.dsm.eh.data.request.user.UserProfileEditRequest
import com.dsm.eh.data.request.user.UserSignUpRequest
import com.dsm.eh.data.response.user.UserProfileResponse
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface UserApi {
    @Multipart
    @POST("user")
    fun userSignUp(
        @Part("email") email: String,
        @Part("password") password: String,
        @Part("nickname") nickname: String,
        @Part("description") description: String,
        @Part("Profile_image_url") images: MultipartBody.Part
    ): Call<ResponseBody>

    @GET("user")
    fun userProfile(@Query("email") email: String): Call<UserProfileResponse>

    @PATCH("user")
    fun userProfileEdit(@Body body: UserProfileEditRequest): Call<ResponseBody>
}