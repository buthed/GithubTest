package com.tematihonov.githubtest.domain.usecase.local

import com.tematihonov.githubtest.data.local.SearchUserEntity
import com.tematihonov.githubtest.domain.repository.RoomRepository
import javax.inject.Inject

class AddSearchUserUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {

    suspend fun invoke(searchUser: SearchUserEntity) {
        return roomRepository.addUserToSearh(searchUser)
    }
}