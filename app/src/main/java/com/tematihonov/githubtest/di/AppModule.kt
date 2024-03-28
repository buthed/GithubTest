package com.tematihonov.githubtest.di

import com.tematihonov.githubtest.domain.usecase.NetworkUnionUseCase
import com.tematihonov.githubtest.presentation.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideMyViewModel(networkUnionUseCase: NetworkUnionUseCase): MainViewModel {
        return MainViewModel(networkUnionUseCase)
    }
}