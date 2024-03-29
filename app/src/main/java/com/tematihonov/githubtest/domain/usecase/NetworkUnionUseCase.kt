package com.tematihonov.githubtest.domain.usecase

import com.tematihonov.githubtest.domain.usecase.network.GetSearchUsersUsecase
import com.tematihonov.githubtest.domain.usecase.network.GetUserUseCase

data class NetworkUnionUseCase(
    val getSearchUsersUsecase: GetSearchUsersUsecase,
    val getUserUseCase: GetUserUseCase
)