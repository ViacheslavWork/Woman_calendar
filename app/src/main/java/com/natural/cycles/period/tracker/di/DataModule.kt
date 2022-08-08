package com.natural.cycles.period.tracker.di

import com.natural.cycles.period.tracker.data.RepositoryImpl
import com.natural.cycles.period.tracker.data.providers.ArticlesProviderImpl
import com.natural.cycles.period.tracker.data.providers.DailyNotificationDataProviderImpl
import com.natural.cycles.period.tracker.data.providers.SymptomsProviderImpl
import com.natural.cycles.period.tracker.domain.ArticlesProvider
import com.natural.cycles.period.tracker.domain.DailyNotificationDataProvider
import com.natural.cycles.period.tracker.domain.Repository
import com.natural.cycles.period.tracker.domain.SymptomsProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
//    single<Repository> { RepositoryTest() }
    single<Repository> { RepositoryImpl(daysDao = get()) }
    single<SymptomsProvider> { SymptomsProviderImpl() }
    single<ArticlesProvider> {
        ArticlesProviderImpl(
            androidContext(),
            getArticlesFlowFromFirebaseUseCase = get()
        )
    }
    single<DailyNotificationDataProvider> { DailyNotificationDataProviderImpl(androidContext()) }
}