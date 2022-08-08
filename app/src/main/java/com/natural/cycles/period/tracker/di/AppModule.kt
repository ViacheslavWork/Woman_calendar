package com.natural.cycles.period.tracker.di

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.natural.cycles.period.tracker.notifications.EverydayNotificationScheduler
import com.natural.cycles.period.tracker.preferences.*
import com.natural.cycles.period.tracker.ui.MainViewModel
import com.natural.cycles.period.tracker.ui.articles.ArticlesViewModel
import com.natural.cycles.period.tracker.ui.articles.details.ArticleDetailsViewModel
import com.natural.cycles.period.tracker.ui.articles.discover.DiscoverViewModel
import com.natural.cycles.period.tracker.ui.articles.recent.RecentViewModel
import com.natural.cycles.period.tracker.ui.articles.saved.SavedViewModel
import com.natural.cycles.period.tracker.ui.calendar.CalendarViewModel
import com.natural.cycles.period.tracker.ui.day_info.DayInfoViewModel
import com.natural.cycles.period.tracker.ui.home.HomeViewModel
import com.natural.cycles.period.tracker.ui.notes.NotesViewModel
import com.natural.cycles.period.tracker.ui.notification_screens.NotificationScreenViewModel
import com.natural.cycles.period.tracker.ui.onboarding.container.OnBoardingContainerViewModel
import com.natural.cycles.period.tracker.ui.symptoms.SymptomsViewModel
import com.natural.cycles.period.tracker.ui.water.WaterViewModel
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
    single { BookmarksPreferences(androidContext(),saveArticlesBookmarksToFirebaseUseCase = get()) }
    single { RecentArticlesPreferences(androidContext()) }
    single { EarliestPeriodPreferences(androidContext()) }
    single { LatestPeriodPreferences(androidContext()) }
    single { FirstRunPreferences(androidContext()) }
    single { PremiumStatusPreferences(androidContext()) }

    //firebase
    single<DatabaseReference> { Firebase.database.reference }

    //onBoarding
    viewModel { OnBoardingContainerViewModel() }
}