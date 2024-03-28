package com.tematihonov.githubtest.data.models.responseUser

data class ResponseUser(
    val avatarUrl: String,
    val bio: String,
    val company: Any,
    val createdAt: String,
    val email: Any,
    val followers: Int,
    val following: Int,
    val id: Int,
    val login: String,
    val name: String,
)