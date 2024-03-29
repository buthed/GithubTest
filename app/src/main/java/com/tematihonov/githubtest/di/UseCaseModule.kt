package com.tematihonov.githubtest.di

import com.tematihonov.githubtest.domain.repository.NetworkRepository
import com.tematihonov.githubtest.domain.usecase.NetworkUnionUseCase
import com.tematihonov.githubtest.domain.usecase.network.GetSearchUsersUsecase
import com.tematihonov.githubtest.domain.usecase.network.GetUserUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun provideNetworkUseCases(networkRepository: NetworkRepository): NetworkUnionUseCase {
        return NetworkUnionUseCase(
            getSearchUsersUsecase = GetSearchUsersUsecase(networkRepository),
            getUserUseCase = GetUserUseCase(networkRepository)
        )
    }
}