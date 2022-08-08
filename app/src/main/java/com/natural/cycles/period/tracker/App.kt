package com.natural.cycles.period.tracker

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import timber.log.Timber
import com.natural.cycles.period.tracker.di.appModule
import com.natural.cycles.period.tracker.di.dataModule
import com.natural.cycles.period.tracker.di.domainModule
import com.natural.cycles.period.tracker.di.roomModule

//com.period.tracker.natural.cycles
//com.natural.cycles.period.tracker
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