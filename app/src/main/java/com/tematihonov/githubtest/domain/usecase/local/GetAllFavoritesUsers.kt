package com.tematihonov.githubtest.domain.usecase.local

import com.tematihonov.githubtest.data.local.FavoritesUserEntity
import com.tematihonov.githubtest.domain.repository.RoomRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoritesUsers @Inject constructor(
    private val roomRepository: RoomRepository
) {

    fun invoke(): Flow<List<FavoritesUserEntity>> {
        return roomRepository.getAllFavoritesUsers()
    }
}