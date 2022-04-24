package woman.calendar.every.day.health.di

import org.koin.dsl.module
import woman.calendar.every.day.health.data.RepositoryImpl
import woman.calendar.every.day.health.domain.Repository

val dataModule = module {
    single<Repository> { RepositoryImpl(daysDao = get()) }
}