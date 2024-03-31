package com.tematihonov.githubtest.di

import android.content.Context
import androidx.room.Room
import com.tematihonov.githubtest.data.local.GitHubTestDao
import com.tematihonov.githubtest.data.local.GitHubTestDataBase
import com.tematihonov.githubtest.utils.RoomConstants.DB_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideGithubTestDB(): GitHubTestDataBase {
        return Room.databaseBuilder(
            context,
            GitHubTestDataBase::class.java,
            DB_NAME,
        ).build()
    }

    @Singleton
    @Provides
    fun provideGithubTestDao(gitHubTestDataBase: GitHubTestDataBase): GitHubTestDao {
        return gitHubTestDataBase.gitHubTestDao()
    }
}