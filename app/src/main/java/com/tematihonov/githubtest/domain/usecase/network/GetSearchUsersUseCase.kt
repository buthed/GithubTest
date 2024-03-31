package com.tematihonov.githubtest.domain.usecase.network

import com.tematihonov.githubtest.domain.models.responseSearch.ResponseSearch
import com.tematihonov.githubtest.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSearchUsersUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
) {
    suspend operator fun invoke(perPage: Int, page: Int, query: String): Flow<ResponseSearch> =
        flow {
            emit(networkRepository.getSearchUsers(perPage, page, query))
        }
}