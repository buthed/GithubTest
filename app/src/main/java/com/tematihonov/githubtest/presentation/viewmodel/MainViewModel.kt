package com.tematihonov.githubtest.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tematihonov.githubtest.domain.usecase.NetworkUnionUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val networkUnionUseCase: NetworkUnionUseCase,
) : ViewModel() {


    init {
        testLog()
    }

    fun testLog() {
        viewModelScope.launch {
            networkUnionUseCase.getSearchUsersUsecase.invoke("buthed").collect {
                Log.d("GGG", "${it.items.get(0).login}")
            }
        }
    }
}