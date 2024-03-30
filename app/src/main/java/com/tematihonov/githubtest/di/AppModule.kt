package com.tematihonov.githubtest.di

import com.tematihonov.githubtest.data.repositoryImpl.NetworkRepositoryImpl
import com.tematihonov.githubtest.domain.usecase.LocalUnionUseCase
import com.tematihonov.githubtest.domain.usecase.NetworkUnionUseCase
import com.tematihonov.githubtest.presentation.viewmodel.FavoriteViewModel
import com.tematihonov.githubtest.presentation.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class AppModule() {

    @Provides
    fun provideMainViewModel(networkUnionUseCase: NetworkUnionUseCase, localUnionUseCase: LocalUnionUseCase, repository: NetworkRepositoryImpl): MainViewModel {
        return MainViewModel(networkUnionUseCase, localUnionUseCase, repository)
    }

    @Provides
    fun provideFavoriteViewModel(networkUnionUseCase: NetworkUnionUseCase, localUnionUseCase: LocalUnionUseCase): FavoriteViewModel {
        return FavoriteViewModel(networkUnionUseCase, localUnionUseCase)
    }
}