package com.dsm.eh.data

import com.dsm.eh.data.api.GroupApi
import com.dsm.eh.data.api.PostApi
import com.dsm.eh.data.api.UserApi
import com.dsm.eh.data.util.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val userApi: UserApi = retrofit.create(UserApi::class.java)
    val groupApi: GroupApi = retrofit.create(GroupApi::class.java)
    val postApi: PostApi = retrofit.create(PostApi::class.java)
}