package com.tematihonov.githubtest.di

import com.tematihonov.githubtest.data.repositoryImpl.NetworkRepositoryImpl
import com.tematihonov.githubtest.domain.repository.NetworkRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideNetworkRepositoryImpl(repository: NetworkRepositoryImpl): NetworkRepository {
        return repository
    }
}