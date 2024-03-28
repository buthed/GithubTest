package com.tematihonov.githubtest

import android.app.Application
import com.tematihonov.githubtest.di.AppComponent
import com.tematihonov.githubtest.di.DaggerAppComponent

class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}