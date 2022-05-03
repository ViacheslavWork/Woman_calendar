package woman.calendar.every.day.health.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import woman.calendar.every.day.health.data.RepositoryImpl
import woman.calendar.every.day.health.data.providers.ArticlesProviderImpl
import woman.calendar.every.day.health.data.providers.DailyNotificationDataProviderImpl
import woman.calendar.every.day.health.data.providers.SymptomsProviderImpl
import woman.calendar.every.day.health.domain.ArticlesProvider
import woman.calendar.every.day.health.domain.DailyNotificationDataProvider
import woman.calendar.every.day.health.domain.Repository
import woman.calendar.every.day.health.domain.SymptomsProvider

val dataModule = module {
//    single<Repository> { RepositoryTest() }
    single<Repository> { RepositoryImpl(daysDao = get()) }
    single<SymptomsProvider> { SymptomsProviderImpl() }
    single<ArticlesProvider> { ArticlesProviderImpl(androidContext()) }
    single<DailyNotificationDataProvider> { DailyNotificationDataProviderImpl(androidContext()) }
}