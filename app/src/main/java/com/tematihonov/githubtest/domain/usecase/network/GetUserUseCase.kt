package com.tematihonov.githubtest.domain.usecase.network

import com.tematihonov.githubtest.domain.models.responseUser.ResponseUser
import com.tematihonov.githubtest.domain.repository.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) {
    suspend operator fun invoke(userLogin: String): Flow<ResponseUser> = flow {
        emit(networkRepository.getUser(userLogin = userLogin))
    }
}