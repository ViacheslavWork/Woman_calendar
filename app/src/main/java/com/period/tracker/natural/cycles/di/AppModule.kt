package com.period.tracker.natural.cycles.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.period.tracker.natural.cycles.notifications.EverydayNotificationScheduler
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
import com.period.tracker.natural.cycles.utils.*


val appModule = module {
    viewModel {
        CalendarViewModel(
            getMonthUseCase = get(),
            markDayUseCase = get(),
            recalculateFromDayUseCase = get(),
            onOffEverydayNotificationUseCase = get(),
            getCountOfPeriodsUseCase = get()
        )
    }
    viewModel {
        HomeViewModel(
            getWeekUseCase = get(),
            getCountOfPeriodsUseCase = get(),
            getLastCyclesUseCase = get(),
            getDayUseCase = get()
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
            recentArticlesPreferences = get()
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
    single { NotificationSchedulerPreferences(androidContext()) }
    single { BookmarksPreferences(androidContext()) }
    single { RecentArticlesPreferences(androidContext()) }
    single { EarliestPeriodPreferences(androidContext()) }
    single { LatestPeriodPreferences(androidContext()) }

    //onBoarding
    viewModel { OnBoardingContainerViewModel() }
}