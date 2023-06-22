package com.dsm.eh.data.api

import com.dsm.eh.data.response.Group
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GroupApi {
    @GET("group/mygroup")
    fun groupMyGroup(@Query("email") email: String): Call<ArrayList<Group>>
}