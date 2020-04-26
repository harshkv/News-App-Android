package com.harshithakv.weatherandnewsinfo.utils

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BaseApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(org.koin.core.logger.Level.DEBUG)
            androidContext(this@BaseApplication)
            modules(listOf(homeViewModel,currentWeatherRepo,dashboardViewModel,newsViewModel))
        }
    }
}