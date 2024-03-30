package com.tematihonov.githubtest.domain.usecase

import com.tematihonov.githubtest.domain.usecase.local.AddSearchUserUseCase
import com.tematihonov.githubtest.domain.usecase.local.AddUserToFavorites
import com.tematihonov.githubtest.domain.usecase.local.CheckUsersOnContainsInTable
import com.tematihonov.githubtest.domain.usecase.local.DeleteUserFromFavorite
import com.tematihonov.githubtest.domain.usecase.local.GetAllFavoritesUsers

data class LocalUnionUseCase(
    val addSearchUser: AddSearchUserUseCase,
    val checkUsersOnContainsInTable: CheckUsersOnContainsInTable,
    val addUserToFavorites: AddUserToFavorites,
    val deleteUserFromFavorite: DeleteUserFromFavorite,
    val getAllFavoritesUsers: GetAllFavoritesUsers
)