package woman.calendar.every.day.health.di

import org.koin.dsl.module
import woman.calendar.every.day.health.domain.Repository
import woman.calendar.every.day.health.data.RepositoryImpl

val dataModule = module {
    single<Repository> { RepositoryImpl() }
}