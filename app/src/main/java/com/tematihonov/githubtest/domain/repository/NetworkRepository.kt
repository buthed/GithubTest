package com.tematihonov.githubtest.domain.repository

import androidx.paging.PagingData
import com.tematihonov.githubtest.domain.models.responseSearch.Item
import com.tematihonov.githubtest.domain.models.responseSearch.ResponseSearch
import com.tematihonov.githubtest.domain.models.responseUser.ResponseUser
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {

    suspend fun getSearchUsers(perPage: Int, page: Int, query: String): ResponseSearch

    suspend fun getUser(userLogin: String): ResponseUser

    fun getPagedUsers(searchBy: String): Flow<PagingData<Item>>
}