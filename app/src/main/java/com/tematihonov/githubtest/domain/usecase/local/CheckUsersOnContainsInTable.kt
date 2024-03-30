package com.tematihonov.githubtest.domain.usecase.local

import android.util.Log
import com.tematihonov.githubtest.domain.repository.RoomRepository
import javax.inject.Inject

class CheckUsersOnContainsInTable @Inject constructor(
    private val roomRepository: RoomRepository
) {

    suspend fun invoke(userLogin: String): Boolean {
        Log.d("GGG","CheckUsersOnContainsInTable - ${roomRepository.checkUsersOnContainsInTable(userLogin).firstOrNull()}")
        val users = roomRepository.checkUsersOnContainsInTable(userLogin).firstOrNull() ?: return false
        return true
    }
}