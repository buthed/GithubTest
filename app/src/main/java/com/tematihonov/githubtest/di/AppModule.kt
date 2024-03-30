package com.tematihonov.githubtest.di

import com.tematihonov.githubtest.domain.usecase.LocalUnionUseCase
import com.tematihonov.githubtest.domain.usecase.NetworkUnionUseCase
import com.tematihonov.githubtest.presentation.viewmodel.FavoriteViewModel
import com.tematihonov.githubtest.presentation.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class AppModule() {

    @Provides
    fun provideMainViewModel(networkUnionUseCase: NetworkUnionUseCase, localUnionUseCase: LocalUnionUseCase): MainViewModel {
        return MainViewModel(networkUnionUseCase, localUnionUseCase)
    }

    @Provides
    fun provideFavoriteViewModel(networkUnionUseCase: NetworkUnionUseCase, localUnionUseCase: LocalUnionUseCase): FavoriteViewModel {
        return FavoriteViewModel(networkUnionUseCase, localUnionUseCase)
    }
}