package woman.calendar.every.day.health.di

import android.content.Context
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import woman.calendar.every.day.health.data.database.mixes.DaysDao
import woman.calendar.every.day.health.data.database.mixes.DaysDatabase

const val DAYS_DATABASE_NAME = "Days.db"

val roomModule = module {
    fun provideDaysDatabase(context: Context): DaysDatabase {
        return Room.databaseBuilder(context, DaysDatabase::class.java, DAYS_DATABASE_NAME)
            .fallbackToDestructiveMigration()//dangerous thing!!!
            .build()
    }

    fun provideDaysDao(database: DaysDatabase): DaysDao {
        return database.daysDao
    }

    single { provideDaysDatabase(context = androidContext()) }
    single { provideDaysDao(get()) }
}