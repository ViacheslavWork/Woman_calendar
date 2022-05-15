package woman.calendar.every.day.health.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import woman.calendar.every.day.health.notifications.EverydayNotificationScheduler
import woman.calendar.every.day.health.ui.articles.ArticlesViewModel
import woman.calendar.every.day.health.ui.articles.details.ArticleDetailsViewModel
import woman.calendar.every.day.health.ui.articles.discover.DiscoverViewModel
import woman.calendar.every.day.health.ui.articles.recent.RecentViewModel
import woman.calendar.every.day.health.ui.articles.saved.SavedViewModel
import woman.calendar.every.day.health.ui.calendar.CalendarViewModel
import woman.calendar.every.day.health.ui.home.HomeViewModel
import woman.calendar.every.day.health.ui.notification_screens.NotificationScreenViewModel
import woman.calendar.every.day.health.ui.symptoms.SymptomsViewModel
import woman.calendar.every.day.health.ui.water.WaterViewModel
import woman.calendar.every.day.health.utils.BookmarksPreferences
import woman.calendar.every.day.health.utils.NotificationSchedulerPreferences
import woman.calendar.every.day.health.utils.RecentArticlesPreferences


val appModule = module {
    viewModel {
        CalendarViewModel(
            getMonthUseCase = get(),
            updatePeriodDayUseCase = get(),
            onOffEverydayNotificationUseCase = get()
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
}