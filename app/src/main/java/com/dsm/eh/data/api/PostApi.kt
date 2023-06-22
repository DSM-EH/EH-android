package com.dsm.eh.data.api

import com.dsm.eh.data.request.post.PostCreateRequest
import com.dsm.eh.data.response.Post
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PostApi {
    @POST("post")
    fun postPost(@Body body: PostCreateRequest):Call<ResponseBody>

    @GET("post")
    fun postGet(@Query("id") id: Int): Call<ArrayList<Post>>
}