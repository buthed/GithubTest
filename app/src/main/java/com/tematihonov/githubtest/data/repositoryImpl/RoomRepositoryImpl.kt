package com.tematihonov.githubtest.data.repositoryImpl

import com.tematihonov.githubtest.data.local.FavoritesUserEntity
import com.tematihonov.githubtest.data.local.GitHubTestDao
import com.tematihonov.githubtest.domain.repository.RoomRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val gitHubTestDao: GitHubTestDao,
) : RoomRepository {

    override fun getAllFavoritesUsers(): Flow<List<FavoritesUserEntity>> {
        return gitHubTestDao.getAllFavoritesUsers()
    }

    override suspend fun addUserToFavorite(favoritesUser: FavoritesUserEntity) {
        gitHubTestDao.addUserToFavorite(favoritesUser)
    }

    override suspend fun deleteUserFromFavorite(userLogin: String) {
        gitHubTestDao.deleteUserFromFavorite(userLogin)
    }


    override suspend fun checkUsersOnContainsInTable(userLogin: String): List<FavoritesUserEntity> {
        return gitHubTestDao.checkUsersOnContainsInTable(userLogin)
    }
}