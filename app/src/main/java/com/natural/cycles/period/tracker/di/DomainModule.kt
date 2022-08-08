package com.natural.cycles.period.tracker.di

import com.natural.cycles.period.tracker.domain.usecase.GetDailyNotificationDataUseCase
import com.natural.cycles.period.tracker.domain.usecase.RecalculateFromDayUseCase
import com.natural.cycles.period.tracker.domain.usecase.articles.GetArticleGroupsUseCase
import com.natural.cycles.period.tracker.domain.usecase.articles.GetArticleUseCase
import com.natural.cycles.period.tracker.domain.usecase.articles.GetArticlesFlowUseCase
import com.natural.cycles.period.tracker.domain.usecase.articles.GetArticlesUseCase
import com.natural.cycles.period.tracker.domain.usecase.cycles.GetCycleUseCase
import com.natural.cycles.period.tracker.domain.usecase.cycles.GetLastCyclesUseCase
import com.natural.cycles.period.tracker.domain.usecase.days.GetDayUseCase
import com.natural.cycles.period.tracker.domain.usecase.days.GetMonthUseCase
import com.natural.cycles.period.tracker.domain.usecase.days.GetWeekUseCase
import com.natural.cycles.period.tracker.domain.usecase.days.SaveDayUseCase
import com.natural.cycles.period.tracker.domain.usecase.firebase.articles.GetArticlesFlowFromFirebaseUseCase
import com.natural.cycles.period.tracker.domain.usecase.firebase.articles.SaveArticleToFirebaseUseCase
import com.natural.cycles.period.tracker.domain.usecase.firebase.bookmarks.DownloadArticlesBookmarksFromFirebaseUseCase
import com.natural.cycles.period.tracker.domain.usecase.firebase.bookmarks.SaveArticlesBookmarksToFirebaseUseCase
import com.natural.cycles.period.tracker.domain.usecase.firebase.days.DownloadDaysFromFirebaseUseCase
import com.natural.cycles.period.tracker.domain.usecase.firebase.days.SaveDayToFirebaseUseCase
import com.natural.cycles.period.tracker.domain.usecase.notification.OnOffEverydayNotificationUseCase
import com.natural.cycles.period.tracker.domain.usecase.periods.*
import com.natural.cycles.period.tracker.domain.usecase.symptoms.GetSymptomsUseCase
import com.natural.cycles.period.tracker.domain.usecase.symptoms.SaveSelectedSymptomsUseCase
import com.natural.cycles.period.tracker.domain.usecase.water.AddWaterUseCase
import com.natural.cycles.period.tracker.domain.usecase.water.GetWaterPerDayUseCase
import com.natural.cycles.period.tracker.domain.usecase.water.SubWaterUseCase
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
