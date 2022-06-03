package com.period.tracker.natural.cycles.di

import com.period.tracker.natural.cycles.domain.usecase.GetDailyNotificationDataUseCase
import com.period.tracker.natural.cycles.domain.usecase.RecalculateFromDayUseCase
import com.period.tracker.natural.cycles.domain.usecase.articles.GetArticleGroupsUseCase
import com.period.tracker.natural.cycles.domain.usecase.articles.GetArticleUseCase
import com.period.tracker.natural.cycles.domain.usecase.articles.GetArticlesFlowUseCase
import com.period.tracker.natural.cycles.domain.usecase.articles.GetArticlesUseCase
import com.period.tracker.natural.cycles.domain.usecase.cycles.GetCycleUseCase
import com.period.tracker.natural.cycles.domain.usecase.cycles.GetLastCyclesUseCase
import com.period.tracker.natural.cycles.domain.usecase.days.GetDayUseCase
import com.period.tracker.natural.cycles.domain.usecase.days.GetMonthUseCase
import com.period.tracker.natural.cycles.domain.usecase.days.GetWeekUseCase
import com.period.tracker.natural.cycles.domain.usecase.days.SaveDayUseCase
import com.period.tracker.natural.cycles.domain.usecase.firebase.articles.GetArticlesFlowFromFirebaseUseCase
import com.period.tracker.natural.cycles.domain.usecase.firebase.articles.SaveArticleToFirebaseUseCase
import com.period.tracker.natural.cycles.domain.usecase.firebase.bookmarks.DownloadArticlesBookmarksFromFirebaseUseCase
import com.period.tracker.natural.cycles.domain.usecase.firebase.bookmarks.SaveArticlesBookmarksToFirebaseUseCase
import com.period.tracker.natural.cycles.domain.usecase.firebase.days.DownloadDaysFromFirebaseUseCase
import com.period.tracker.natural.cycles.domain.usecase.firebase.days.SaveDayToFirebaseUseCase
import com.period.tracker.natural.cycles.domain.usecase.notification.OnOffEverydayNotificationUseCase
import com.period.tracker.natural.cycles.domain.usecase.periods.*
import com.period.tracker.natural.cycles.domain.usecase.symptoms.GetSymptomsUseCase
import com.period.tracker.natural.cycles.domain.usecase.symptoms.SaveSelectedSymptomsUseCase
import com.period.tracker.natural.cycles.domain.usecase.water.AddWaterUseCase
import com.period.tracker.natural.cycles.domain.usecase.water.GetWaterPerDayUseCase
import com.period.tracker.natural.cycles.domain.usecase.water.SubWaterUseCase
import org.koin.dsl.module

val domainModule = module {

    single<GetMonthUseCase> { GetMonthUseCase(repository = get()) }
    single<GetDayUseCase> { GetDayUseCase(repository = get()) }
    single<SaveDayUseCase> {
        SaveDayUseCase(
            repository = get(),
            saveDayToFirebaseUseCase = get(),
        )
    }
    single<GetWeekUseCase> { GetWeekUseCase(repository = get()) }
    single<GetLastPeriodsUseCase> { GetLastPeriodsUseCase(repository = get()) }
    single<GetLastCyclesUseCase> { GetLastCyclesUseCase(repository = get()) }
    single<GetCycleUseCase> { GetCycleUseCase(getDayUseCase = get()) }
    single<GetMinCountOfPeriodsUseCase> { GetMinCountOfPeriodsUseCase(repository = get()) }
    single<RecalculateFromDayUseCase> {
        RecalculateFromDayUseCase(
            saveDayUseCase = get(),
            getDayUseCase = get(),
            latestPeriodPreferences = get()
        )
    }
    single<GetSymptomsUseCase> { GetSymptomsUseCase(symptomsProvider = get()) }
    single<SaveSelectedSymptomsUseCase> {
        SaveSelectedSymptomsUseCase(
            saveDayUseCase = get(),
            getDayUseCase = get()
        )
    }

    single<UpdatePeriodDayUseCase> {
        UpdatePeriodDayUseCase(
            repository = get(),
            recalculateFromDayUseCase = get()
        )
    }
    single {
        MarkDayUseCase(
            saveDayUseCase = get(),
            getDayUseCase = get(),
            getMinCountOfPeriodsUseCase = get(),
            earliestPeriodPreferences = get(),
            latestPeriodPreferences = get(),
            getLastPeriodsUseCase = get()
        )
    }
    single { GetEarliestPeriodUseCase(getDayUseCase = get()) }
    //notifications
    single {
        GetDailyNotificationDataUseCase(
            getNotificationDataProvider = get(),
            getLastCyclesUseCase = get()
        )
    }
    //articles
    single { GetArticleGroupsUseCase(articlesProvider = get()) }
    single { GetArticlesUseCase(articlesProvider = get()) }
    single { GetArticlesFlowUseCase(articlesProvider = get()) }
    single { GetArticleUseCase(articlesProvider = get()) }
    //water
    single<GetWaterPerDayUseCase> { GetWaterPerDayUseCase() }
    single<AddWaterUseCase> { AddWaterUseCase(getDayUseCase = get(), saveDayUseCase = get()) }
    single<SubWaterUseCase> { SubWaterUseCase(getDayUseCase = get(), saveDayUseCase = get()) }
    //notification
    single {
        OnOffEverydayNotificationUseCase(
            everydayNotificationScheduler = get(),
            getMinCountOfPeriodsUseCase = get(),
            notificationSchedulerPreferences = get()
        )
    }
    //firebase
    single {
        DownloadDaysFromFirebaseUseCase(
            firebaseDatabaseReference = get(),
            repository = get(),
        )
    }
    single { SaveDayToFirebaseUseCase(firebaseDatabase = get()) }
    single { SaveArticlesBookmarksToFirebaseUseCase(firebaseDatabase = get()) }
    single {
        DownloadArticlesBookmarksFromFirebaseUseCase(
            firebaseDatabaseReference = get(),
            bookmarksPreferences = get()
        )
    }
    single { SaveArticleToFirebaseUseCase(firebaseDatabase = get()) }
    single { GetArticlesFlowFromFirebaseUseCase(firebaseDatabaseReference = get()) }
}
