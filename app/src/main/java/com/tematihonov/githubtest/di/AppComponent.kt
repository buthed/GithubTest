package com.tematihonov.githubtest.di

import com.tematihonov.githubtest.presentation.ui.fragment.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    RepositoryModule::class,
    NetworkModule::class,
    UseCaseModule::class
])
interface AppComponent {

    fun inject(fragment: MainFragment)
}