package com.tematihonov.githubtest

import android.app.Application
import com.tematihonov.githubtest.di.AppComponent
import com.tematihonov.githubtest.di.DaggerAppComponent
import com.tematihonov.githubtest.di.DataBaseModule

class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .dataBaseModule(DataBaseModule(applicationContext))
            .build()
    }
}