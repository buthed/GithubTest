package com.tematihonov.githubtest.domain.usecase.network

import com.tematihonov.githubtest.data.models.responseSearch.ResponseSearch
import com.tematihonov.githubtest.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSearchUsersUsecase @Inject constructor(
    private val networkRepository: NetworkRepository
) {
    suspend operator fun invoke(query: String): Flow<ResponseSearch> = flow {
        emit(networkRepository.getSearchUsers("buthed",5,1))
    }
}