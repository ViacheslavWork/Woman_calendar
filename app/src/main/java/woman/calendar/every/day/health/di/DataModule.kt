package woman.calendar.every.day.health.di

import org.koin.dsl.module
import woman.calendar.every.day.health.data.RepositoryTest
import woman.calendar.every.day.health.data.SymptomsProviderImpl
import woman.calendar.every.day.health.domain.Repository
import woman.calendar.every.day.health.domain.SymptomsProvider

val dataModule = module {
    single<Repository> { RepositoryTest() }
    single<SymptomsProvider> { SymptomsProviderImpl() }
}