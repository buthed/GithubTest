package com.tematihonov.githubtest.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tematihonov.githubtest.data.models.responseSearch.ResponseSearch
import com.tematihonov.githubtest.data.models.responseUser.ResponseUser
import com.tematihonov.githubtest.domain.usecase.NetworkUnionUseCase
import com.tematihonov.githubtest.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val networkUnionUseCase: NetworkUnionUseCase,
) : ViewModel() {

    val responseSearch = MutableLiveData<Resource<ResponseSearch>>()
    val currentUser = MutableLiveData<Resource<ResponseUser>>()

    fun searchUsers(searchInput: String) {
        if (searchInput.isBlank()) {
            responseSearch.postValue(Resource.Success(ResponseSearch(false, emptyList(), 0)))
        } else {
            viewModelScope.launch {
                networkUnionUseCase.getSearchUsersUsecase.invoke(searchInput).onStart {
                    responseSearch.postValue(Resource.Loading())
                }.catch {
                    responseSearch.postValue(it.message?.let { it1 -> Resource.Error(it1) })
                }.collect {
                    responseSearch.postValue(Resource.Success(it))
                    Log.d("GGG","Current list")
                }
            }
        }
    }

    fun setCurrentUser(userLogin: String) {
        viewModelScope.launch {
            networkUnionUseCase.getUserUseCase.invoke(userLogin).onStart {
                currentUser.postValue(Resource.Loading())
            }.catch {
                currentUser.postValue(it.message?.let { it1 -> Resource.Error(it1) })
            }.collect {
                currentUser.postValue(Resource.Success(it))
                Log.d("GGG", "vm url - ${it.avatar_url}, name - ${it.name}")
                delay(100)
                Log.d("GGG", "vm url - ${it.avatar_url}, name - ${it.name}")
                Log.d("GGG", "${it}")
            }
        }
    }
}