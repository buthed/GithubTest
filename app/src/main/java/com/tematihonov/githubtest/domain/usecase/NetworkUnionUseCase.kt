package com.tematihonov.githubtest.domain.usecase

import com.tematihonov.githubtest.domain.usecase.network.GetSearchUsersUsecase

data class NetworkUnionUseCase(
    val getSearchUsersUsecase: GetSearchUsersUsecase
)