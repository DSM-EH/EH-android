package com.dsm.eh.data.request.post

data class PostCreateRequest(
    val user_id: Int,
    val group_id: Int,
    val title: String,
    val content: String,
    val is_promotion: Boolean
)
