package com.period.tracker.natural.cycles

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import timber.log.Timber
import com.period.tracker.natural.cycles.di.appModule
import com.period.tracker.natural.cycles.di.dataModule
import com.period.tracker.natural.cycles.di.domainModule
import com.period.tracker.natural.cycles.di.roomModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        AndroidThreeTen.init(this)
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(listOf(appModule, domainModule, dataModule, roomModule))
        }
    }
}