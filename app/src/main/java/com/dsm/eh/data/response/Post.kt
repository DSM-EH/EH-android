package com.dsm.eh.data.response

data class Post(
    val content: String,
    val created_at: String,
    val group: Group,
    val id: Int,
    val is_promotion: Boolean,
    val title: String,
    val writer: Writer
)