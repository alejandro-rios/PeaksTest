package com.alejandrorios.peakstest

import android.app.Application
import com.alejandrorios.peakstest.di.appModule
import com.alejandrorios.peakstest.di.dataModule
import com.alejandrorios.peakstest.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class PeaksTestApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@PeaksTestApplication)
            modules(appModule, domainModule, dataModule)
        }
    }
}
