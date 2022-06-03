package com.period.tracker.natural.cycles.di

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.period.tracker.natural.cycles.notifications.EverydayNotificationScheduler
import com.period.tracker.natural.cycles.preferences.*
import com.period.tracker.natural.cycles.ui.MainViewModel
import com.period.tracker.natural.cycles.ui.articles.ArticlesViewModel
import com.period.tracker.natural.cycles.ui.articles.details.ArticleDetailsViewModel
import com.period.tracker.natural.cycles.ui.articles.discover.DiscoverViewModel
import com.period.tracker.natural.cycles.ui.articles.recent.RecentViewModel
import com.period.tracker.natural.cycles.ui.articles.saved.SavedViewModel
import com.period.tracker.natural.cycles.ui.calendar.CalendarViewModel
import com.period.tracker.natural.cycles.ui.day_info.DayInfoViewModel
import com.period.tracker.natural.cycles.ui.home.HomeViewModel
import com.period.tracker.natural.cycles.ui.notes.NotesViewModel
import com.period.tracker.natural.cycles.ui.notification_screens.NotificationScreenViewModel
import com.period.tracker.natural.cycles.ui.onboarding.container.OnBoardingContainerViewModel
import com.period.tracker.natural.cycles.ui.symptoms.SymptomsViewModel
import com.period.tracker.natural.cycles.ui.water.WaterViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    viewModel { MainViewModel(downloadDaysFromFirebaseUseCase = get()) }
    viewModel {
        CalendarViewModel(
            getMonthUseCase = get(),
            markDayUseCase = get(),
            recalculateFromDayUseCase = get(),
            onOffEverydayNotificationUseCase = get(),
            getMinCountOfPeriodsUseCase = get()
        )
    }
    viewModel {
        HomeViewModel(
            getWeekUseCase = get(),
            getMinCountOfPeriodsUseCase = get(),
            getLastCyclesUseCase = get(),
            getDayUseCase = get(),
            downloadDaysFromFirebaseUseCase = get()
        )
    }
    viewModel { ArticlesViewModel() }
    viewModel {
        WaterViewModel(
            getWaterPerDayUseCase = get(),
            addWaterUseCase = get(),
            subWaterUseCase = get(),
            getDayUseCase = get()
        )
    }
    viewModel {
        SymptomsViewModel(
            getSymptomsUseCase = get(),
            saveSelectedSymptomsUseCase = get(),
            getDayUseCase = get(),
            getLastCyclesUseCase = get(),
        )
    }
    viewModel { DayInfoViewModel(getDayUseCase = get(), getCycleUseCase = get()) }
    viewModel {
        NotesViewModel(
            getDayUseCase = get(),
            getCycleUseCase = get(),
            saveDayUseCase = get()
        )
    }
    //articles
    viewModel {
        DiscoverViewModel(
            getArticleGroupsUseCase = get(),
            getArticlesUseCase = get(),
            getArticlesFlowUseCase = get(),
            recentArticlesPreferences = get()
        )
    }
    viewModel {
        SavedViewModel(
            getArticlesFlowUseCase = get(),
            bookmarksPreferences = get()
        )
    }
    viewModel {
        RecentViewModel(
            getArticlesFlowUseCase = get(),
            recentArticlesPreferences = get()
        )
    }
    viewModel {
        ArticleDetailsViewModel(
            getArticleUseCase = get(),
            getArticlesFlowUseCase = get(),
            recentArticlesPreferences = get(),
            saveArticleToFirebaseUseCase = get()
        )
    }
    //notification
    single {
        EverydayNotificationScheduler(
            androidContext(),
            notificationSchedulerPreferences = get()
        )
    }
    viewModel {
        NotificationScreenViewModel(
            getDailyNotificationDataUseCase = get(),
            getLastCyclesUseCase = get()
        )
    }
    //preferences
    single { NotificationSchedulerPreferences(androidContext()) }
    single {
        BookmarksPreferences(
            androidContext(),
            saveArticlesBookmarksToFirebaseUseCase = get()
        )
    }
    single { RecentArticlesPreferences(androidContext()) }
    single { EarliestPeriodPreferences(androidContext()) }
    single { LatestPeriodPreferences(androidContext()) }
    single { FirstRunPreferences(androidContext()) }

    //firebase
    single<DatabaseReference> { Firebase.database.reference }

    //onBoarding
    viewModel { OnBoardingContainerViewModel() }
}