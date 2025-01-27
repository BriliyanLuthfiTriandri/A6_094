package com.example.uasdatabase

import android.app.Application
import com.example.uasdatabase.dependenciesinjection.AppContainer
import com.example.uasdatabase.dependenciesinjection.ContainerApp

class AcaraApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = ContainerApp()
    }
}