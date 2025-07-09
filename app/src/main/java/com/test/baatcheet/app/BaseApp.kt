package com.test.baatcheet.app

import android.app.Application
import com.test.baatcheet.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
            androidContext(applicationContext)
        }
    }
}