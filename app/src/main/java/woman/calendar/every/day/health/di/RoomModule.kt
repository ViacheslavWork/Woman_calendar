package woman.calendar.every.day.health.di

import org.koin.dsl.module

const val DAYS_DATABASE_NAME = "Days.db"

val roomModule = module {
/*    fun providePersonsDatabase(context: Context): PersonsDatabase {
        return Room.databaseBuilder(context, PersonsDatabase::class.java, PERSONS_DATABASE_NAME)
            .fallbackToDestructiveMigration()//dangerous thing!!!
            .build()
    }

    fun providePersonsDao(database: PersonsDatabase): PersonsDao {
        return database.personsDao
    }

    single { providePersonsDatabase(context = androidContext()) }
    single { providePersonsDao(database = get()) }*/
}