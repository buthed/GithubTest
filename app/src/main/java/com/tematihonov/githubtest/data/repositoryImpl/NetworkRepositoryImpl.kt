package com.tematihonov.githubtest.data.repositoryImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tematihonov.githubtest.presentation.ui.paginate.UsersPageLoader
import com.tematihonov.githubtest.presentation.ui.paginate.UsersPagingSource
import com.tematihonov.githubtest.data.network.ApiService
import com.tematihonov.githubtest.domain.models.responseSearch.Item
import com.tematihonov.githubtest.domain.models.responseSearch.ResponseSearch
import com.tematihonov.githubtest.domain.models.responseUser.ResponseUser
import com.tematihonov.githubtest.domain.repository.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.http.Query
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): NetworkRepository {

    override suspend fun getSearchUsers(perPage: Int, page: Int, query: String): ResponseSearch {
        return apiService.getSearchUsers(
            perPage, page, query
        )
    }

    override suspend fun getUser(userName: String): ResponseUser {
        return apiService.getUser(userName)
    }

    override fun getPagedUsers(searchBy: String): Flow<PagingData<Item>> {
        val loader: UsersPageLoader = { pageIndex, pageSize ->
            getUsers(pageSize, pageIndex, searchBy)
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UsersPagingSource(loader, PAGE_SIZE) }
        ).flow
    }

    private suspend fun getUsers(perPage: Int, page: Int, query: String): List<Item>
            = withContext(Dispatchers.IO) {

        delay(2000)

        val list: ArrayList<Item> = apiService.getSearchUsers(
            perPage = perPage,
            page = page,
            query = query
        ).items as ArrayList<Item>


        return@withContext list
    }

    private companion object {
        const val PAGE_SIZE = 20
    }
}