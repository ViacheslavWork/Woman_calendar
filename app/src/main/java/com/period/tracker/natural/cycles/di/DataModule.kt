package com.period.tracker.natural.cycles.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import com.period.tracker.natural.cycles.data.RepositoryImpl
import com.period.tracker.natural.cycles.data.providers.ArticlesProviderImpl
import com.period.tracker.natural.cycles.data.providers.DailyNotificationDataProviderImpl
import com.period.tracker.natural.cycles.data.providers.SymptomsProviderImpl
import com.period.tracker.natural.cycles.domain.ArticlesProvider
import com.period.tracker.natural.cycles.domain.DailyNotificationDataProvider
import com.period.tracker.natural.cycles.domain.Repository
import com.period.tracker.natural.cycles.domain.SymptomsProvider

val dataModule = module {
//    single<Repository> { RepositoryTest() }
    single<Repository> { RepositoryImpl(daysDao = get()) }
    single<SymptomsProvider> { SymptomsProviderImpl() }
    single<ArticlesProvider> { ArticlesProviderImpl(androidContext()) }
    single<DailyNotificationDataProvider> { DailyNotificationDataProviderImpl(androidContext()) }
}