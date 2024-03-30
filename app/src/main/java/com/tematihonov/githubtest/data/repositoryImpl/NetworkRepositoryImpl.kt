package com.tematihonov.githubtest.data.repositoryImpl

import com.tematihonov.githubtest.data.network.ApiService
import com.tematihonov.githubtest.domain.models.responseSearch.ResponseSearch
import com.tematihonov.githubtest.domain.models.responseUser.ResponseUser
import com.tematihonov.githubtest.domain.repository.NetworkRepository
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): NetworkRepository {

    override suspend fun getSearchUsers(query: String, perPage: Int, page: Int): ResponseSearch {
        return apiService.getSearchUsers(
            query, 3, 1
        )
    }

    override suspend fun getUser(userName: String): ResponseUser {
        return apiService.getUser(userName)
    }
}