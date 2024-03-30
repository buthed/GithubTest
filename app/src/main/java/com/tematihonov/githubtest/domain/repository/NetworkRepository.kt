package com.tematihonov.githubtest.domain.repository

import com.tematihonov.githubtest.domain.models.responseSearch.ResponseSearch
import com.tematihonov.githubtest.domain.models.responseUser.ResponseUser

interface NetworkRepository {

    suspend fun getSearchUsers(query: String, perPage: Int, page: Int, ): ResponseSearch

    suspend fun getUser(userLogin: String): ResponseUser
}