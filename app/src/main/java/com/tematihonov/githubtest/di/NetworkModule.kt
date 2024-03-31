package com.tematihonov.githubtest.di

import com.tematihonov.githubtest.data.network.ApiService
import com.tematihonov.githubtest.data.repositoryImpl.NetworkRepositoryImpl
import com.tematihonov.githubtest.utils.RetrofitConstants.BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideNetworkRepositoryImpl(
        apiService: ApiService,
    ) = NetworkRepositoryImpl(apiService)

    @Singleton
    @Provides
    fun provideRetrofit(): ApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiService::class.java)
    }
}