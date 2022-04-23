package woman.calendar.every.day.health

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import timber.log.Timber
import woman.calendar.every.day.health.di.appModule
import woman.calendar.every.day.health.di.dataModule
import woman.calendar.every.day.health.di.domainModule
import woman.calendar.every.day.health.di.roomModule

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