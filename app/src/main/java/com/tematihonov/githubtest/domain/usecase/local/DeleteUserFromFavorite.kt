package com.tematihonov.githubtest.domain.usecase.local

import com.tematihonov.githubtest.domain.repository.RoomRepository
import javax.inject.Inject

class DeleteUserFromFavorite @Inject constructor(
    private val roomRepository: RoomRepository,
) {

    suspend fun invoke(userLogin: String) {
        return roomRepository.deleteUserFromFavorite(userLogin)
    }
}