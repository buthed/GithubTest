package com.tematihonov.githubtest.domain.usecase

import com.tematihonov.githubtest.domain.usecase.network.GetSearchUsersUseCase
import com.tematihonov.githubtest.domain.usecase.network.GetUserUseCase

data class NetworkUnionUseCase(
    val getSearchUsersUsecase: GetSearchUsersUseCase,
    val getUserUseCase: GetUserUseCase
)