package com.tematihonov.githubtest.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tematihonov.githubtest.data.local.FavoritesUserEntity
import com.tematihonov.githubtest.domain.models.responseUser.ResponseUser
import com.tematihonov.githubtest.domain.usecase.LocalUnionUseCase
import com.tematihonov.githubtest.domain.usecase.NetworkUnionUseCase
import com.tematihonov.githubtest.utils.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val networkUnionUseCase: NetworkUnionUseCase,
    private val localUnionUseCase: LocalUnionUseCase,
) : ViewModel() {

    val currentUser = MutableLiveData<Resource<ResponseUser>>()

    val responseFavoritesUsers = MutableLiveData<Resource<List<FavoritesUserEntity>>>()

    init {
        getAllFavoritesUsers()
    }

    private fun getAllFavoritesUsers() {
        viewModelScope.launch {
            localUnionUseCase.getAllFavoritesUsers.invoke().onStart {
                responseFavoritesUsers.postValue(Resource.Loading())
            }.catch {
                responseFavoritesUsers.postValue(it.message?.let { it1 -> Resource.Error(it1) })
            }.collect {
                responseFavoritesUsers.postValue(Resource.Success(it))
            }
        }
    }

    fun deleteFromFavorites(userLogin: String) {
        viewModelScope.launch {
            localUnionUseCase.deleteUserFromFavorite.invoke(userLogin)
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

    fun deleteFromFavorite(user: FavoritesUserEntity) {
        viewModelScope.launch {
            localUnionUseCase.deleteUserFromFavorite.invoke(user.login)
        }
    }
}