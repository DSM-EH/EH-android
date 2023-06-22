package com.dsm.eh.data.response

data class Group(
    val background_image: String,
    val description: String,
    val id: Int,
    val max_people: Int,
    val owner: Owner,
    val poster_image: String,
    val profile_image: String,
    val set_time: String,
    val title: String
)