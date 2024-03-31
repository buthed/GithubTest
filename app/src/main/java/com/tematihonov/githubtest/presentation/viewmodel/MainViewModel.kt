package com.tematihonov.githubtest.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.tematihonov.githubtest.data.local.FavoritesUserEntity
import com.tematihonov.githubtest.data.repositoryImpl.NetworkRepositoryImpl
import com.tematihonov.githubtest.domain.models.responseSearch.ResponseSearch
import com.tematihonov.githubtest.domain.models.responseUser.ResponseUser
import com.tematihonov.githubtest.domain.usecase.LocalUnionUseCase
import com.tematihonov.githubtest.domain.usecase.NetworkUnionUseCase
import com.tematihonov.githubtest.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val networkUnionUseCase: NetworkUnionUseCase,
    private val localUnionUseCase: LocalUnionUseCase,
    private val repository: NetworkRepositoryImpl,
) : ViewModel() {

    val searchBy = MutableLiveData("")

    val usersFlow = searchBy.asFlow()
        .debounce(500)
        .flatMapLatest {
            repository.getPagedUsers(it)
        }.cachedIn(viewModelScope)


    private val responseSearch = MutableLiveData<Resource<ResponseSearch>>()
    val currentUser = MutableLiveData<Resource<ResponseUser>>()


    fun addOrDeleteFromFavorite(user: FavoritesUserEntity, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = localUnionUseCase.checkUsersOnContainsInTable.invoke(user.login)
            when (result) {
                true -> localUnionUseCase.deleteUserFromFavorite.invoke(user.login)
                false -> localUnionUseCase.addUserToFavorites.invoke(user)
            }
            callback(result)
        }
    } //TODO recheck

    fun testDbFavorites(userLogin: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = localUnionUseCase.checkUsersOnContainsInTable.invoke(userLogin)
            delay(100)
            callback(result)
        }
    }

    fun setSearchBy(value: String) {
        if (this.searchBy.value == value) return
        this.searchBy.value = value
        searchUsers(value)
    }

    fun searchUsers(searchInput: String) {
        if (searchInput.isBlank()) {
            responseSearch.postValue(Resource.Success(ResponseSearch(false, emptyList(), 0)))
        } else {
            viewModelScope.launch {
                networkUnionUseCase.getSearchUsersUsecase.invoke(
                    perPage = 20,
                    page = 1,
                    query = searchInput
                ).onStart {
                    responseSearch.postValue(Resource.Loading())
                }.catch {
                    responseSearch.postValue(it.message?.let { it1 -> Resource.Error(it1) })
                }.collect {
                    responseSearch.postValue(Resource.Success(it))
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
            }
        }
    }
}