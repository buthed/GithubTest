package com.tematihonov.githubtest.domain.models.responseUser

data class ResponseUser(
    val avatar_url: String,
    val bio: Any,
    val company: String?,
    val created_at: String,
    val email: Any?,
    val followers: Int,
    val following: Int,
    val id: Int,
    val login: String,
    val name: String,
)