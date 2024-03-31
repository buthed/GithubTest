package com.tematihonov.githubtest.di

import com.tematihonov.githubtest.domain.repository.NetworkRepository
import com.tematihonov.githubtest.domain.repository.RoomRepository
import com.tematihonov.githubtest.domain.usecase.LocalUnionUseCase
import com.tematihonov.githubtest.domain.usecase.NetworkUnionUseCase
import com.tematihonov.githubtest.domain.usecase.local.AddUserToFavorites
import com.tematihonov.githubtest.domain.usecase.local.CheckUsersOnContainsInTable
import com.tematihonov.githubtest.domain.usecase.local.DeleteUserFromFavorite
import com.tematihonov.githubtest.domain.usecase.local.GetAllFavoritesUsers
import com.tematihonov.githubtest.domain.usecase.network.GetSearchUsersUseCase
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
            getSearchUsersUsecase = GetSearchUsersUseCase(networkRepository),
            getUserUseCase = GetUserUseCase(networkRepository)
        )
    }

    @Singleton
    @Provides
    fun provideLocalUseCases(roomRepository: RoomRepository): LocalUnionUseCase {
        return LocalUnionUseCase(
            checkUsersOnContainsInTable = CheckUsersOnContainsInTable(roomRepository),
            addUserToFavorites = AddUserToFavorites(roomRepository),
            deleteUserFromFavorite = DeleteUserFromFavorite(roomRepository),
            getAllFavoritesUsers = GetAllFavoritesUsers(roomRepository)
        )
    }
}